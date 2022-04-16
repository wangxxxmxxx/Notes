#!/usr/bin/env python
# Author:Wang Xueming

'''
with语句
为了避免打开文件后忘记关闭，可以通过管理上下文，即：
with open('log','r') as f:

    ...
如此方式，当with代码块执行完毕时，内部会自动关闭并释放文件资源。

在Python 2.7 后，with又支持同时对多个文件的上下文进行管理，即：
with open('log1') as obj1, open('log2') as obj2:
    pass
'''
print("with 使用：")
with open("TestFile", 'r', encoding="utf-8") as f, \
        open("TestFile2", 'r', encoding="utf-8") as f2:
    print(f.readlines())
    print(f2.readlines())
