package com.mike.outer;

interface Speaker {
    void speak();
}

public class LocalInnerClass {

    public Speaker getSpeaker(String str) {
        class MySpeak implements Speaker {
            //  private String s;
//            public MySpeak(String s) {
//                this.s = s;
//            }

            @Override
            public void speak() {
                System.out.println(str);
            }
        }
        return new MySpeak();
    }

    public static void main(String[] args) {
        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.getSpeaker("aaa").speak();
    }
}
