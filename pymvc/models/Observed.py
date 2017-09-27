class Observed(object):
    def __init__(self):
        self.__observers = set()

    def observers_add(self, *observers):
        for observer in observers:
            self.__observers.add(observer)
            observer.update(self)

    def observer_discard(self, observer):
        self.__observers.discard(observer)

    def observers_notify(self):
        for observer in self.__observers:
            observer.update(self)
