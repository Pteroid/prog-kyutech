class Model:
    def __init__(self):
        self.__observer = None
        self.__text = None

    def add_observer(self, observer):
        self.__observer = observer

    def notify_observer(self):
        self.__observer.update(self)

    @property
    def text(self):
        return self.__text

    @text.setter
    def text(self, text):
        self.__text = text
        self.notify_observer()


class View:
    @staticmethod
    def input():
        return input("Enter text: ")

    @staticmethod
    def update(model):
        print("You entered: ", model.text)


class Controller:
    def __init__(self):
        self.model = Model()
        self.view = View()
        self.model.add_observer(self.view)

    def start(self):
        self.model.text = self.view.input()


if __name__ == '__main__':
    Controller().start()
