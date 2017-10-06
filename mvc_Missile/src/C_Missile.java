public class C_Missile extends Thread {

    int missileID = -100;

    public C_Missile(int id) {
        missileID = id;
        start();
    }

    private void launch() {
        M_Missile missile=new M_Missile(missileID++);
        V_MissileDraw.getInstance().addDataSource(missile);
        V_MissileSound.getInstance().addDataSource(missile);
        missile.runFunction();

    }

    public void run(){
        launch();
    }


}
