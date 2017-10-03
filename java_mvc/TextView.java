package mvc;

import java.util.Scanner;

public class TextView implements Observer {

	private final Scanner SCANNER = new Scanner(System.in);

	//Viewが一度に見れるObservableは一つだけという設定
	TextModel dataSource;
	public void addDataSource(TextModel textModel) {
		if(dataSource==null){
			dataSource=textModel;
			textModel.Add(this);
		}else if(dataSource==textModel){
			//同じObservableを引数に与えられたら、何もしない
		}else{
			//Observableの変更は未実装(Observableにdeleteなどが実装されていないため)
		}

	}

	@Override
	public void Update(Observable observable) {
		TextModel textModel = (TextModel)observable;
		if(textModel!=null)
			System.out.println("You entered:"+textModel.getText());
	}
	public String input() {
		System.out.print("Enter text: ");
		return SCANNER.nextLine();
	}

}
