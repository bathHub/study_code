# -*- coding: GB2312 -*-
# import sys
#
# sys.setdefaultencoding('GB2312')  # 设置默认编码格式为'utf-8'

import os

def eachFile(filepath):
    pathDir = os.listdir(filepath)
    """
    files = [os.path.join('%s\%s' % (filepath, allDir)) for allDir in pathDir ]
    print (files)
    """
    files = []
    for allDir in pathDir:
        child = os.path.join('%s%s' % (filepath, allDir))
        #print(child)
        files.append(child)
    return files

f=eachFile('/workspace/17210240120-project-1/documents collection')
file=open('/workspace/17210240120-project-1/pj1-program/my_file_make/fileName.txt', 'w')
for i in f:
    print(i)
    file.write('%s%s' % (i, os.linesep))



print('=========================read_file==========================')
#read a txt
def readFile(filename):
    fopen = open(filename, 'r') #'r' means read
    for eachLine in fopen:
        print('Have read: '+ eachLine)
    fopen.close()
# readFile('/workspace/17210240120-project-1/documents collection/zC3-Art4.TXT')

#merge files into a bigfile
def mergeFile(files, bigfile):
    fwrite = open(bigfile, 'w')
    for eachFile in files:
        fread = open(eachFile)
        while True:
            s = fread.read(16*1024)
            if not s:
                break
            fwrite.write(s)
        fread.close()
    fwrite.close()
#mergeFile(f, 'D:\作业\big')wrong
def readFileToDes(filename, des):
    fopenw = open(des, 'a')#add
    fopen = open(filename, 'r') #'r' means read
    for eachLine in fopen:
        s = eachLine.strip().replace('\n', '') #空格去掉，换行去掉
        if len(s) != 0:
            print('Have read: ', s)
            fopenw.write('%s%s' % (s, os.linesep))
    fopen.close()
    fopenw.close()
# readFileToDes('/workspace/17210240120-project-1/documents collection/zC11-Space1.txt', '/workspace/17210240120-project-1/pj1-program/my_file_make/check.txt')
