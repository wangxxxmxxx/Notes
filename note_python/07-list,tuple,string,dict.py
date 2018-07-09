# wangxueming
###########################################################
print("列表：")
names = []         # 空列表
print(names)
nums = ["zero", "one", "two", "three", "four", "five"]
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
所以a[::-1]相当于 a[-1:-len(a)-1:-1]，也就是从最后一个元素到第一个元素复制一遍。所以你看到一个倒序的东东。
'''
print("切片：")
print(nums[1:3])     # 取下标为1倒2的值的
print(nums[1:6])     # 取下标为1倒5的值
print(nums[-1])      # 取最后一个元素
print(nums[-2])      # 取倒数第二个元素
print(nums[1:6][0])  # 取下标为1倒5的值的下表为0的值，one
print(nums[-3:-1])   # 取倒数第二个和第三个元素
print(nums[-3:])     # 取倒数后三个元素,如果想取最后一个，必须不能写-1，只能这么写
print(nums[:3])      # 取前三个元素
print(nums[::2])     # 类比for循环range方法，start,stop,step
print(nums[1:5:2])   # ['one', 'three']
print(nums[::-1])   # ['one', 'three']
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
nums.append("del")
print(nums)
nums.remove("del")       # 只删除了后一个相同元素
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
    print(ele)
# 清空
print("清空：")
nums.clear()
print(nums)
# 复制
print("浅复制：")
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
######################################################################
# 元组,元组其实跟列表差不多，也是存一组数，只不是它一旦创建，便不能再修改，所以又叫只读列表
print("元组：")
names = ("zhao", "qian", "sun", "li")
print(names.count("qian"))          # 它只有2个方法，一个是count,一个是index，当然切片也可以使用，但是修改的动作均无法使用
print(names.index("zhao"))
################################################################################
# 购物小程序
# product_list = [
#     ('Iphone', 5800),
#     ('Mac Pro', 9800),
#     ('Bike', 800),
#     ('Watch', 10600),
#     ('Coffee', 31)
# ]
# shopping_list = []
# salary = input("Input your salary:")
# if salary.isdigit():       # 判断是否为数字
#     salary = int(salary)
#     while True:
#         for index, item in enumerate(product_list):            # 注意enumerate方法的使用
#             # print(product_list.index(item),item)
#             print(index, item)
#         user_choice = input("选择要买的产品？>>>:")
#         if user_choice.isdigit():
#             user_choice = int(user_choice)
#             if 0 <= user_choice < len(product_list):           # 得到数组长度的用法
#                 p_item = product_list[user_choice]
#                 if p_item[1] <= salary:
#                     shopping_list.append(p_item)
#                     salary -= p_item[1]
#                     print("Added %s into shopping cart,your current balance is \033[31;1m%s\033[0m" % (p_item, salary))       # \033[31;1m%s\033[0m，打印颜色
#                 else:
#                     print("你的余额只剩[%s]，无法购买" % salary)
#             else:
#                 print("product code [%s] is not exist!" % user_choice)
#         elif user_choice == 'q':
#             print("--------shopping list------")
#             for p in shopping_list:
#                 print(p)
#             print("Your current balance:", salary)
#             exit()
#         else:
#             print("invalid option")
#########################################################################################################
# 字符串操作
print("字符串操作：")
motto = "Life is short, I use Python \tTab {name}"
print(motto.capitalize())                # 首字母大写
print(motto.count("i"))                  # 统计字符数
print(motto.center(60, "-"))             # 打印字符60，放中间，然后用-补全
print(motto.endswith("on"))              # 判断是否以什么结尾
print(motto.expandtabs(7))               # 将字符串中tab建转成的空格数，注意\t前无空格不生效
print(motto.find("is"))                  # 查找字符串位置
print(motto[2:])                         # 字符串可以切片
print(motto.format(name='wang xueming')) # 更多见03文件字符串打印
print(motto.format_map({'name': 'wang xueming'}))
print('ab23'.isalnum())                  # 是否字符和数字
print('abA'.isalpha())                   # 是否是字母
print(u'123'.isdecimal())                # Python isdecimal() 方法检查字符串是否只包含十进制字符。这种方法只存在于unicode对象。
                                                # 注意:定义一个十进制字符串，只需要在字符串前添加 'u' 前缀即可
print('123'.isdigit())                   # 是否是数字
print(' abc'.isidentifier())             # 是否是合法的标识符
print('23'.isnumeric())                  # 是否是数字
print('ab'.islower())                    # 是否是小写
print(' '.isspace())                     # 是否是空格
print("My Name Is".istitle())            # 是否是标题，首字母大写
print("aa".isprintable())                # tty file,drive file不可打印
print("+".join(["a", "b", "c"]))         # 将列表中数字用前面字符串分隔
print(motto.ljust(50, '*'))              # 打印固定长度并补齐
print(motto.rjust(50, '*'))
print(motto.lower())
print(motto.upper())
print('\naaaa\n'.lstrip())              # 去除换行
print('\naaaa\n'.rstrip())
print('\naaaa\n'.strip())
p = str.maketrans("abcdefg", "1234567")     # 转换
print("I am student".translate(p))
print('aaabbbcccaaa'.replace('a', 'f', 2))  # 替换几个，第三个参数不写替换全部
print('aaaabbbbcccccaaaaa'.rfind("aa"))     # 找到最右边的一个下标
print('a+b+c'.split("+"))                   # 拆分
print('a+b\n+c'.splitlines())               # 按行拆分
print('a+b\n+c'.swapcase())                 # 大小写互换
print('i am student'.title())               # title
print('i am student'.zfill(20))             # 补0
#########################################################################################
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
