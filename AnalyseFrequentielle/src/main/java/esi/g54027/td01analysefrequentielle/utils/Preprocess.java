package esi.g54027.td01analysefrequentielle.utils;

import java.io.*;
import java.util.Scanner;

public class Preprocess {
    public static final String DIACRITICS = "àâäéèêëîïôùûçÿ";
    public static final String DIACRITICS_REPLACE = "aaaeeeeiiouucy";
    public static final String NOT_REMOVE = "[a-zA-Z]";

    public static int indexOf(char c, String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    public static String preprocessLine(String line) {
        line = line.toLowerCase();
        var out = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            int index = indexOf(c, DIACRITICS);
            if (index != -1) {//char is not a diacritic
                out.append(DIACRITICS_REPLACE.charAt(index));
            } else if (!Character.valueOf(c).toString().matches(NOT_REMOVE)) { //char has to be removed
            } else if (c == 'æ') {
                out.append("ae");
            } else if (c == 'œ') {
                out.append("oe");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static void preprocessFile(String input, String output, boolean ignoreLines) throws
            FileNotFoundException, IOException {
        Scanner in = new Scanner(new FileReader(input));
        PrintStream out = new PrintStream(new FileOutputStream(output));

        while (in.hasNextLine()) {
            if (!ignoreLines) {
                out.println(preprocessLine(in.nextLine()));
            } else {
                out.print(preprocessLine(in.nextLine()));
            }
        }
    }
}