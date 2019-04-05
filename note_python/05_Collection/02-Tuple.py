#!/usr/bin/env python
# Author:Wang Xueming

# 元组,元组其实跟列表差不多，也是存一组数，只不是它一旦创建，便不能再修改，所以又叫只读列表
print("元组：")
names = ("zhao", "qian", "sun", "li")
print(names.count("qian"))          # 它只有2个方法，一个是count,一个是index，当然切片也可以使用，但是修改的动作均无法使用
print(names.index("zhao"))