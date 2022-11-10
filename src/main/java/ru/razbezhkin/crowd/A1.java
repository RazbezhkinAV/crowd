package ru.razbezhkin.crowd;

import java.util.Locale;

public class A1 {
    public static void main(String[] args) {
        String name = "Alexandr";
        String lastName = "Razbezhkin";
        String login = name.toLowerCase(Locale.ROOT).charAt(0) + "." + lastName.toLowerCase(Locale.ROOT);
        String newLastname = "Petrov";
        String s = login.split("\\.")[0];
        System.out.println(s + "." + newLastname.toLowerCase(Locale.ROOT));

        int i = 1 + 1;
    }
}
