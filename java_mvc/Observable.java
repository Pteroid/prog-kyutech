package mvc;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    List<Observer> observers = new ArrayList<Observer>();

    public void Add(Observer observer) // オブサーバーの追加
    {
        observers.add(observer);
    }

    protected void Update() // 更新イベント
    {
        NorifyObservers();
    }

    private void NorifyObservers() // 全オブザーバーに更新を通知
    {
        observers.forEach(observer -> observer.Update(this));
    }
}
