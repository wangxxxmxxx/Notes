#!/usr/bin/env python
# Author:Wang Xueming

# 输入密文需要导入 import getpass 模块
import getpass             # 在命令行运行有效，在pycharm中不好用

passwd = getpass.getpass("password:")
print(passwd)
