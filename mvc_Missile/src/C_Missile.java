public class C_Missile {
    static C_Missile instance = new C_Missile();

    private C_Missile() {
    }

    public static C_Missile getInstance() {
        return instance;
    }

    //名前識別用のID。これは本来コントローラーでなく名前管理用のモデルに書くべきだが、面倒なので省略。
    int missileID = 0;

    public void launch() {
        M_Missile missile = new M_Missile(missileID++);
        V_MissileDraw.getInstance().addDataSource(missile);
        V_MissileSound.getInstance().addDataSource(missile);
        (new Thread(missile)).start();
    }


}