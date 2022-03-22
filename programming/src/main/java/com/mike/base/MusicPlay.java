package com.mike.base;


class Music {
    public Music() {
        System.out.println("Construct Music");
        play();
    }

    public void play() {
        System.out.println("Play Music");
    }
}

class Jazz extends Music {
    public Jazz() {
        System.out.println("Construct Jazz ");
        play();
    }

    public void play() {
        System.out.println("Play Jazz");
    }
}

public class MusicPlay {
    public static void main(String[] args) {
        new Jazz();
    }

}
