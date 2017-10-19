class Test():
    def __init__(self):
        self.__x = None

    # 呼び出しが面倒くさいゲッター
    def get_x(self):
        return self.__x
    # 呼び出しが面倒くさいセッター
    def set_x(self, arg):
        self.__x = arg

    # 呼び出しが簡単なゲッター
    @property
    def x(self):
        return self.__x

    # 呼び出しが簡単なセッター
    @x.setter
    def x(self, arg):
        self.__x = arg
        

if __name__ == '__main__':
    test = Test()
    
    test.set_x("めんどい")
    print(test.get_x())

    test.x = "かんたん"
    print(test.x)