import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        C_Missile.getInstance().launch();
        C_Missile.getInstance().launch();
        try {
            sleep(950);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        C_Missile.getInstance().launch();
    }
}
