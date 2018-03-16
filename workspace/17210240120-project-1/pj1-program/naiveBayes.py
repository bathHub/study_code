import random
import math
import jieba
import os

dirname =os.path.dirname(os.path.realpath(__file__))

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

#readFeaturewordtoset('D:\作业\\featureword.txt')


def listtoDict(trainlist):
    '''

    :param trainlist: a list of documents , each entry's format is : categ content
    :return: a dict whose key is document category and value is a long line contain all documents in this categ
    a dict of log  priors of all categ
    '''
    trainDict = {}#sampling without a class?
    priors = {}
    for label_line in trainlist:
        label, line = label_line.split()
        priors.setdefault(label,0)
        priors[label] += 1
        trainDict.setdefault(label,'')
        trainDict[label] += line
    for categ in priors:
        priors[categ] /= float(len(trainlist))
        priors[categ] =  math.log(priors[categ])
    #print(priors)
    return trainDict, priors
#listtoDict(readDataIntoList('D:\作业\\big.txt'), )
def naiveBayes(traindict, priors,featurewords,alpha):
    '''

    :param traindict: a dict whose key is document category and value is a long line contain a documents in this categ
    :param priors: a dict of priors of all categ
    :param featurewords:  a set of feature words
    :param alpha: smoothing constant
    :return: a dict of log probability of all feature word belong to a categ
    '''
    procategword = {}
    for categ in traindict:
        wordnumofcateg = 0#compute the total number of word of this categ
        procategword[categ] = {}# compute the prob of every word belong to every categ
        for word in featurewords:
            procategword[categ].setdefault(word,alpha)
            wordnumofcateg += (traindict[categ].count(word) + alpha)
            procategword[categ][word] += traindict[categ].count(word)
        #print(wordnumofcateg)
        for word in featurewords:#for each word we should divide by total word num of this categ
            procategword[categ][word] /= float(wordnumofcateg)
            procategword[categ][word] = math.log(procategword[categ][word])
    #print(sum(procategword['Space'].values())) #should be 1 if prob without log

    return procategword, priors
naiveBayes(*listtoDict(readDataIntoList(dirname+'\\big.txt')),readFeaturewordtoset(dirname+'\\featureword.txt'),1)

def keyofmaxdictvalues(di):
    for world, miv in sorted(di.items(), key=lambda item: item[1], reverse=True):
        return world

def bayesPredict(testlist, featurepro,  priors, featurewords):
    '''

    :param testlist: a list of documents , each entry's format is : categ content
    :param featurepro: a dict of log probability of all feature word belong to a categ
    :param featurewords: a set of feature words
    :param priors: a dict of priors of all categ
    :return:  correct rate on test
    '''
    docbelongcateg = {}
    confuse_matrix = { 'Education': {}, 'Sports': {}, 'Politics': {}, 'Medical': {}, 'Economy': {}, 'Agriculture': {},
                       'Enviornment': {}, 'Art': {}, 'Transport': {}, 'Space': {}}
    correct = 0
    for label_doc_pair in testlist:
        label, doc = label_doc_pair.split(' ')
        #print(label, doc)
        for categ in featurepro:
            docbelongcateg.setdefault(categ, priors[categ])

            #print(priors[categ])
            #print(categ, docbelongcateg[categ])
            for word in featurewords & set(jieba.cut(doc, cut_all=True)):
                times = doc.count(word)
                docbelongcateg[categ] += times*featurepro[categ][word]

                #print(categ,docbelongcateg[categ])
        #print(label)
        predict = keyofmaxdictvalues(docbelongcateg)
        confuse_matrix[label].setdefault(predict, 0)
        confuse_matrix[label][predict] += 1
        if label == predict:
            correct += 1

        docbelongcateg ={} #it's very very important to give up useless data of last step!!!!
        #print("Prediction: %s , True Class: %s" %(predict, label))#find biggest posterior prob as predict
    if __name__== '__main__':
        print('correct rate is %f .'%(float(correct)/len(testlist)))#output is float !!
        print("confusion matrix is :")
        print(confuse_matrix)
        macro_microaverage(confuse_matrix)

    return  float(correct)/len(testlist)

'''
def macro_microaverage(confusematrix):
    recall = {}
    class_list = ['Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art',
                  'Transport', 'Space']
    matrix_list = []
    i = 0
    map1 = {}
    for lablei in class_list:
        matrix_list[i] = []
        map1.setdefault(lablei, i)
        i += 1
        j = 0
        map2 = {}
        for lablej in class_list:
            matrix_list[i][j] = confusematrix[lablei].get(lablej, 0)
            map2.setdefault(lablej, j)
            j = j + 1
        print("map2 is")
        print(map2)
    print("map1 is")
    print(map1)
'''


def macro_microaverage(confusematrix):
    recall = {}
    micro_up_right = 0
    micro_up_left = 0
    for true_class in  confusematrix:
        recall.setdefault(true_class, 0.0)
        nume = confusematrix[true_class][true_class]
        micro_up_left += nume
        denomi = sum(confusematrix[true_class].values())
        micro_up_right += (denomi - nume)
        recall[true_class] = nume / denomi
    precise_doni = {}
    class_list = ['Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art',
                  'Transport', 'Space']
    for lablei in class_list:
        precise_doni.setdefault(lablei, 0)

    for categ in confusematrix:
        for judged_class in confusematrix[categ]:
            precise_doni[judged_class] += confusematrix[categ][judged_class]
    micro_down_left = 0
    #micro_down_right =0
    total = 0
    for lablei in class_list:
        #total += precise_doni[lablei]
        micro_down_left += (precise_doni[lablei] - confusematrix[lablei][lablei])
        precise_doni[lablei] = confusematrix[lablei][lablei] / precise_doni[lablei]
    #micro_down_right = total - micro_down_left - micro_up_left - micro_up_right
    micro_recall = micro_up_left / (micro_up_left + micro_up_right)
    micro_precise = micro_up_left / (micro_up_left + micro_down_left)
    macro_recall = sum(recall.values()) / len(recall)
    macro_precise = sum(precise_doni.values()) / len(precise_doni)

    print("Recall is: ")
    print(recall)
    print("Precision is: ")
    print(precise_doni)
    print("Micro-recall is: ")
    print(micro_recall)
    print("Micro-precise is: ")
    print(micro_precise)
    print("Macro-recall is: ")
    print(macro_recall)
    print("Macro-precise is: ")
    print(macro_precise)
    print("\n")



'''
bayesPredict(readDataIntoList('D:\作业\\big.txt'),
             *naiveBayes(*listtoDict(readDataIntoList('D:\作业\\big.txt')),
                        readFeaturewordtoset('D:\作业\\featureword.txt'),1),
             readFeaturewordtoset('D:\作业\\featureword.txt'))
'''

def crossValidate(datalist, turns_num, feawordnum=90):
    '''

    :param datalist: a list of documents , each entry's format is : categ content
    :param turns_num: fold number of cross validation
    :return: average correct rate of cross validation
    '''
    n = len(datalist)
    ave_correc_rate = 0.0
    for i in range(turns_num):
        testlist  = datalist[i*n // turns_num:(i + 1)*n//turns_num]
        trainlist = [i for i in datalist if i not in testlist]
        trai_res = naiveBayes(*listtoDict(trainlist),readFeaturewordtoset(os.path.dirname(os.path.realpath(__file__))+'\\featureword.txt'),1)
        print('round %i '%i)
        ave_correc_rate += bayesPredict(testlist,
                     *trai_res,
                     readFeaturewordtoset(dirname+'\\featureword.txt',feawordnum))

    print('The average correct rate is:  %f ' %(ave_correc_rate/turns_num))
    return ave_correc_rate/turns_num
if __name__ == '__main__':
    crossValidate(readDataIntoList(dirname+'\\big.txt'),5)
    input()