#!/usr/bin/env python
# Author:Wang Xueming

# 字符串操作
print("字符串操作：")
motto = "Life is short, I use Python \tTab {name}"
print(motto.capitalize())                # 首字母大写
print(motto.count("i"))                  # 统计字符数
print(motto.center(60, "-"))             # 打印字符60，放中间，然后用-补全
print(motto.endswith("on"))              # 判断是否以什么结尾
print(motto.expandtabs(7))               # 将字符串中tab建转成的空格数，注意\t前无空格不生效
print(motto.find("is"))                  # 查找字符串位置
print(motto[2:])                         # 字符串可以切片
print(motto.format(name='wang xueming')) # 更多见03文件字符串打印
print(motto.format_map({'name': 'wang xueming'}))
print("Life is short, I use Python \tTab %s" % ('wang xueming'))
print('ab23'.isalnum())                  # 是否字符和数字
print('abA'.isalpha())                   # 是否是字母
print(u'123'.isdecimal())                # Python isdecimal() 方法检查字符串是否只包含十进制字符。这种方法只存在于unicode对象。
                                                # 注意:定义一个十进制字符串，只需要在字符串前添加 'u' 前缀即可
print('123'.isdigit())                   # 是否是数字
print(' abc'.isidentifier())             # 是否是合法的标识符
print('23'.isnumeric())                  # 是否是数字
print('ab'.islower())                    # 是否是小写
print(' '.isspace())                     # 是否是空格
print("My Name Is".istitle())            # 是否是标题，首字母大写
print("aa".isprintable())                # tty file,drive file不可打印
print("+".join(["a", "b", "c"]))         # 将列表中数字用前面字符串分隔
print(motto.ljust(50, '*'))              # 打印固定长度并补齐
print(motto.rjust(50, '*'))
print(motto.lower())
print(motto.upper())
print('\naaaa\n'.lstrip())              # 去除换行
print('\naaaa\n'.rstrip())
print('\naaaa\n'.strip())
p = str.maketrans("abcdefg", "1234567")     # 转换
print("I am student".translate(p))
print('aaabbbcccaaa'.replace('a', 'f', 2))  # 替换几个，第三个参数不写替换全部
print('aaaabbbbcccccaaaaa'.rfind("aa"))     # 找到最右边的一个下标
print('a+b+c'.split("+"))                   # 拆分
print('a+b\n+c'.splitlines())               # 按行拆分
print('a+b\n+C'.swapcase())                 # 大小写互换
print('i am student'.title())               # title
print('i am student'.zfill(20))             # 补0
# 遍历
for c in motto:
    print(c, "====", end='')