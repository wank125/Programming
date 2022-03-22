package com.mike.car;

/**
 * YAMAHA
 */
class Yamaha implements IEngine {
    @Override
    public void start() {
        System.out.println("YAMAHA");
    }

    @Override
    public void stop() {
        System.out.println("YAMAHA");
    }

    @Override
    public void speed() {
        System.out.println("YAMAHA");
    }
}

/**
 * HONDA
 */
class Honda implements IEngine {
    @Override
    public void start() {
        System.out.println("HONDA");
    }

    @Override
    public void stop() {
        System.out.println("HONDA");
    }

    @Override
    public void speed() {
        System.out.println("HONDA");
    }
}

public class CarFactory {

    private IEngine iEngine;

    public CarFactory(IEngine iEngine) {
        this.iEngine = iEngine;
    }

    public void testEngine() {
        iEngine.start();
        iEngine.speed();
        iEngine.stop();
    }


    public static void main(String[] args) {
        CarFactory carFactory = new CarFactory(new Honda());
        carFactory.testEngine();
    }
}
