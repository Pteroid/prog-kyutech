import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class V_MissileSound implements Observer {
    static V_MissileSound instance = new V_MissileSound();

    private V_MissileSound() {
    }

    public static V_MissileSound getInstance() {
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
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + " が近づく音がする");
        else if (((M_Missile) o).isExist()) {
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "の爆発音が鳴り響く");
            deleteDataSource((M_Missile) o);
        }
    }
}
