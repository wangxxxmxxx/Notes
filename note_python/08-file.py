# wangxueming
print("文件操作：")
'''
步骤：
1-打开文件，得到文件句柄并赋值给一个变量
2-通过句柄对文件进行操作
3-关闭文件 

打开文件的模式有：
r，只读模式（默认，文件不存在会报错）。
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
如用在网络传输，py3中必须通过二进制传输，打开时无需encoding参数
rb
wb
ab
'''
# f = open("TestFileOper")         # UnicodeDecodeError: 'gbk' codec can't decode byte 0x80 in position 26:
###############################################################################################
f = open("TestFileOper", 'w', encoding="utf-8")     # w 此模式属于创建一个文件，会把原来文件覆盖清空，不可读
for i in range(10):
    f.write(str(i) + " 测试写模式写入\n")
f.close()
##################################################################################################
f = open("TestFileOper", 'r', encoding="utf-8")     # 默认模式为 r
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
print(data2)                      # data2为读到数据，可以理解为读取时存在一个指针，data已经读到了最后
# f.write("测试写入")             # io.UnsupportedOperation: not writable
f.close()
#################################################################################################
f = open("TestFileOper", 'a', encoding="utf-8")    # a 此模式属于创建一个文件，不会把原来文件覆盖清空，追加数据，不可读
# f.read()                                         # io.UnsupportedOperation: not readable
f.write("追加数据")
f.close()
#####################################################################################################
print("循环读取文件：")
# 读取方式一：
f = open("TestFileOper", 'r', encoding="utf-8")
for i in range(5):
    print(f.readline())
f.close()
# 读取方式二：
f = open("TestFileOper", 'r', encoding="utf-8")
for line in f.readlines():
    print(line.strip())
f.close()
# 读取方式三：
f = open("TestFileOper", 'r', encoding="utf-8")
for index, value in enumerate(f.readlines()):
    print(index, "--", value)
f.close()
# 以上方式通过read()和readlines()方式读取会将文件全部加载，消耗内存，读取大文件不合适
print("效率高的读取方式，内存中只存储一行数据：")
f = open("TestFileOper", 'r', encoding="utf-8")
for line in f:
    print(line)
f.close()
#################################################################################
print("file方法：")
f = open("TestFileOper", 'r', encoding="utf-8")
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
######################################################################
print("flush方法：")
import sys, time
for i in range(5):
    sys.stdout.write("#")
    time.sleep(0.3)
    sys.stdout.flush()   # 不添加此语句，会先把要写的数据写入缓冲区，最后缓冲区满了，一下写入
########################################################################
print("\ntruncate方法：")
f = open("TestFileOper", 'a', encoding="utf-8")
f.seek(1)
print(f.truncate(11))      # 将指针后10个字符以后的所有数据截断
f.close()
#########################################################################
# print("读写模式：")                    # 先读在写，不适用seek()会插入数据到最后，先写在读，会位置改写模式覆盖着元数据从指针位置写
# f = open("TestFileOper2", 'r+', encoding="utf-8")       # 文件不存在会报错
# print(f.readline())
# print(f.tell())
# f.write("insert data")  # 这种方式插入会覆盖原来数据，属于改写模式
# print(f.tell())
# print(f.readline())
# f.close()
############################################################################
# print("写读模式：")
# f = open("TestFileOper2", 'w+', encoding="utf-8")       # 文件不存在不报错，会创建一个新的，存在会清空文件
# print(f.readline())
# print(f.tell())
# f.write("insert data")
# f.seek(0)
# print(f.tell())
# print(f.readline())
# f.close()
###################################################################################
print("追加读模式：")
f = open("TestFileOper2", 'a+', encoding="utf-8")       # 文件不存在不报错，会创建一个新的，存在不清空文件
f.seek(0)
print(f.tell())                                         # 刚开始指针便在文件末尾
print(f.readline())
print(f.tell())
f.write("insert data")
f.seek(0)
print(f.tell())
print(f.readline())
f.close()
###################################################################
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
with open("TestFileOper", 'r', encoding="utf-8") as f,  \
        open("TestFileOper2", 'r', encoding="utf-8") as f2:
    print(f.readlines())
    print(f2.readlines())
