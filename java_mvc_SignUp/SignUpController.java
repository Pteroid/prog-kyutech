public final class SignUpController {
	public static void  Start(){
		while(true)
			try {
				UserDataModel.setUserName(ConsoleFormView.registUserName());
				break;
			} catch (SignUpDataException e) {
				ConsoleFormView.showMyExceptionMsg(e);
			}

		while(true)
			try {
				UserDataModel.setUserPassword(ConsoleFormView.inputUserPassword());
				UserDataModel.confirmUserPassword(ConsoleFormView.reImputUserPassword());
				UserDataModel.registSavedUserData();
				break;
			} catch (SignUpDataException e) {
				ConsoleFormView.showMyExceptionMsg(e);
			}
	}
}
