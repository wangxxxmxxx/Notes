#!/usr/bin/env python
# Author:Wang Xueming
print("参数：")


def test(x, y):
    print(x)
    print(y)


test(y=2, x=1)        # 关键字调用，与形参顺序无关
test(1, 2)            # 位置调用，与形参一一对应
# test(x=2, 3)        # 错误调用方式 positional argument follows keyword argument  关键参数是不能写到位置参数前面的
test(3, y=2)


print("默认参数：")


def conn(host, port=3306):    # 定义时非默认参数必须要在默认参数的前面
    print(host, "=====", port)   # 默认参数特点：调用函数的时候，默认参数非必须传递


conn("123")
conn("456", 8888)


print("参数组：")


def test(*args): # *args:接受N个位置参数，转换成元组形式
    print(args)


test(1, 2, 3, 4, 5, 5)
test(*[1, 2, 3, 4, 5, 5])   # args=tuple([1,2,3,4,5])
test(*(1, 2, 3, 4, 5, 5))


def test1(x, *args):
    print(x)
    print(args)


test1(1, 2, 3, 4, 5, 6, 7)


# ** kwargs接受N个关键字参数，转换成字典的方式
def test2(**kwargs):
    print(kwargs)
    print(kwargs['name'])
    print(kwargs['age'])
    # print(kwargs['sex'])


test2(name='alex', age=8, sex='F')
test2(**{'name': 'alex', 'age': 8})


def test3(name, **kwargs):
    print(name)
    print(kwargs)


test3('alex', age=18, sex='m')


def test4(name, age=18, **kwargs):
    print(name)
    print(age)
    print(kwargs)


test4('alex', age=34, sex='m', hobby='tesla')


def test4(name, age=18, *args, **kwargs):
    print(name)
    print(age)
    print(args)
    print(kwargs)
    logger1("TEST4")


def logger1(source):              # 注意方法调用方法，方法的定义可以在调用之后，但是实际调用方法时要定义在调用前
    print("from %s" % source)


test4('alex', age=34, sex='m', hobby='tesla') # 实际调用方法时一定要先定义
test4('alex', sex='m', hobby='tesla')
