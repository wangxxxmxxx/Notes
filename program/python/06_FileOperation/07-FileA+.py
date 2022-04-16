#!/usr/bin/env python
# Author:Wang Xueming

print("追加读模式：")
f = open("TestFile", 'a+', encoding="utf-8")       # 文件不存在不报错，会创建一个新的，存在不清空文件
f.seek(0)
print(f.tell())                                         # 刚开始指针便在文件末尾
print(f.readline())
print(f.tell())
f.write("insert data")
f.seek(0)
print(f.tell())
print(f.readline())
f.close()