package com.mike.exception;

public class MathException extends Exception {
    public MathException() {
    }

    public MathException(String message) {
        super(message);
    }
}

class DivisorIsZeroException extends MathException {
    public DivisorIsZeroException() {
    }

    public DivisorIsZeroException(String message) {
        super(message);
    }
}

class DivisorInvalidException extends MathException {

    public DivisorInvalidException() {
    }

    public DivisorInvalidException(String message) {
        super(message);
    }
}

class MyMath {
    public int divide(int a, int b) throws MathException {
        if (b == 0) {
            throw new DivisorIsZeroException("除数不能为0");
        }
        return a / b;
    }
}

class Test {
    private int x = 5;
    private int y = 0;

    public int divide(MyMath mm) {
        int result = 0;
        try {
            result = mm.divide(x, y);
        } catch (MathException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.divide(new MyMath());
    }
}

class SuperMath extends MyMath {
    @Override
    public int divide(int a, int b) throws DivisorInvalidException, DivisorIsZeroException {
        if (0 == b) {
            throw new DivisorIsZeroException("除数为0");
        } else if (b < 0) {
            throw new DivisorInvalidException("除数为负数");
        }
        return a / b;
    }
}