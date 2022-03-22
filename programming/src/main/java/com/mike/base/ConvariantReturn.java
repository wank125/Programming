package com.mike.base;

/**
 * 协变返回类型
 */

class Liquor {
    public String toString() {
        return "Liquor";
    }
}

class Beer extends Liquor {
    public String toString() {
        return "Beer";
    }
}

class LiquorFactory {
    public Liquor make() {
        return new Liquor();
    }
}

class BeerFactory extends LiquorFactory {
    public Beer make() {
        return new Beer();
    }
}

public class ConvariantReturn {
    public static void main(String[] args) {
        LiquorFactory lf = new LiquorFactory();
        Liquor l = lf.make();
        System.out.println(l);
        lf = new BeerFactory();
        l = lf.make();
        System.out.println(l);
    }
}
