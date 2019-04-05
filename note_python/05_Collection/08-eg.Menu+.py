#!/usr/bin/env python
# Author:Wang Xueming

# 菜单应用
print("菜单应用：")
data = {
    '北京': {
        "昌平": {
            "沙河": ["oldboy", "test"],
            "天通苑": ["链家地产", "我爱我家"]
        },
        "朝阳": {
            "望京": ["奔驰","陌陌"],
            "国贸": {"CICC","HP"},
            "东直门": {"Advent", "飞信"},
        },
        "海淀": {},
    },
    '山东': {
        "德州": {},
        "青岛": {},
        "济南": {}
    },
    '广东':{
        "东莞": {},
        "常熟": {},
        "佛山": {},
    },
}
exit_flag = False
current_layer = data
layers = [data]
while not exit_flag:
    for k in current_layer:
        print(k)
    choice = input(">>:")
    if choice == "b":
        if len(layers)>0:
            current_layer = layers[-1] # 取最后一个元素
            # print("change to laster", current_layer)
            layers.pop()
    elif choice not in current_layer:
        continue
    else:
        layers.append(current_layer)
        current_layer = current_layer[choice]

