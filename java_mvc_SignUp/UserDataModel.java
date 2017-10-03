import java.util.HashMap;
import java.util.Map;

public final class UserDataModel {

	//以下、一時的にデータを格納する場所(一時変数)
	private static String tmpUserName="";
	private static String tmpUserPassword="";

	//ユーザーのログイン情報を保存する場所
	private static Map<String, String> LoginData=new HashMap<>();

	//一時変数の初期化(前のユーザー情報を残さないように)
	private static void initTmpUserData() {
		tmpUserName="";
		tmpUserPassword="";
	}

	//以下、一時変数のアクセッサ。
	public static void setUserName(String txt) throws SignUpDataException{
		//confirm...で入力文字に対して様々なエラーを検出し、それをControllerに投げる。
		confirmCorrectName(txt);
		tmpUserName=txt;
		//プロパティ(一時変数)を変更したので、オブザーバ(View)に情報をStringで通知する。
		NotifyView("tmpUserName = "+tmpUserName);
	}
	public static void setUserPassword(String txt) throws SignUpDataException{
		//上と同様
		confirmCorrectPassword(txt);
		tmpUserPassword=txt;
		NotifyView("tmpUserPassword = "+tmpUserPassword);
	}

	//以下、ユーザーのログイン情報を新規追加
	private static void addNewUser(String un,String up) throws SignUpDataException {
		//confirm...で入力文字に対して様々なエラーを検出し、エラーをControllerに投げる。Controllerはtry-catch構文でエラーを受け取る。
		confirmCorrectName(un);
		confirmCorrectPassword(up);
		LoginData.put(un, up);
		NotifyView("Regist Completed.");
	}
	public static void registSavedUserData() throws SignUpDataException {
		addNewUser(tmpUserName,tmpUserPassword);
		//後処理
		initTmpUserData();
	}

	//以下、エラーを検出するメソッド。throwすると、この関数の呼び出し元にExceptionを渡す。try-catch構文でcatchするまで、そのExceptionは遡り続ける。
	private static void confirmCorrectName(String txt) throws SignUpDataException{
		if(txt.equals(""))throw new SignUpDataException("ユーザー名が不正です。");
		if(LoginData.containsKey(txt))throw new SignUpDataException("そのユーザー名は既に使用されています。");
	}
	private static void confirmCorrectPassword(String txt) throws SignUpDataException{
		if(txt.equals("")||txt.contains(" "))throw new SignUpDataException("パスワードの文字列が不正です。");
	}

	//再度入力した確認用パスワードの文字列に対し、エラーを検出するメソッド。
	public static void confirmUserPassword(String inputTxt) throws SignUpDataException{
		if(!inputTxt.equals(tmpUserPassword))throw new SignUpDataException("入力したパスワードが一致しません。");
	}

	//オブザーバ(View)に対し、内部データが変更されたことを通知するための関数。
	private static void NotifyView(String str){
		//通知された内容をもとにViewが実行する関数(update~という名前が一般的)をここに書く。update関数の実装(中身)はViewクラス内に書く。
		ConsoleFormView.updateView(str);
	}

}
