import loadData
import naiveBayes
import matplotlib.pylab as pl
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
def wordnumTocorrectRate():
    x = []
    y = []
    y1 = []
    i = 3
    while i <= 200:
        x.append(i)
        rate = naiveBayes.crossValidate(naiveBayes.readDataIntoList('D:\作业\\big.txt'), 4, i)
        y.append(rate)
        rate1 = loadData.crossValidate(*loadData.listToNumpy(readFeaturewordtoset('D:\作业\\featureword.txt',i),
                                                             loadData.readDataIntoList('D:\作业\\big.txt')),4)
        y1.append(rate1)
        i += 5
    plot1 = pl.plot(x, y,'or',label='Bayes')  # use pylab to plot x and y
    plot2 = pl.plot(x, y1,'*g',label='Logistic')

    pl.title('feature word number VS correct rate')  # give plot a title
    pl.xlabel('word number')  # make axis labels
    pl.ylabel('correct rate')
    pl.xlim(0, 200)  # set axis limits
    pl.ylim(0.0, 1.0)
    pl.legend()
    pl.show()
if __name__ == '__main__':
    wordnumTocorrectRate()