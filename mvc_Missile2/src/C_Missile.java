public class C_Missile {
    static C_Missile instance = new C_Missile();

    private C_Missile() {
    }

    public static C_Missile getInstance() {
        return instance;
    }

    int missileID = 0;


    public void launch() {
        func();
    }

    private void func(){
        M_Missile missile = new M_Missile(missileID++);
        missile.addObserver(V_MissileDraw.getInstance());
        missile.addObserver(V_MissileSound.getInstance());
        (new Thread(missile)).start();
    }


}
