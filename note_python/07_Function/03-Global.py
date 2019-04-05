#!/usr/bin/env python
# Author:Wang Xueming
print("局部全局变量：")
school = "DL"


def change_name(name1):
    global school          # 加上此句代表要处理的是外部的全局变量
    school = "dl"          # 外面不定义该变量也可以，外部即可访问，但是这种加global的方式不建议用，逻辑复杂且混乱
    print("before change", name, school)
    name1 = "wxm"               # 这个函数就是这个变量的作用域
    print(name1)


name = "WXM"
change_name(name)
print(name) # 传值，未改变
print(school)

names = ["Jim", "Jack", "Rain"]


def change_name():                     # 传递引用可以改值
    names[0] = "Correct"
    print("inside func", names)


change_name()
print(names)
