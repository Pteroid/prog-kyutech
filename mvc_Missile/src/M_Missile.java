import java.util.Observable;

import static java.lang.Thread.sleep;

public class M_Missile extends Observable implements Runnable{

    public M_Missile(int id) {
        missileID = id;
        generate();
    }

    final int limitDistance = 50;

    //以下、プロパティ
    boolean activation = false;
    boolean existence = true;
    int moveDistance = 0;
    int missileID;

    //以下、アクセサ
    private void setActive(boolean bl) {
        if (activation != bl) {
            activation = bl;
            setChanged();
            notifyObservers();
        }
    }

    private void generate() {
        setActive(true);
        //System.out.println("generated : missile" + missileID);
    }

    public void destroy() {
        setActive(false);
        //System.out.println("destroyed : missile" + missileID);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setLost();
    }

    public boolean isActive() {
        return activation;
    }

    private void setMoveDistance(int distance) {
        if (moveDistance != distance) {
            moveDistance = distance;
            setChanged();
            //System.out.println("move distance : missile" + missileID + " = " + getMoveDistance());
            notifyObservers();
        }
    }

    public int getMoveDistance() {
        return moveDistance;
    }

    public int getMissileID() {
        return missileID;
    }

    private void setLost() {
        if (existence) {
            existence = false;
            setChanged();
            notifyObservers();
        }
    }

    public boolean isExist() {
        return existence;
    }


    //以下、主な動作
    private void fly() {
        if (getMoveDistance() >= limitDistance)
            destroy();
        else
            setMoveDistance(getMoveDistance() + 1);
    }

    public void run() {
        while (isActive()) {
            fly();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
