#!/usr/bin/env python
# Author:Wang Xueming

print("写读模式：")
f = open("TestFile", 'w+', encoding="utf-8")       # 文件不存在不报错，会创建一个新的，存在会清空文件
print(f.readline())
print(f.tell())
f.write("insert data")
f.seek(0)
print(f.tell())
print(f.readline())
f.close()