import numpy as np
import pandas as pd

from pandas import read_csv
from pandas import DataFrame
from datetime import datetime
from matplotlib import pyplot
from pylab import mpl

from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import mean_squared_error
from sklearn.metrics import mean_absolute_error
from sklearn.metrics import r2_score
from sklearn.ensemble import IsolationForest

from pandas import concat
from PyEMD import EEMD

from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.callbacks import ModelCheckpoint
from keras.layers import Dropout
from keras.layers import Activation

from scipy import interpolate, math
import matplotlib.pyplot as plt

from keras import Input, Model
from keras.layers import Dense
from keras.models import load_model


def data_split(data, train_len, lookback_window):
    train = data[:train_len]  # 标志训练集
    test = data[train_len:]  # 标志测试集

    # X1[]代表移动窗口中的10个数
    # Y1[]代表相应的移动窗口需要预测的数
    # X2, Y2 同理

    X1, Y1 = [], []
    for i in range(lookback_window, len(train)):
        X1.append(train[i - lookback_window:i])
        Y1.append(train[i])
        Y_train = np.array(Y1)
        X_train = np.array(X1)

    X2, Y2 = [], []
    for i in range(lookback_window, len(test)):
        X2.append(test[i - lookback_window:i])
        Y2.append(test[i])
        y_test = np.array(Y2)
        X_test = np.array(X2)

    return (X_train, Y_train, X_test, y_test)


def data_split_LSTM(X_train, Y_train, X_test, y_test):  # data split f
    X_train = X_train.reshape(X_train.shape[0], X_train.shape[1], 1)
    X_test = X_test.reshape(X_test.shape[0], X_test.shape[1], 1)
    Y_train = Y_train.reshape(Y_train.shape[0], 1)
    y_test = y_test.reshape(y_test.shape[0], 1)
    return (X_train, Y_train, X_test, y_test)


def imf_data(data, lookback_window):
    X1 = []
    for i in range(lookback_window, len(data)):
        X1.append(data[i - lookback_window:i])
    X1.append(data[len(data) - 1:len(data)])
    X_train = np.array(X1)
    return X_train


def visualize(history):
    plt.rcParams['figure.figsize'] = (10.0, 6.0)
    # Plot training & validation loss values
    plt.plot(history.history['loss'])
    plt.plot(history.history['val_loss'])
    plt.title('Model loss')
    plt.ylabel('Loss')
    plt.xlabel('Epoch')
    plt.legend(['Train', 'Test'], loc='upper left')
    plt.show()


def LSTM_Model(X_train, Y_train,i):
    # filepath = '../lbw5/' + str(i) + '-{epoch:02d}-{val_acc:.2f}.h5'
    # checkpoint = ModelCheckpoint(filepath, monitor='loss',verbose=1,save_best_only=False,mode='auto',period=10)
    # callbacks_list = [checkpoint]
    model = Sequential()
    model.add(LSTM(50,activation='tanh', input_shape=(X_train.shape[1], X_train.shape[2])))  # 已经确定10步长
    '''
    ,return_sequences = True
    如果设置return_sequences = True，该LSTM层会返回每一个time step的h，
    那么该层返回的就是1个由多个h组成的2维数组了，如果下一层不是可以接收2维数组
    的层，就会报错。所以一般LSTM层后面接LSTM层的话，设置return_sequences = True，
    如果接全连接层的话，设置return_sequences = False。
    '''
    model.add(Dense(1))
    model.compile(loss='mse', optimizer='adam')
    model.fit(X_train, Y_train, epochs=100, batch_size=16, validation_split=0.1, verbose=2, shuffle=True)
    return (model)

def isolutionforest(DO):

    rng = np.random.RandomState(42)
    clf = IsolationForest(random_state=rng, contamination=0.025)  # contamination为异常样本比例
    clf.fit(DO)

    DO_copy = DO
    m = 0

    pre = clf.predict(DO)
    for i in range(len(pre)):
        if pre[i] == -1:
            DO_copy = np.delete(DO_copy, i - m, 0)
            plt.scatter(i,DO[i],c='red')
            print(i)
            m += 1
    # plt.plot(DO)
    # plt.show()
    return DO_copy


def plot_curve(true_data, predicted):
    # rmse=format(RMSE(test,prediction),'.4f')
    # mape=format(MAPE(test,prediction),'.4f')
    plt.plot(true_data, label='True data')
    plt.plot(predicted, label='Predicted data')
    plt.legend()
    # plt.text(1, 1, 'RMSE:' + str(rmse)+' \n '+'MAPE:'+str(mape), color = "r",style='italic', wrap=True)
    #plt.text(2, 2, "RMSE:" + str(format(RMSE(true_data,predicted),'.4f'))+" \n "+"MAPE:"+str(format(MAPE(true_data,predicted),'.4f')), style='italic', ha='center', wrap=True)
    #plt.savefig('result_EEMD_LSTM_E5B16.png')
    plt.show()


def RMSE(test, predicted):
    rmse = math.sqrt(mean_squared_error(test, predicted))
    return rmse


def MAPE(Y_true, Y_pred):
    Y_true, Y_pred = np.array(Y_true), np.array(Y_pred)
    return np.mean(np.fabs((Y_true - Y_pred) / Y_true)) * 100


if __name__ == '__main__':

    plt.rcParams['figure.figsize'] = (10.0, 5.0)  # set default size of plots
    plt.rcParams['image.interpolation'] = 'nearest'
    plt.rcParams['image.cmap'] = 'gray'

    dataset = pd.read_csv('../csv/Water Quality Record.csv', header=0, index_col=0, parse_dates=True)
    data = dataset.values.reshape(-1)

    values = dataset.values
    groups = [0, 1, 2, 3]
    # fig, axs = plt.subplots(1)

    df = pd.DataFrame(dataset)  # 整体数据的全部字典类型
    do = df['Dissolved Oxygen']  # 返回溶解氧那一列，用字典的方式

    DO = []
    for i in range(0, len(do)):
        DO.append([do[i]])
    scaler_DO = MinMaxScaler(feature_range=(0, 1))
    DO = scaler_DO.fit_transform(DO)
    # DO=DO[:100]

    DO = isolutionforest(DO)
    # plt.plot(DO)
    # plt.show()

    eemd = EEMD()
    eemd.noise_seed(12345)
    imfs = eemd.eemd(DO.reshape(-1),None,8)
    
    # print(imfs.shape)
    c = int(len(DO) * .85)
    lookback_window = 6
    imfs_prediction = []

    i = 1
    for imf in imfs:
       plt.subplot(len(imfs), 1, i)
       plt.plot(imf)
       i += 1

    plt.savefig('result_imf.png')
    plt.show()

    test = np.zeros([len(DO) - c - lookback_window, 1])

#     i = 1
#     for imf in imfs:
#         print('-' * 45)
#         print('This is  ' + str(i) + '  time(s)')
#         print('*' * 45)
#         X1_train, Y1_train, X1_test, Y1_test = data_split(imf_data(imf, 1), c, lookback_window)
#         X2_train, Y2_train, X2_test, Y2_test = data_split_LSTM(X1_train, Y1_train, X1_test, Y1_test)
#         test += Y2_test
#         model = load_model('../lbw6/EEMD-LSTM-imf' + str(i) + '-100.h5')
#         prediction_Y = model.predict(X2_test)
    
#         imfs_prediction.append(prediction_Y)
#         i += 1;

    i = 1
    for imf in imfs:
       print('-'*45)
       print('This is  ' + str(i)  + '  time(s)')
       print('*'*45)
       X1_train, Y1_train, X1_test, Y1_test = data_split(imf_data(imf,1), c, lookback_window)
       X2_train, Y2_train, X2_test, Y2_test = data_split_LSTM(X1_train, Y1_train, X1_test, Y1_test)
       test += Y2_test
       model = LSTM_Model(X2_train,Y2_train,i)
       model.save('../lbw6/EEMD-LSTM-imf' + str(i) + '-100.h5')
       prediction_Y = model.predict(X2_test)
       imfs_prediction.append(prediction_Y)
       i+=1;

    
    imfs_prediction = np.array(imfs_prediction)
    prediction = [0.0 for i in range(len(test))]
    prediction = np.array(prediction)
    for i in range(len(test)):
        t = 0.0
        for imf_prediction in imfs_prediction:
            t += imf_prediction[i][0]
        prediction[i] = t
    
    prediction = prediction.reshape(prediction.shape[0], 1)
    
    test = scaler_DO.inverse_transform(test)
    prediction = scaler_DO.inverse_transform(prediction)
    
    # plot_curve(test, prediction)
    # print(RMSE(test, prediction))
    # print(MAPE(test, prediction))
    
    rmse=format(RMSE(test,prediction),'.4f')
    mape=format(MAPE(test,prediction),'.4f')
    r2 = format(r2_score(test, prediction), '.4f')
    mae = format(mean_absolute_error(test, prediction), '.4f')
    print('RMSE:' + str(rmse) + '\n' +  'MAE:' + str(mae) + '\n' + 'MAPE:' + str(mape) + '\n' + 'R2:' + str(r2))
    
    
    
