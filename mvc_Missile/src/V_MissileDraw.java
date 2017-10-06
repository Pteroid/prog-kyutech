import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class V_MissileDraw implements Observer {
    static V_MissileDraw instance = new V_MissileDraw();

    private V_MissileDraw() {
    }

    public static V_MissileDraw getInstance() {
        return instance;
    }

    List<M_Missile> missileSources = new ArrayList<M_Missile>();

    public void addDataSource(M_Missile ms) {
        if (missileSources.contains(ms))
            System.out.println("System error occurred. Please fix bug.");
        missileSources.add(ms);
        ms.addObserver(getInstance());
    }
    private void deleteDataSource(M_Missile ms){
        missileSources.remove(ms);
        (ms).deleteObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (((M_Missile) o).isActive())
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + " 進行中　： " + ((M_Missile) o).getMoveDistance());
        else if (((M_Missile) o).isExist())
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "は壊れた");
        else {
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "は消滅した");
            deleteDataSource((M_Missile) o);
        }
    }
}
