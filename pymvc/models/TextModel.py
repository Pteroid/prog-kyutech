from models.Observed import Observed


class TextModel(Observed):
    def __init__(self):
        super().__init__()
        self.__text = None

    @property
    def text(self):
        return self.__text

    @text.setter
    def text(self, text):
        self.__text = text
        self.observers_notify()
