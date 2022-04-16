#!/usr/bin/env python
# Author:Wang Xueming

num = input("请输入数字：")
num = int(num)

if num<10:
    print("输入数字小于10")
elif 10 <= num <= 15:
    print("输入数字在10和15之间")
else:
    print("输入数字大于15")