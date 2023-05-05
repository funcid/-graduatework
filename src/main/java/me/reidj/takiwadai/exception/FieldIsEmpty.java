package me.reidj.takiwadai.exception;

import lombok.Getter;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.Utils;

import java.util.Arrays;

@Getter
public class FieldIsEmpty implements Solid {

	private final String[] message = new String[]{
			"Пожалуйста, заполните все поля",
			"Поля не могут быть пустыми!"
	};

	@Override
	public boolean check(String... strings) {
		return Arrays.stream(strings).anyMatch(s -> s == null || s.isEmpty());
	}

}
