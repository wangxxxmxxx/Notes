# wangxueming
print("文件操作：")
'''
步骤：
1-打开文件，得到文件句柄并赋值给一个变量
2-通过句柄对文件进行操作
3-关闭文件 

打开文件的模式有：
r，只读模式（默认）。
w，只写模式。【不可读；不存在则创建；存在则删除内容；】
a，追加模式。【可读；   不存在则创建；存在则只追加内容；】
"+" 表示可以同时读写某个文件
r+，可读写文件。【可读；可写；可追加】
w+，写读
a+，同a
"U"表示在读取时，可以将 \r \n \r\n自动转换成 \n （与 r 或 r+ 模式同使用）
rU
r+U
"b"表示处理二进制文件（如：FTP发送上传ISO镜像文件，linux可忽略，windows处理二进制文件时需标注）
rb
wb
ab
'''
# f = open("TestFileOper")         # UnicodeDecodeError: 'gbk' codec can't decode byte 0x80 in position 26:
###############################################################################################
f = open("TestFileOper", 'w', encoding="utf-8")     # w 此模式属于创建一个文件，会把原来文件覆盖清空，不可读
for i in range(10):
    f.write(str(i) + " 测试写模式写入\n")
##################################################################################################
f = open("TestFileOper", 'r', encoding="utf-8")     # 默认模式为 r
print(f.readline())               # 读取一行
print(f.readlines)                # 读取文件放在列表中，每一行是一个列表元素
data = f.read()
data2 = f.read()
print(data)
print(data2)                      # data2为读到数据，可以理解为读取时存在一个指针，data已经读到了最后
# f.write("测试写入")             # io.UnsupportedOperation: not writable
#################################################################################################
f = open("TestFileOper", 'a', encoding="utf-8")    # a 此模式属于创建一个文件，不会把原来文件覆盖清空，追加数据，不可读
# f.read()                                         # io.UnsupportedOperation: not readable
f.write("追加数据")
#####################################################################################################
print("循环读取文件：")
# 读取方式一：
f = open("TestFileOper", 'r', encoding="utf-8")
for i in range(5):
    print(f.readline())
# 读取方式二：
f = open("TestFileOper", 'r', encoding="utf-8")
for line in f.readlines():
    print(line.strip())
# 读取方式三：
f = open("TestFileOper", 'r', encoding="utf-8")
for index, value in enumerate(f.readlines()):
    print(index, "--", value)
# 以上方式通过read()和readlines()方式读取会将文件全部加载，消耗内存，读取大文件不合适
print("效率高的读取方式，内存中只存储一行数据：")
f = open("TestFileOper", 'r', encoding="utf-8")
for line in f:
    print(line)

f.close()

