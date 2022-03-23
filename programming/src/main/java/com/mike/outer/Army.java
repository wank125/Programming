package com.mike.outer;

interface Weapon {
    public void shoot();
}

public class Army {
    public static void attack(Weapon weapon) {
        weapon.shoot();
    }

    public static void main(String[] args) {
        //Army army = new Army();
        Army.attack(new Weapon() {
            @Override
            public void shoot() {
                System.out.println("shoot");
            }
        });
    }
}
