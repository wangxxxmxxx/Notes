#!/usr/bin/env python
# Author:Wang Xueming
f = open("TestFile", 'w', encoding="utf-8")     # w 此模式属于创建一个文件，会把原来文件覆盖清空，不可读
for i in range(10):
    f.write(str(i) + " 测试写模式写入\n")
print("写入完毕！")
f.close()