package ru.ibs.appline.framework.utils;

public class Utils {
    public static Integer convertToInteger(String text) {
        return Integer.parseInt(text.replaceAll("\\D", ""));
    }

    public static String withoutSpace(String s) {
        return s.replaceAll(" ", "");
    }

}
