#!/usr/bin/env python
# Author:Wang Xueming
import time
'''
特性:
1、减少重复代码
2、使程序变的可扩展
3、使程序变得易维护
'''


# 函数名
def say_hi():
    print("Hello")


say_hi()    # 调用函数


def logger():                                 # 函数书写规范，定义前后要空两行
    time_format = '%Y-%m-%d %X'
    time_current = time.strftime(time_format)
    with open('TestFile','a+') as f:
        f.write('%s end action\n' % time_current)


def test1():
    print('in the test1')
    logger()


def test2():
    print('in the test2')
    logger()


def test3():
    print('in the test3')
    logger()


test1()
test2()
test3()
