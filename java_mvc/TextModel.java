package mvc;

public class TextModel extends Observable{
	String Text="";
	public String getText() {
		return Text;
	}
	public void setText(String str) {
		if(!Text.equals(str)){
			Text=str;
			Update();
		}
	}

}
