#!/usr/bin/env python
# Author:Wang Xueming
# 猜年龄 for用法
guess_age = 25
for i in range(3):
    age = int(input("input guess age:"))
    if age > guess_age:
        print("too large")
    elif age < guess_age:
        print("too small")
    else:
        print("you are right")
        break
else:
    print("you tried too many times!")

# for用法
for i in range(2, 10, 3):    # args start stop step, 两个参数step默认为1
    print("loop:", i)

# continue
for i in range(0, 5):
    if i < 3 or i > 3:
        print("loop -- :", i)
    else:
        continue
    print("not run continue")
