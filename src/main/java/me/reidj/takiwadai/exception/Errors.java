package me.reidj.takiwadai.exception;

import lombok.AllArgsConstructor;
import me.reidj.takiwadai.App;

@AllArgsConstructor
public enum Errors implements Solid {

	EMAIL(new EmailIsIncorrect()),
	FIELD_EMPTY(new FieldIsEmpty()),
	PASSWORD(new PasswordIsNotEqual()),
	PASSWORD_IS_SHORT(new PasswordShort()),
	INCORRECT_SYMBOL(new SymbolIsIncorrect());

	private final Solid solid;


	@Override
	public boolean check(String... strings) {

		boolean bool = solid.check(strings);

		if (bool) {

			String[] message = solid.getMessage();

			App.getApp().getRegistrationScene().errorAlert(
					message[0],
					message[1]
			);
		}

		return bool;
	}

	@Override
	public String[] getMessage() {
		return solid.getMessage();
	}
}
