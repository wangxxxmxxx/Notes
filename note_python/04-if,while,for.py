# wangxueming
# 猜年龄1 while用法
guess_age = 25
count = 0
while count < 3:
    age = int(input("input guess age:"))
    if age > guess_age:
        print("too large")
    elif age < guess_age:
        print("too small")
    else:
        print("you are right")
        break
    count += 1
else:
    print("you tried too many times!")

# 猜年龄2 for用法
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


