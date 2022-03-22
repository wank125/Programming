package com.mike.outer;

/**
 * 内部类
 */
public class Outer {
    private int index = 100;

    class Middle {
        private int index = 50;

        class Inner {
            private int index = 25;

            public void print() {
                System.out.println(index);
                System.out.println(Middle.this.index);
                System.out.println(Outer.this.index);
            }
        }

        public Inner getINner() {
            return new Inner();
        }
    }

    public Middle getMiddle() {
        return new Middle();
    }
}

class Test1 {
    public static void main(String[] args) {
        Outer ou = new Outer();
        Outer.Middle mi = ou.new Middle();
        Outer.Middle.Inner in = mi.new Inner();
        in.print();
    }
}
