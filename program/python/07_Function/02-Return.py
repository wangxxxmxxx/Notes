#!/usr/bin/env python
# Author:Wang Xueming
print("函数返回值：")


def test4():
    pass # 该处的 pass 便是占据一个位置，因为如果定义一个空函数程序会报错，当你没有想好函数的内容是可以用 pass 填充，使程序可以正常运行。


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
