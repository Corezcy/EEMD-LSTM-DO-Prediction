

import numpy as np
from PyEMD import EEMD, EMD, Visualisation
import pylab as plt
import os

def Signal():
    global E_imfNo
    E_imfNo = np.zeros(50, dtype=np.int)

    # EEMD options
    max_imf = 7

    """
    信号参数：
    N:采样频率500Hz
    tMin:采样开始时间
    tMax:采样结束时间 2*np.pi
    """
    N = 500
    tMin, tMax = 0, 2 * np.pi
    T = np.linspace(tMin, tMax, N)
    # 信号S:是多个信号叠加信号
    S = 3 * np.sin(4 * T) + 4 * np.cos(9 * T) + np.sin(8.11 * T + 1.2)

    # EEMD计算
    eemd = EEMD()
    eemd.trials = 50
    eemd.noise_seed(12345)

    E_IMFs = eemd.eemd(S)
    imfNo = E_IMFs.shape[0]

    # Plot results in a grid
    c = np.floor(np.sqrt(imfNo + 1))
    r = np.ceil((imfNo + 1) / c)

    plt.ioff()
    plt.subplot(r, c, 1)
    plt.plot(T, S, 'r')
    plt.xlim((tMin, tMax))
    plt.title("Original signal")

    i = 1;
    for imf in E_IMFs:
       plt.subplot(len(E_IMFs), 1, i)
       plt.plot(imf)
       i += 1

    # for num in range(imfNo):
    #     plt.subplot(r, c, num + 2)
    #     plt.plot(T, E_IMFs[num], 'g')
    #     plt.xlim((tMin, tMax))
    #     plt.title("Imf " + str(num + 1))


    plt.text(0,0,str(format(i,'.4f')),style='italic', ha='center', wrap=True)
    plt.save("haha.jpg")




Signal()


