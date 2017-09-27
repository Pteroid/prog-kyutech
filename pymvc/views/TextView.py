from views.Observer import Observer


class TextView(Observer):
    @staticmethod
    def input():
        return input("Enter text: ")

    def update(self, model):
        if model.text is not None:
            print("You entered:", model.text)
