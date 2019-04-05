#!/usr/bin/env python
# Author:Wang Xueming
# Python的强大之处在于他有非常丰富和强大的标准库和第三方库，几乎你想实现的任何功能都有
# 相应的Python库支持，以后的课程中会深入讲解常用到的各种库，现在，我们先来象征性的看2个简单的
import sys
print("Python path:")
print(sys.path)    # 打印python路径
'''
['F:\\Notes\\note_python', 
'E:\\InstallPackage\\python-3.5.0-embed-amd64\\python35.zip', 
'E:\\InstallPackage\\python-3.5.0-embed-amd64\\DLLs', 
'E:\\InstallPackage\\python-3.5.0-embed-amd64\\lib', 
'E:\\InstallPackage\\python-3.5.0-embed-amd64']
'''

print("Args:")
print(sys.argv)    # 打印脚本和参数列表
print(sys.argv[0])    # 取第0个值
# ['F:/Notes/note_python/01-library.py']
####################################################################

import os
print("Exe dir:")
dir_res = os.system("dir")  # 执行命令，保存执行的返回值
print("Result:", dir_res)
dir_res_location = os.popen("dir")    # 保存执行显示内容的地址
print("Location:", dir_res_location)
dir_res_location_content = dir_res_location.read()    # 读取内容
print("ResultContent:", dir_res_location_content)
if not os.path.exists("newDir"):
    print("create dir:")
    os.mkdir("newDir")      # 创建目录

########################################################################################
# 导入自己写的模块，保存在成 模块名.py 即可
# 然后导入，注意模块的位置需要在 sys.path 下
