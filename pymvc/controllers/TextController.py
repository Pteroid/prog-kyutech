from models.TextModel import TextModel
from views.TextView import TextView


class TextController:
    def __init__(self):
        self.model = TextModel()
        self.view = TextView()
        self.model.observers_add(self.view)

    def start(self):
        self.model.text = self.view.input()
