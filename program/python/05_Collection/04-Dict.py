#!/usr/bin/env python
# Author:Wang Xueming

# 字典
print("字典：")
info = {
    '1': 'one',
    '2': 'two',
    '3': 'three'
}

print(info)
print(info["1"])

# 修改
print("修改：")
info['2'] = '二'

#增加
print("增加：")
info['4'] = 'four'   # 如果存在即修改，否则创建一条
print(info)

# 删除
print("删除：")
del info['4']
print(info)
info.pop('3')        # 必须指定删除的元素
print(info)
info.popitem()       # 随机删除
print(info)

# 查找
info = {
    '1': 'one',
    '2': 'two',
    '3': 'three'
}
print("查找：")
print(info)
# print("元素：", info['4'])     # 不存在会报错
print("元素：", info.get('4'))  # 安全获取
print('2' in info)              # python2中info.hasKey('2'),py3取消

# 其他
print("其他：")
print(info.values())
print(info.keys())
print(info.setdefault("1", "一"))      # 存在则返回，不存在则创建
print(info.setdefault("4", "four"))
new_dict = {
    '1': '壹',
    '5': 'five'
}
info.update(new_dict)                  # 交叉的替换，不交叉的新增
print(info)

print(info.items())                    # 转列表
print(dict.fromkeys([1, 2, 3], '1'))   # 初始化，注意使用此种初始化，如果值是引用类型，是同一个地址

# 循环
print("循环：")
for key in info:
    print(key, info[key])

for k, v in info.items():             # 会先把dict转成list,数据里大时莫用
    print(k, v)