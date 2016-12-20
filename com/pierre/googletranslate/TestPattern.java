package com.pierre.googletranslate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		String line = "[[[\"Edited by Christian F. Feest\",\"Herausgegeben von Christian F. Feest\",,,3]],,\"de\"]\"";
		Matcher m = p.matcher(line);
		if (m.find()) {
			System.out.println(m.group(1));
		}

		String text = "Hello? World!...";
		String pattern = "(?<=(rn|r|n|\\?|!|\\.))([ \t]*$)+";

		String[] par = Pattern.compile(pattern, Pattern.MULTILINE).split(text);

		for (int i = 0; i < par.length; i++) {

			String paragraph = par[i];

			System.out.println(paragraph);

		}
	}
}
