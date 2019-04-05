#!/usr/bin/env python
# Author:Wang Xueming

print("flush方法：")
import sys, time
for i in range(10):
    sys.stdout.write("#")
    time.sleep(0.5)
    sys.stdout.flush()   # 不添加此语句，会先把要写的数据写入缓冲区，最后缓冲区满了，一下写入
