import jieba
import math
import splitdict
from itertools import chain#lst = list(chain(*L))


def buildClassWorddictandCompuNdot(bigdict):
    """

    :param bigdict: a dict of lists(value) of classes(key),each list contains all longline of the initial documents
    :return: a dict of dict classworddict[key][word],a dict whose value is docs  num contain a word(ntxdot),
    a dict whose value is docs  num belong to a class(ndotcx),total num of docs
    """
    #print( len([x for j in bigdict.values() for x in j]))1989 docs
    classworddict = {}
    ndotcx = {}
    n = 0 #total num of docs
    for key in bigdict:
        classworddict[key] ={}  #{'Others': 0, 'Education': 0, 'Sports': 0, 'Politics': 0, 'Medical': 0,
               # 'Economy': 0, 'Agriculture': 0, 'Enviornment': 0, 'Art': 0, 'Transport': 0, 'Space': 0}
        #ndotcx.setdefault(key, len(bigdict[key]))
        #ndotcx.setdefault(key,len(bigdict[key]))TypeError: tuple indices must be integers or slices, not dict
        ndotcx[key] = len(bigdict[key])
        n += ndotcx[key]
        for longline in bigdict[key]: #each longline is a document

            for word in set(jieba.cut(longline, cut_all= True)):
                classworddict[key].setdefault(word, 0)#if world is not in dict ,add world and set it 0
                classworddict[key][word] += 1
        ntxdot = {}
        for word in set( jieba.cut(''.join([x for j in bigdict.values() for x in j]))):#list(chain(bigdict.values())) change list of list to list
            for key in classworddict:
                #print(word, key)
                ntxdot.setdefault(word,0)
                ntxdot[word] += classworddict[key].get(word,0)

    #print(ntxdot)
    return [classworddict, ndotcx, ntxdot, n]
buildClassWorddictandCompuNdot(splitdict.readDataIntoDict('D:\作业\\big.txt'))

def compuMI(classworddict, ndotcx, ntxdot, n ):
    '''

    :param classworddict: a dict of dicts gives number of docs contains a word and belong to a class
    :param ndotcx:a dict whose value is docs  num belong to a class(ndotcx),
    :param ntxdot: a dict whose value is docs  num contain a word(ntxdot)
    :param n:total num of docs
    :return:a dict of mutual information of all world in all class
    '''
    def compuLog(n, ntxcx, ntxdot, ndotcx):
        return 1.0 * ntxcx / n * ((math.log((n * ntxcx) / (ntxdot * ndotcx))) / math.log(2.0))
    mi = {}
    for categ in classworddict:
        mi[categ] = {}

        for word in ntxdot:
            n1dot =ntxdot[word]
            ndot1 = ndotcx[categ]
            ndot0 = n - ndot1
            n0dot = n - n1dot
            n11 = classworddict[categ].get(word, 0)
            n10 = n1dot - n11
            n01 = ndot1 - n11
            n00 = n - ntxdot[word] - n01# n - nt1dot - nt0c1
            mi[categ].setdefault(word, 0)
            if n11 > 0 and n10 > 0 and n01 > 0 and n00 > 0:
                mi[categ][word] = compuLog(n, n11, n1dot, ndot1) + compuLog(n, n10, n1dot, ndot0)\
                              + compuLog(n, n01, n0dot, ndot1) + compuLog(n, n00, n0dot, ndot0)
            elif n11 > 0 and n10 == 0:
                print(word, categ, "n11 is %d , n10 is %d \n" % (n11, n10))
    for categ in mi:
        print(categ)
        count = 0
        print(mi[categ])
    return mi

#compuMI(*buildClassWorddictandCompuNdot(splitdict.readDataIntoDict('D:\作业\\big.txt')))


def selectFeatureword(mi, filename, featurenum):
    fwrite = open(filename, 'a')
    for categ in mi:
        print(categ)
        count = 0
       # print(mi[categ])
        for world, miv in sorted(mi[categ].items(), key = lambda item : item[1],reverse = True):
            if count <= featurenum:
                count += 1 #count
                print(categ, world, miv)
                fwrite.write(world + ' ')
            else:
                fwrite.write('\n')
                break


selectFeatureword(compuMI(*buildClassWorddictandCompuNdot(splitdict.readDataIntoDict('D:\作业\\big.txt'))),
                  'D:\作业\\featureword.txt',100)














