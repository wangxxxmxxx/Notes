#!/usr/bin/env python
# Author:Wang Xueming

# 集合，无序的
print("集合：")
s = set([3, 5, 9, 10])    # 创建一个数值集合
t = set([5, 66, 77])
print(s)
print(t)

str_set = set("Hello")    # 创建一个唯一字符的集合,天然去重
print(str_set)

a = t | s                 # t 和 s的并集
print(a)

b = t & s                 # t 和 s的交集
print(b)

c = t - s                 # 求差集（项在t中，但不在s中）
print(c)

d = t ^ s                 # 对称差集（项在t或s中，但不会同时出现在二者中）
print(d)

# 基本操作：
t.add('x')                # 添加一项
print(t)

s.update([10, 37, 42])    # 在s中添加多项
print(s)

print("删除：")
t.remove('x')             # 使用remove()可以删除一项,不存在会报错 Remove an element from a set; it must be a member.
print(t)                          # If the element is not a member, raise a KeyError.
t.discard(1)              # Remove an element from a set if it is a member. If the element is not a member, do nothing.
print(t)

t.pop()
print(t)
# del t[0]                # 不支持 TypeError: 'set' object doesn't support item deletion
print(len(s))             # set的长度

print('x' in s )          # 测试x是否是s的成员
print('x' not in s)       # 测试x是否不是s的成员

print(s.issubset(t))
print(s <= t)             # 测试是否s中的每一个元素都在t中

print(s.issuperset(t))
print(s >= t)             # 测试是否t中的每一个元素都在s中

print(s.union(t))
print(s | t)              # 返回一个新的set包含s和t中的每一个元素

print(s.intersection(t))
print(s & t)              # 返回一个新的set包含s和t中的公共元素

print(s.difference(t))
print(s - t)              # 返回一个新的set包含s中有但是t中没有的元素

print(s.symmetric_difference(t))
print(s ^ t)              # 返回一个新的set包含s和t中不重复的元素

print(s.copy())           # 返回set “s”的一个浅复制

s = set([1, 2, 3])
t = set([4, 5, 6])
print(s.isdisjoint(t))   # 如果连个集合不存在交集返回true
