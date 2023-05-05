package me.reidj.takiwadai.exception;

import lombok.Getter;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.Utils;

import java.util.Arrays;

@Getter
public class SymbolIsIncorrect implements Solid {

	private final String[] message = new String[]{
			"Пожалуйста, проверьте правильность введённых данных",
			"Имя, фамилия и отчество могут содержать только русские символы!"
	};

	@Override
	public boolean check(String... strings) {
		return Arrays.stream(strings).anyMatch(s -> Utils.isRegularExpressionCheck(Utils.RUSSIAN_SYMBOL_REGEX, s));
	}

}
