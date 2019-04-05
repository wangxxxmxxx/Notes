#!/usr/bin/env python
# Author:Wang Xueming
# 用户输入：
var1 = input("变量一：")
var2 = input()
# python2 raw_input() --> python3 input()
# python2 中也包含input，但是没什么用，需要输入“变量:值”的形式
var3 = input(""" 输入变量三
请输入：""")
print(type(var3)) # 输入的默认的是字符串<class 'str'>
var3 = int(var3)  # Python是强类型语言，需要强转类型
print(type(var3))
print(var1, "---", var2, "---", var3)

info = """
-------info--------
var1:%s
var2:%s
var3:%d
-------end---------
""" % (var1, var2, var3) # 字符串是 %s;整数 %d;浮点数%f
print(info)

info2 = """
-------info2--------
var1:{var1}
var2:{var2}
var3:{var3} === {_var2}
-------end---------
""" .format(var1=var1,
            var2=var2,
            _var2=var2,
            var3=var3)
print(info2)

info3 = """
-------info3--------
var1:{0}
var2:{1}
var3:{2} =============== {1}
-------end---------
""" .format(var1, var2, var3)
print(info3)

# 注意以上三种格式化方式同样适用于单行字符串，单行字符串和多行字符串是相同的
