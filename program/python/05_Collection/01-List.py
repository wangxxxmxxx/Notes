#!/usr/bin/env python
# Author:Wang Xueming

names = []         # 空列表


nums = ["zero", "one", "two", "three", "four", "five"]
print(len(nums))
print(nums)

# 取值
print("取值：")
print(nums[0])
# print(nums["one"]) # 用法错误

# 切片，不包含尾部下标元素 slice
'''
a = [0,1,2,3,4,5,6,7,8,9]
b = a[i:j] 表示复制a[i]到a[j-1]，以生成新的list对象
b = a[1:3] 那么，b的内容是 [1,2]
当i缺省时，默认为0，即 a[:3]相当于 a[0:3]
当j缺省时，默认为len(alist), 即a[1:]相当于a[1:10]
当i,j都缺省时，a[:]就相当于完整复制一份a了

b = a[i:j:s]这种格式呢，i,j与上面的一样，但s表示步进，缺省为1.
所以a[i:j:1]相当于a[i:j]

当s<0时，i缺省时，默认为-1. j缺省时，默认为-len(a)-1
所以a[::-1]相当于 a[-1:-len(a)-1:-1]，也就是从最后一个元素到第一个元素复制一遍。所以你看到一个倒序。

'''
print("切片：")
print(nums[1:3])     # 取下标为1倒2的值的
print(nums[1:6])     # 取下标为1倒5的值
print(nums[-1])      # 取最后一个元素
print(nums[-2])      # 取倒数第二个元素
print(nums[1:6][0])  # 取下标为1倒5的值的下表为0的值，one
print(nums[-3:-1])   # 取倒数第二个和第三个元素 ['three', 'four']
print(nums[-3:])     # 取倒数后三个元素,如果想取最后一个，必须不能写-1，只能这么写 ['three', 'four', 'five']
print(nums[:3])      # 取前三个元素 ['zero', 'one', 'two']
print(nums[::2])     # 类比for循环range方法，start,stop,step ['zero', 'two', 'four']
print(nums[1:5:2])   # ['one', 'three']
print(nums[::-1])   # ['five', 'four', 'three', 'two', 'one', 'zero']

# 追加
print("追加：")
nums.append("seven")
print(nums)

# 插入
print("插入：")
nums.insert(6, "six")
print(nums)

# 删除
print("删除：")
nums.append("del")
nums.append("DEL")
nums.append("del")
print(nums)

nums.remove("del")       # 只删除了第一个相同元素
print(nums)

del nums[8]              # 按照下标删除
print(nums)

nums.pop()                # 删除最后一个
print(nums)

nums.pop(6)               # 按照下标删除
print(nums)

# 查找
print("查找：")
print(nums.index("three"))

# 统计
print("统计：")
nums.append("two")
print(nums.count("two"))

# 反转
print("反转：")
nums.reverse()
print(nums)

# 排序
print("排序：")
nums.sort()       # ascii 排序
print(nums)

# 扩展
print("扩展：")
nums2 = [1, 2, 3]
nums.extend(nums2)
print(nums)

# nums.sort()          # 3.0里不同数据类型不能放在一起排序了

# 循环
print("循环：")
for ele in nums:
    print(ele,"==", end="") # 注意查看方法说明，可以了解方法的各个参数的用法

# 清空
print("\n清空：")
nums.clear()
print(nums)

# 复制
print("浅复制，即引用相同：")
week = ["sunday", "monday", "tuesday", ["am", "pm"]]
# week_copy = week.copy()         # 实现浅复制三种方式
# week_copy = week[:]
week_copy = list(week)
print(week)
print(week_copy)
week[3].append("hour")
print(week)
print(week_copy)

print("深复制：")
import copy
week = ["sunday", "monday", "tuesday", ["am", "pm"]]
# week_copy = copy.copy()            # 等于上述的copy
week_copy = copy.deepcopy(week)
print(week)
print(week_copy)
week[3].append("hour")
print(week)
print(week_copy)