import re
a='Beautiful, is; better*than\nugly'
# 四个分隔符为：,  ;  *  \n
x= re.split(',|; |\*|\n',a)
print(x)

def readDataIntoDict(filename):
    '''

    :param filename: the merged big filename
    :return: a dictionary of list,every list contains all the merged long lines of initial files
    one file changed into a long line
    '''
    fopen = open(filename, 'r', encoding="utf-8")
    dataDict = {'Others': [], 'Education': [], 'Sports': [], 'Politics': [], 'Medical': [],
                'Economy': [], 'Agriculture': [], 'Enviornment': [], 'Art': [], 'Transport': [], 'Space': []}
    for line in fopen.readlines():  #依次读取每行
        label, content = line.split()
        print('label:'+label, 'content:'+content)
        dataDict[label].append(content)
   # for label in ['Others', 'Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art', 'Transport', 'Space']:
        #print(len(dataDict[label]))
    del dataDict['Others']
    return dataDict

f=readDataIntoDict('/workspace/17210240120-project-1/pj1-program/big.txt')
print(f)