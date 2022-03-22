package com.mike.outer;

interface Operation {
    void operate();
}


class MyImplement implements Operation {
    @Override
    public void operate() {
        System.out.println("MyImplement operation");
    }
}

class Caller {
    private Operation call;

    public Caller(Operation call) {
        this.call = call;
    }

    public void call() {
        call.operate();
    }
}

public class Callback {

    public static void main(String[] args) {
        Caller caller = new Caller(new MyImplement());
        caller.call();
    }
}
