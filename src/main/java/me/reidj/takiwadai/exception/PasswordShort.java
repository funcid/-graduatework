package me.reidj.takiwadai.exception;

import lombok.Getter;

@Getter
public class PasswordShort implements Solid {

	private final String[] message = new String[]{
			"Пожалуйста, проверьте правильность введённого пароля!",
			"Ваш пароль должен быть длиннее 6 символов!"
	};

	@Override
	public boolean check(String... strings) {
		return strings[0].length() <= 6;
	}

}
