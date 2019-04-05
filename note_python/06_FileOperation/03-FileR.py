#!/usr/bin/env python
# Author:Wang Xueming

f = open("TestFile", 'r', encoding="utf-8")     # 默认模式为 r
print("读取一行：")
print(f.readline())               # 读取一行

print("readlines:")
print(f.readlines())                # 读取文件放在列表中，每一行是一个列表元素

f.seek(0)
data = f.read()
data2 = f.read()
print("data:")
print(data)

print("data2")
print(data2)                      # data2未读到数据，可以理解为读取时存在一个指针，data已经读到了最后

# f.write("测试写入")             # io.UnsupportedOperation: not writable
f.close()
#########################################################
print("循环读取文件：")
# 读取方式一：
f = open("TestFile", 'r', encoding="utf-8")
for i in range(5):
    print(f.readline())
f.close()

# 读取方式二：
f = open("TestFile", 'r', encoding="utf-8")
for line in f.readlines():
    print(line.strip())
f.close()

# 读取方式三：
f = open("TestFile", 'r', encoding="utf-8")
for index, value in enumerate(f.readlines()):
    print(index, "--", value)
f.close()

# 以上方式通过read()和readlines()方式读取会将文件全部加载，消耗内存，读取大文件不合适

print("效率高的读取方式，内存中只存储一行数据：")
f = open("TestFile", 'r', encoding="utf-8")
for line in f:
    print(line)
f.close()

###########################################################
print("File方法：")
f = open("TestFile", 'r', encoding="utf-8")
print(f.tell())      # 打印指针所在字符位置
print(f.read(5))     # 读取的是字符数，下面的truncate方法应该是截取的字节数
print(f.tell())
print(f.readline())
print(f.tell())
f.seek(0)            # 将光标指针只移动到指定位置
print(f.tell())
print(f.readline())
print(f.encoding)    # 文件编码
print(f.seekable())  # 文件指针是否可以改变
print(f.readable())  # 文件是否可读等，还有其他类似的方法
f.close()