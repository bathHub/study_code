# -*- coding: utf-8 -*-


import random
import numpy as np


def readDataIntoList(largefile):
    '''

    :param filename: the merged big filename
    :return: a list of documents , each entry's format is : categ content
    '''
    fopen = open(largefile, 'r', encoding="utf-8")
    datalist = []
    for line in fopen.readlines():  #依次读取每行
        #label, content = line.split()
        datalist.append(line)
   # for label in ['Others', 'Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art', 'Transport', 'Space']:
        #print(len(dataDict[label]))
    random.shuffle(datalist)

    return datalist
#readDataIntoList('D:\作业\\big.txt')
def readFeaturewordtoset(filename, num=200):
    '''

    :param filename: the file saves chosen features
    num : feature word
    :return: a set of feature words
    '''
    featurewords = set()
    fread = open(filename, 'r')
    for line in fread.readlines():
        line = line.split()[:num]
        #print(line)#10 line
        featurewords = featurewords | set(line)
    return featurewords

outview=readFeaturewordtoset('/workspace/17210240120-project-2/logistic回归program/featureword.txt')
print(outview)
def listToNumpy(featurewords, datalist):
    '''

    :param featurewords:a list of words
    :param datalist:a list of documents , each entry's format is : categ content
    :return:two numpy array X (m, n) Y (m, c) m , number of samples , n ,number of features, c,number
    of class  every row of Y use one hot code
    '''
    example_num = len(datalist)
    feature_num = len(featurewords)
    class_list = ['Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art',
                  'Transport', 'Space']

    class_num = len(class_list)
    X = np.zeros([example_num, feature_num+1 ])# +1 intercept
    X[:,1] = np.ones([example_num])#,,,,,,
    Y = np.zeros([example_num,class_num])
    i = 0#aaaaaaa
    for label_line in datalist:
        label, line = label_line.split()
        line_count = []

        #j= 0
        for feature in featurewords:#list is ordered?
            #if j == 0:
                #print(feature)
            line_count.append(line.count(feature))
            #j += 1
        #np.row_stack((X, line_count))#add a row to stack
        X[i,1:] = line_count#from 1 column,0col is intercept
        #print(X[i,:])
        #print(len(line_count))
        label_matrix = np.eye(class_num)
        c = class_list.index(label)
        #print(c)
        y = label_matrix[c,:]#from[0,0],
        #np.row_stack((Y,))
        Y[i,:] = y
        #print(y)
        i += 1
    #print(Y[:])
    return X, Y

#listToNumpy(readFeaturewordtoset('D:\作业\\featureword.txt'),readDataIntoList('D:\作业\\big.txt'))
def softmaxtrain(X, Y, precise = 0.00001, iternum= 10000, rate = 0.1):
    '''

    :param X: X , Y data for train two numpy array X (m, n) Y (m, c) m , number of samples , n ,number of features include intercept, c,number
    of class  every row of Y use one hot code
    :param Y:
    :param precise: difference between two iterations
    :param iternum: num of iteration
    :param rate: learning rate
    :return: W (c, n)  parameter matrix every column is a vector of certain class
    '''
    train_num = np.shape(X)[0]
    w_dim = np.shape(X)[1]
    class_num =np.shape(Y)[1]
    W = np.random.randn(w_dim, class_num) * 0.0001
    temp =X.dot(W)#http://cs229.stanford.edu/section/vec_demo/Vectorization_Section.pdf
    a = np.max(temp, axis=1)#a (m, ) max of every row
    #np.reshape(v, (3, 1))
    a_broad = np.reshape(a, (train_num, 1))#a_broad (m,1),temp (m, c)
    temp1 = np.exp(temp - a_broad)#temp1 (m,c)
    sums = np.sum(temp1, axis=1)#(m, )
    sums_broad = np.reshape(sums, (train_num, 1))#sums_broad (m,1)
    h =temp1 / sums_broad#broadcasting make their shape same (m, c)
    #diff = 10
    time = 0
    while  time < iternum:# diff < precise and
        W += rate*(((Y - h).T).dot(X)).T#θ -= α((h − y)T X)T W (c, n) every column is a vector of certain class
        time += 1
        #print(time)
    return W

#softmaxtrain(*listToNumpy(readFeaturewordtoset('D:\作业\\featureword.txt'),readDataIntoList('D:\作业\\big.txt')))
def softmaxpredict(W, X, Y):
    '''

    :param W: W (c, n)  parameter matrix every column is a vector of certain class
    :param X:X , Y data for test two numpy array X (m, n) Y (m, c) m , number of samples , n ,number of features include intercept, c,number
    of class  every row of Y use one hot code
    :param Y:
    :return:
    '''
    score_matrix = X.dot(W)#(m, c)
    Ypred = np.argmax(score_matrix, axis=1)#predict the class that achieve the max score of every row
    Y_class = np.argmax(Y, axis=1)
    correct_rate = np.mean(Y_class==Ypred)
    print(correct_rate)
    return correct_rate

#X , Y = listToNumpy(readFeaturewordtoset('D:\作业\\featureword.txt'), readDataIntoList('D:\作业\\big.txt'))
#softmaxpredict(softmaxtrain(X, Y), X, Y)


def crossValidate(X, Y, turns_num):
    '''

    :param X, Y: X , Y all  data  two numpy array X (m, n) Y (m, c) m , number of samples , n ,number of features include intercept, c,number
    of class  every row of Y use one hot code
    :param turns_num: fold number of cross validation
    :return: average correct rate of cross validation
    '''
    m = np.shape(X)[0]
    w_dim = np.shape(X)[1]
    class_num = np.shape(Y)[1]
    ave_correc_rate = 0.0
    for i in range(turns_num):
        print("ROUND %d :" %(i))
        X_test = X[ i*m//turns_num: (i+1)*m//turns_num,:]
        X_train =np.delete(X, np.s_[i*m//turns_num: (i+1)*m//turns_num:], axis=0 )
        #print(np.shape(X_test))
        #delete the elem of every col in np.s_[], ie delete rows
        Y_test = Y[ i*m//turns_num: (i+1)*m//turns_num,:]
        Y_train = np.delete(Y, np.s_[i * m // turns_num: (i + 1) * m // turns_num:], axis=0)
        W = softmaxtrain(X_train, Y_train)
        ave_correc_rate += softmaxpredict(W, X_test, Y_test)

    print('The average correct rate is:  %f ' % (ave_correc_rate / turns_num))
    return ave_correc_rate / turns_num
if __name__ == '__main__':
    crossValidate(*listToNumpy(readFeaturewordtoset('/workspace/17210240120-project-2/logistic回归program/my_file/featureword.txt',9), readDataIntoList('/workspace/17210240120-project-2/logistic回归program/my_file/big.txt')),4)
    input()