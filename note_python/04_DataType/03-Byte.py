#!/usr/bin/env python
# Author:Wang Xueming

'''
python 3中最重要的新特性可能就是将文本(text)和二进制数据做了更清晰的区分。文本总是用unicode进行编码，
以str类型表示；而二进制数据以bytes类型表示。
在python3中，不能以任何隐式方式将str和bytes类型二者混合使用。不可以将str和bytes类型进行拼接，不能在
str中搜索bytes数据(反之亦然)，也不能将str作为参数传入需要bytes类型参数的函数(反之亦然)。
'''
msg = "测试编码转换"
byteMsg = msg.encode("utf-8")
print(type(byteMsg))
print(repr(byteMsg))    # 内置函数repr可以帮我们在这里显示存储内容
print("字节：", byteMsg)
print("UTF-8，字符串：", byteMsg.decode("utf-8"))
print("iso-8859-1，字符串：", byteMsg.decode("iso-8859-1"))

