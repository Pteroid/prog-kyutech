
import java.util.Scanner;

public final class ConsoleFormView {
	private static Scanner scanner;
	public static String registUserName() throws  SignUpDataException{
		System.out.println(">登録するユーザー名を入力してください。");
		scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	public static String inputUserPassword() throws  SignUpDataException{
		System.out.println(">登録するパスワードを入力してください。");
		scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	public static String reImputUserPassword() throws  SignUpDataException{
		System.out.println(">再度パスワードを入力してください。");
		scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	public static void updateView(String txt){
		System.out.println("【UDM Notified View: "+txt+" 】");
	}
	public static void showMyExceptionMsg(SignUpDataException e){
		System.out.println(e+"\n");
	}

}
