public class C_Missile {
    static C_Missile instance = new C_Missile();

    private C_Missile() {
    }

    public static C_Missile getInstance() {
        return instance;
    }


    public void launch(MissileID id) {
        M_Missile missile = new M_Missile(id);
        V_MissileDraw.getInstance().addDataSource(missile);
        V_MissileSound.getInstance().addDataSource(missile);
        (new Thread(missile)).start();
    }


}