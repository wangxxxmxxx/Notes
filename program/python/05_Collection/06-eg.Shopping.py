#!/usr/bin/env python
# Author:Wang Xueming

# 购物小程序
product_list = [
    ('Iphone', 5800),
    ('Mac Pro', 9800),
    ('Bike', 800),
    ('Watch', 10600),
    ('Coffee', 31)
]
shopping_list = []
salary = input("Input your salary:")
if salary.isdigit():       # 判断是否为数字
    salary = int(salary)
    while True:
        for index, item in enumerate(product_list):            # 注意enumerate方法的使用
            # print(product_list.index(item),item)
            print(index, item)
        user_choice = input("选择要买的产品？>>>:")
        if user_choice.isdigit():
            user_choice = int(user_choice)
            if 0 <= user_choice < len(product_list):           # 得到数组长度的用法
                p_item = product_list[user_choice]
                if p_item[1] <= salary:
                    shopping_list.append(p_item)
                    salary -= p_item[1]
                    print("Added %s into shopping cart,your current balance is \033[31;1m%s\033[0m" % (p_item, salary))       # \033[31;1m%s\033[0m，打印颜色
                else:
                    print("你的余额只剩[%s]，无法购买" % salary)
            else:
                print("product code [%s] is not exist!" % user_choice)
        elif user_choice == 'q':
            print("--------shopping list------")
            for p in shopping_list:
                print(p)
            print("Your current balance:", salary)
            exit()
        else:
            print("invalid option")