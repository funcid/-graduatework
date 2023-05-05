package me.reidj.takiwadai.exception;

import lombok.Getter;
import me.reidj.takiwadai.util.Utils;

@Getter
public class EmailIsIncorrect implements Solid {

	private final String[] message = new String[]{
			"Пожалуйста, проверьте правильность введённой почты",
			"Ваш формат почты неверен!"
	};

	@Override
	public boolean check(String... strings) {
		return Utils.isRegularExpressionCheck(Utils.EMAIL_REGEX, strings[0]);
	}

}
