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


    @Override
    public void update(Observable o, Object arg) {
        if (((M_Missile) o).isActive())
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + " 進行中　： " + ((M_Missile) o).getMoveDistance());
        else if (((M_Missile) o).isExist())
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "は壊れた");
        else {
            System.out.println("ミサイル" + ((M_Missile) o).getMissileID() + "は消滅した");
        }
    }
}
