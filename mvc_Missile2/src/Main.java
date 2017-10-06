import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        C_Missile c1 = new C_Missile(0);
        C_Missile c2 = new C_Missile(1);
        try {
            sleep(950);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        C_Missile c3 = new C_Missile(2);
    }
}
