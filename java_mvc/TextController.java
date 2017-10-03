package mvc;

public class TextController {
	TextModel textModel=new TextModel();
	TextView textView=new TextView();
	public TextController() {
		textView.addDataSource(textModel);
	}

	public void start() {
		textModel.setText(textView.input());
	}

}
