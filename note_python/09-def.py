# wangxueming
import time
print("函数：")
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
    with open('TestFileOper2','a+') as f:
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

#############################################################################################
print("函数返回值：")


def test4():
    pass


def test5():
    return 0


def test6():
    return 0, 'hello', ['a', 'b', 'c'], {'name': 'alex'}


x = test4()
y = test5()
z = test6()
print(x)        # 返回值数=0，返回None
print(y)        # 返回值数=1，返回Object
print(z)        # 返回值数>1，返回tuple

##############################################################################
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

print("参数组：")


# *args:接受N个位置参数，转换成元组形式
def test(*args):
    print(args)


test(1, 2, 3, 4, 5, 5)
test(*[1, 2, 4, 5, 5])   # args=tuple([1,2,3,4,5])


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
    logger("TEST4")


def logger(source):
    print("from %s" % source)


test4('alex', age=34, sex='m', hobby='tesla')
