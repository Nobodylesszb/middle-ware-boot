package com.jwt.token.demo.utils;

import java.util.HashMap;

public class FileSwitch {

    public static String validFilePath(String filepath) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        map.put("d", "d");
        map.put("e", "e");
        map.put("f", "f");
        map.put("g", "g");
        map.put("h", "h");
        map.put("i", "i");
        map.put("j", "j");
        map.put("k", "k");
        map.put("l", "l");
        map.put("m", "m");
        map.put("n", "n");
        map.put("o", "o");
        map.put("p", "p");
        map.put("q", "q");
        map.put("r", "r");
        map.put("s", "s");
        map.put("t", "t");
        map.put("u", "u");
        map.put("v", "v");
        map.put("w", "w");
        map.put("x", "x");
        map.put("y", "y");
        map.put("z", "z");


        map.put("A", "A");
        map.put("B", "B");
        map.put("C", "C");
        map.put("D", "D");
        map.put("E", "E");
        map.put("F", "F");
        map.put("G", "G");
        map.put("H", "H");
        map.put("I", "I");
        map.put("J", "J");
        map.put("K", "K");
        map.put("L", "L");
        map.put("M", "M");
        map.put("N", "N");
        map.put("O", "O");
        map.put("P", "P");
        map.put("Q", "Q");
        map.put("R", "R");
        map.put("S", "S");
        map.put("T", "T");
        map.put("U", "U");
        map.put("V", "V");
        map.put("W", "W");
        map.put("X", "X");
        map.put("Y", "Y");
        map.put("Z", "Z");


        map.put(":", ":");
        map.put("/", "/");
        map.put("\\", "\\");
        map.put(".", ".");
        map.put("-", "-");
        map.put("_", "_");

        map.put("0", "0");
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        map.put("6", "6");
        map.put("7", "7");
        map.put("8", "8");
        map.put("9", "9");


        String temp = "";
        for (int i = 0; i < filepath.length(); i++) {
            if (map.get(filepath.charAt(i) + "") != null) {
                temp += map.get(filepath.charAt(i) + "");
            }
        }
        filepath = temp;


        return filepath;


    }

    public static void main(String[] args) {
        String s = FileSwitch.validFilePath("http://www.baidu.com");
        System.out.println(s);
    }


}