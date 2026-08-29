#-*-coding:utf-8-*-
import keras
from flask import Flask,jsonify
from flask import request
from keras import optimizers
import os
import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from sklearn.ensemble import IsolationForest
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import MetaData
from sqlalchemy import create_engine
from sqlalchemy.orm import *
import sys
import pymysql.cursors

from keras.optimizers import Adam
from keras import Input, Model
from keras.models import load_model
#导入EEMD库
from PyEMD import EEMD


app = Flask(__name__)

#
# basedir = os.path.abspath(os.path.dirname(__file__))
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'userConfigBase.sqlite')
# app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True
# app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
#
# engine = create_engine('sqlite:///' + os.path.join(basedir, 'userConfigBase.sqlite'), echo=True)
# metadata = MetaData(engine)
# db = SQLAlchemy(app)
#
# # manager=Manager(app)
# userName = ''
# # 初始化数据库连接:
# engine = create_engine('sqlite:///' + os.path.join(basedir, 'userConfigBase.sqlite'))
# # 创建DBSession类型:
# DBSession = sessionmaker(bind=engine)

'''配置数据库'''
app.config['SECRET_KEY'] = 'hard to guess'#一个字符串，密码。也可以是其他如加密过的
#在此登录的是root用户，要填上密码如123456，MySQL默认端口是3306。并填上创建的数据库名如youcaihua
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:App123@localhost:3306/bhjbhj'

#设置下方这行code后，在每次请求结束后会自动提交数据库中的变动
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db = SQLAlchemy(app)#实例化数据库对象，它提供访问Flask-SQLAlchemy的所有功能

app.config['JSON_AS_ASCII'] = False
DB_URI = 'mysql+pymysql://root:App123@localhost:3306/bhjbhj'
engine = create_engine(DB_URI)


'''
函数处理
'''


# 预测前准备数据
def prepare_data():
    con = engine.connect()
    # con.execute("truncate DOdataPrecictOnly")
    # con.execute("INSERT INTO DOdataPrecictOnly(Date,`Dissolved Oxygen`) ( SELECT Date,`Dissolved Oxygen` FROM DOdata order by Date ASC)")
    rv = con.execute("select `Dissolved Oxygen` from DOdataPrecictOnly")

    do = []
    for i in rv:
        print(i)
        do.append(i[0])

    DO = []
    for i in range(0, len(do)):
        DO.append([do[i]])
    scaler_DO = MinMaxScaler(feature_range=(0, 1))
    DO = scaler_DO.fit_transform(DO)
    # DO = isolutionforest(DO)

    eemd = EEMD()
    eemd.noise_seed(12345)
    imfs = eemd.eemd(DO.reshape(-1), None, 8)
    con.close()

    return imfs,scaler_DO

# 完成溶解氧预测
def DO_predict(imfs,scaler_DO,lookback_window=10):
    imfs_prediction = []
    i = 1


    for imf in imfs:
        print('-' * 45)
        print('This is  ' + str(i) + '  time(s)')
        print('*' * 45)
        X2_test = imf[len(imf)-10:len(imf)+1].reshape(1,lookback_window,1)
        model = load_model('model/EEMD-LSTM-imf' + str(i) + '-100.h5')
        prediction_Y = model.predict(X2_test)
        imfs_prediction.append(prediction_Y)
        keras.backend.clear_session()
        i += 1;

    imfs_prediction = np.array(imfs_prediction).reshape(-1)
    prediction = 0.0
    for i in range(len(imfs_prediction)):
        prediction += imfs_prediction[i]

    prediction = scaler_DO.inverse_transform(np.array(prediction).reshape(1,-1)).reshape(-1)[0]

    return prediction

def predict():
    con = engine.connect()
    imfs, scaler = prepare_data()
    prediction = DO_predict(imfs, scaler)
    con.execute("INSERT INTO DOdataPrecictOnly(`Dissolved Oxygen`) VALUES("+str(round(prediction,2))+")")
    con.close()
    return str(round(prediction,2))

'''
定义数据库类
'''
class userInfoTable(db.Model):
    __tablename__ = 'userInfo'
    id = db.Column(db.Integer,primary_key=True)
    username = db.Column(db.String, unique=True)
    password = db.Column(db.String)

    def __repr__(self):
        return 'table name is ' + self.username


'''
接口端API函数
'''
@app.route('/')
def test():
    return "服务器准备就绪！"


@app.route('/predictone')
def predictone():
    str = predict()
    return "未来1天的溶解氧数据预测为:\n\t" + str;

@app.route('/predictthree')
def predictthree():

    str = predict()
    str1 = predict()
    str2 = predict()

    return "未来3天的溶解氧数据预测为:\n\t" + str +"\n\t"+str1+"\n\t"+str2;

@app.route('/predictseven')
def predictseven():
    str = predict()
    str1 = predict()
    str2 = predict()
    str3 = predict()
    str4 = predict()
    str5 = predict()
    str6 = predict()
    str7 = predict()

    return "未来7天的溶解氧数据预测为:\n\t" + str +"\n\t"+str1+"\n\t"+str2+ str3 +"\n\t"+ str4 +"\n\t"+ str5 +"\n\t"+ str6 +"\n\t"+ str7 +"\n\t"


# 检查用户登陆
@app.route('/user', methods=['GET','POST'])
def check_user():
    userName = request.form['username']
    haveregisted = userInfoTable.query.filter_by(username=request.form['username']).all()
    if haveregisted.__len__() is not 0:  # 判断是否已被注册
        passwordRight = userInfoTable.query.filter_by(username=request.form['username'],
                                                      password=request.form['password']).all()
        if passwordRight.__len__() is not 0:
            print(str(userName) + "log success")
            return '2'
        else:
            return '1'
    else:
        print(str(userName) + "log fail")
        return '0'


# 此方法处理用户注册
@app.route('/register', methods=['POST'])
def register():
    userName = request.form['username']
    db.create_all()
    haveregisted = userInfoTable.query.filter_by(username=request.form['username']).all()
    if haveregisted.__len__() is not 0:  # 判断是否已被注册
        return '0'
    userInfo = userInfoTable(username=request.form['username'], password=request.form['password'])
    db.session.add(userInfo)
    db.session.commit()
    return '1'

@app.route('/change', methods=['GET','POST'])
def change():
    userName = request.form['username']
    print(str(userName))
    haveregisted = userInfoTable.query.filter_by(username=request.form['username']).all()
    print(haveregisted)
    if haveregisted.__len__() is not 0:  # 判断是否已被注册
        passwordRight = userInfoTable.query.filter_by(username=request.form['username'],
                                                      password=request.form['password']).all()
        user = db.session.query(userInfoTable).filter_by(username=request.form['username'],
                                                      password=request.form['password']).first()

        if passwordRight.__len__() is not 0:

            user.password = str(request.form['newpassword'])
            db.session.commit()
            print(str(userName) + " change success")
            return '2'
        else:
            return '1'
    else:
        print(str(userName) + " log fail")
        return '0'



# 此方法处理用户注册
@app.route('/allyear')
def allyear():
    # 创建一个游标
    conn = engine.connect()
    rv = conn.execute("select distinct YEAR(Date) from DOdata order by YEAR(Date) ASC")

    list = []
    for i in rv:
        print(i)
        list.append(i[0])

    t = {
        'a':len(list),
        'b': list
    }

    conn.close()

    return jsonify(t);



@app.route('/alldidian')
def alldidian():
    # 创建一个游标
    conn = engine.connect()
    rv = conn.execute("select distinct Address from DOdata")

    list = []
    for i in rv:
        print(i)
        list.append(i[0])

    t = {
        'a':len(list),
        'b': list
    }
    conn.close()
    return jsonify(t);


@app.route('/chartdata',methods=['POST'])
def chartdata():
    # 创建一个游标
    conn = engine.connect()

    didian = request.form['didian']
    cmyear = request.form['cmyear']
    zhibiao = request.form['zhibiao']
    if(zhibiao == "耗氧量"):
        zhibiao = "Oxygen Consumption"
    elif(zhibiao == "氨氮化合物"):
        zhibiao = "Ammonia Nitrogen"
    elif(zhibiao == "PH值"):
        zhibiao = "PH"
    elif (zhibiao == "溶解氧"):
        zhibiao = "Dissolved Oxygen"

    rv = conn.execute("select Date," + "`" +zhibiao + "`" +" from DOdata where YEAR(Date) = "+cmyear+" and Address = "+"\"" + didian +"\"")

    date = []
    data = []
    for i in rv:
        print(i)
        date.append(i[0].__str__())
        data.append(i[1])

    t = {
        'a':len(date),
        'b': date,
        'c':data
    }
    conn.close()
    return jsonify(t);


@app.route('/search_now')
def searchnow():
    # 创建一个游标
    conn = engine.connect()
    rv = conn.execute("select `Date`,`Dissolved Oxygen` from DOdata where to_days(Date) = to_days(now());")

    list = []
    list1=[]
    for i in rv:
        print(i)
        list.append(i[0])
        list1.append(i[1])

    t = {
        'a':len(list),
        'b':list,
        'c':list1
    }
    conn.close()
    return jsonify(t);

@app.route('/search_past_one')
def search_past_one():
    # 创建一个游标
    conn = engine.connect()
    #SELECT `Date`,`Dissolved Oxygen` FROM DOdata WHERE TO_DAYS( NOW( ) ) - TO_DAYS( Date) <= 1
    #select `Date`,`Dissolved Oxygen` from DOdata where YEAR(now()) - YEAR(Date) <=3
    rv = conn.execute("SELECT `Date`,`Dissolved Oxygen` FROM DOdata WHERE TO_DAYS( NOW( ) ) - TO_DAYS( Date) <= 1")

    list = []
    list1=[]
    for i in rv:
        print(i)
        list.append(i[0])
        list1.append(i[1])

    t = {
        'a':len(list),
        'b':list,
        'c':list1
    }
    conn.close()
    return jsonify(t);

@app.route('/search_past_three')
def search_past_three():
    # 创建一个游标
    conn = engine.connect()
    rv = conn.execute("SELECT `Date`,`Dissolved Oxygen` FROM DOdata where DATE_SUB(CURDATE(), INTERVAL 3 DAY) <= date(Date)")

    list = []
    list1=[]
    for i in rv:
        print(i)
        list.append(i[0])
        list1.append(i[1])

    t = {
        'a':len(list),
        'b':list,
        'c':list1
    }
    conn.close()
    return jsonify(t);

@app.route('/search_past_seven')
def search_past_seven():
    # 创建一个游标
    conn = engine.connect()
    rv = conn.execute("SELECT `Date`,`Dissolved Oxygen` FROM DOdata where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(Date)")

    list = []
    list1=[]
    for i in rv:
        print(i)
        list.append(i[0])
        list1.append(i[1])

    t = {
        'a':len(list),
        'b':list,
        'c':list1
    }
    conn.close()
    return jsonify(t);



#把string类型转换为一个个数字
# @app.route('/<string>')
# def get_string(string):
#
#     return float(string.split(',')[0])
#
#

if __name__ == '__main__':
    con = engine.connect()
    con.execute("truncate DOdataPrecictOnly")
    con.execute("INSERT INTO DOdataPrecictOnly(Date,`Dissolved Oxygen`) ( SELECT Date,`Dissolved Oxygen` FROM DOdata order by Date ASC)")
    con.close()

    app.run('0.0.0.0', port=5000, debug=True)


