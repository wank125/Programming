package com.mike.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchGroup {
    public static void main(String[] args) {
        String str = "<a href=\"http://www.google.com\">Google</a>";
        String reg = "href=\"(.*?)\">(.*?)</a>";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if (m.find()) {
            System.out.println(m.group(1) + "--" + m.group(2));
            System.out.println(m.group());
        }
    }
}
