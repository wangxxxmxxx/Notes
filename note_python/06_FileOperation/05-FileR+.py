#!/usr/bin/env python
# Author:Wang Xueming

print("读写模式：")                    # 先读在写，不适用seek()会插入数据到最后，先写在读，会位置改写模式覆盖着元数据从指针位置写
f = open("TestFile", 'r+', encoding="utf-8")       # 文件不存在会报错
print(f.readline())
print(f.tell())
f.write("insert data")  # 这种方式插入会覆盖原来数据，属于改写模式
print(f.tell())
print(f.readline())
f.close()