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



    @Override
    public void update(Observable o, Object arg) {
        if (((M_Missile) o).isActive())
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + " が近づく音がする");
        else if (((M_Missile) o).isExist()) {
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "の爆発音が鳴り響く");
        }
    }
}
