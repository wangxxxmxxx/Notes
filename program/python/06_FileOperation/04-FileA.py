#!/usr/bin/env python
# Author:Wang Xueming

f = open("TestFile", 'a', encoding="utf-8")    # a 此模式属于创建一个文件，不会把原来文件覆盖清空，追加数据，不可读
# f.read()                                         # io.UnsupportedOperation: not readable
f.write("追加数据")
print("追加数据完毕")
f.close()

print("truncate方法：")
f = open("TestFile", 'a', encoding="utf-8")
f.seek(5)   # truncate方法不受此方法影响
print(f.truncate(11))      # 将指针后10个字符以后的所有数据截断
f.close()