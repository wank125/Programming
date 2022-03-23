package com.mike.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    public static void main(String[] args) {
        String str = "foobaroobar";
        Pattern p;
        Matcher m;

        String[] regs = {"f.*bar", "f.*?bar", "f.*+bar"};
        for (String reg : regs) {
            p = Pattern.compile(reg);
            m = p.matcher(str);
            System.out.println("Regexp:\"" + reg + "\"Match: " + m.find());
            System.out.println(m);
        }
    }
}
