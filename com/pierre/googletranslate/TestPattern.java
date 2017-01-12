package com.pierre.googletranslate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		String line = "[[[\"Hello \",\"Hallo  \",,,3],[\"Goodbye\",\"Tschuss\",,,3]],,\"de\"]";
		Matcher m = p.matcher(line);
		List<String> answers = new ArrayList<String>();
		while (m.find()) {
			String item = m.group(1);
			System.out.println("m.group(1)=" + item);
			answers.add(item);
		}
		String[] elements = new String[answers.size()];
		elements = (String[]) answers.toArray(elements);
		String result = "";
		for (int i = 0; i < (elements.length -1) / 2; i++) {
			result += elements[i] + " ";
		}
		System.out.println("result=" + result);

		String text = "Hello? World!...";
		String pattern = "(?<=(rn|r|n|\\?|!|\\.))([ \t]*$)+";

		String[] par = Pattern.compile(pattern, Pattern.MULTILINE).split(text);

		for (int i = 0; i < par.length; i++) {

			String paragraph = par[i];

			System.out.println(paragraph);

		}
	}
}
