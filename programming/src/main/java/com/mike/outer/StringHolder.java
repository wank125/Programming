package com.mike.outer;


interface Iterator {
    boolean hasNext();

    String next();
}

interface Iterable {
    public Iterator iterator();
}

public class StringHolder implements Iterable {
    private String[] values;
    private int pos;


    public StringHolder(int length) {
        values = new String[length];
        pos = 0;
    }

    public void put(String val) {
        values[pos++] = val;
    }

    public String get(int index) {
        return values[index];
    }

    @Override
    public Iterator iterator() {
        return new StringIterator();
    }

    private class StringIterator implements Iterator {
        private int ipos = 0;

        @Override
        public boolean hasNext() {
            return ipos < values.length;
        }

        @Override
        public String next() {
            return values[ipos++];
        }
    }

    public static void main(String[] args) {
        StringHolder sh = new StringHolder(10);
        for (int i = 0; i < 10; i++) {
            sh.put(((Integer) (i * 3)).toString());
        }
        Iterator iterator = sh.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
