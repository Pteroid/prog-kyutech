public class C_Missile extends Thread {

    int missileID = -100;

    public C_Missile(int id) {
        missileID = id;
        start();
    }

    private void launch() {
        M_Missile missile=new M_Missile(missileID++);
        missile.addObserver(V_MissileDraw.getInstance());
        missile.addObserver(V_MissileSound.getInstance());
        missile.runFunction();

    }

    public void run(){
        launch();
    }


}
