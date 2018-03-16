import re
import os
import glob



def translate(bytesstr):
    line = bytesstr.strip().decode('gbk', 'ignore')#  .decode('utf-8', 'ignore')  default Unicode
    p2 = re.compile(u'[^\u4e00-\u9fa5]')  # 中文的编码范围是：\u4e00到\u9fa5
    zh = "".join(p2.split(line)).strip()
    zh = "".join(zh.split())
    outStr = zh  # 经过相关处理后得到中文的文本
    return outStr
def joinFile(filename):
    '''

    :param filename: filename
    :return: a string that join together all characters in filename but remove all other symbols
    '''
    fopen = open(filename, 'rb')#add 'b'
    fileList = []
    for line in fopen:
        #line_notbytes_utf8 = line.decode()
        s = translate(line)
        fileList.append(s)
    return ''.join(fileList)

def findLable(stri):
    '''

    :param stri: a string ,there a filename
    :return:
    '''
    for  lable in ['Education', 'Sports', 'Politics', 'Medical', 'Economy', 'Agriculture', 'Enviornment', 'Art', 'Transport', 'Space']:
        if (stri.find(lable)) != -1:
            return  lable
    #stri = 'Others'
    return "Others"

#print(findLable('zC11-Space11.txt'))


def dirTxtToLargeTxt(dir, outputFileName):
    '''从dir目录下读入所有的TXT文件,
    concatenate them in one line (remove other symbol left only characters) and add the class they belong
    (there is a space between the class and the line )将它们写到outputFileName里去'''
    # 如果dir不是目录返回错误

    if not os.path.isdir(dir):
        print("传入的参数有错%s不是一个目录" % dir)

        return False
    # list all txt files in dir
    outputFile = open(outputFileName, "ab")# this "b" is vital

    for txtFile in glob.glob(os.path.join(dir, "*.txt")):
        #print(findLable(txtFile))
        #inputFile = open(txtFile, "rb",)
        outputFile.write(findLable(txtFile).encode(encoding="utf-8"))#outputfile 'ab'
        outputFile.write(' '.encode(encoding="utf-8"))
        inputFile = joinFile(txtFile)

        for line in inputFile:
            #print(type(line))
            #website_bytes_utf8 = website.encode(encoding="utf-8")
            line_bytes_utf8 = line.encode(encoding="utf-8")
            #print(type(line_bytes_utf8))
            outputFile.write(line_bytes_utf8)
        outputFile.write('\n'.encode(encoding="utf-8"))
    outputFile.close()
    return True


dirTxtToLargeTxt('D:\作业\documents collection','D:\作业\\big.txt')

