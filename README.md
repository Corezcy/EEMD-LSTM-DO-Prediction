# EEMD-LSTM-DO-prediction
EEMD(集合经验模态分解)、LSTM(长短时记忆网络)、time series prediction(时间序列预测)、DO（dissolved oxygen,溶解氧）


本文提出了一种改进后的 LSTM 模型，即 EEMD-LSTM 模型。该方法在获取原始 溶解氧时间序列后并预处理后，经过 EEMD 分解为若干子序列，并对其分别建立 LSTM 预测模型，叠加个各个模型的预测结果即可获取最终的预测结果。在获取江苏无锡长江 水质实时监测站溶解氧数据后展开实验，选取原始 LSTM 模型、改进后的 BP 模型、原 始 BP 模型作为对比，实验表明，EEMD-LSTM 模型具有最小的预测误差，更好的模拟 溶解氧时间序列的走势，具有最好的预测效果。
 

 This paper presents an improved LSTM model, the EEMD-LSTM model. After the original dissolved oxygen time series is obtained and preprocessed, the method is decomposed into several subsequences by EEMD, and LSTM prediction models are established for them respectively, and the prediction results of each model are superimposed to obtain the final prediction results. After obtaining the dissolved oxygen data of Jiangsu Wuxi Yangtze River Water Quality Real-Time Monitoring Station, the experiment was carried out, and the original LSTM model, the improved BP model, and the original BP model were selected for comparison. The experiment showed that the EEMD-LSTM model has the smallest prediction error and a better simulation The trend of the dissolved oxygen time series has the best prediction effect.
 
 该文件夹包含三个部分，分别模型部分、服务器端部分、安卓app端部分
 
 The folder contains three parts, the model part, the server part, and the Android app part
