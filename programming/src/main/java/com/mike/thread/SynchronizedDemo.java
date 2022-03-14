package thread;

import java.util.concurrent.Semaphore;

public class SynchronizedDemo {
  final String lockA = "lockA";
  final String lockB = "lockB";
  final static String lockC = "lockC";

  public static void main(String[] args) throws InterruptedException {

    final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
    Semaphore semaphore = new Semaphore(5);


//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        //synchronizedDemo.generallMethod3();
//        synchronizedDemo.blockMethod1();
//      }
//    }).start();
//    new Thread((new Runnable() {
//      @Override
//      public void run() {
//       // synchronizedDemo.generallMethod4();
//        synchronizedDemo.blockMethod2();
//      }
//    })).start();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          synchronized (lockC) {
            for (int i = 1; i < 100; i++) {
              System.out.println("generalMethod1 " + i + " time");
              Thread.sleep(3000);

              if (Thread.interrupted()) {
                System.out.println("interupted");
              }
            }
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    thread.start();
    Thread.sleep(1000);
    thread.interrupt();
  }

  public synchronized void generallMethod1() {
    try {
      for (int i = 1; i < 3; i++) {
        System.out.println("generalMethod1 " + i + " time");
        Thread.sleep(3000);

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public synchronized void generallMethod2() {
    try {
      for (int i = 1; i < 3; i++) {
        System.out.println("generalMethod2 " + i + " time");
        Thread.sleep(3000);

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public void generallMethod3() {
    synchronized (lockA) {
      try {
        for (int i = 1; i < 3; i++) {
          System.out.println("generalMethod1 " + i + " time");
          Thread.sleep(3000);

        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void generallMethod4() {
    synchronized (lockA) {
      try {
        for (int i = 1; i < 3; i++) {
          System.out.println("generalMethod1 " + i + " time");
          Thread.sleep(3000);

        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void blockMethod1() {

    try {
      synchronized (lockA) {
        for (int i = 1; i < 3; i++) {
          System.out.println("generalMethod1 " + i + " time");
          Thread.sleep(3000);
          synchronized (lockB) {
          }

        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void blockMethod2() {

    try {
      synchronized (lockB) {
        for (int i = 1; i < 100; i++) {
          System.out.println("generalMethod1 " + i + " time");
          Thread.sleep(3000);
//          synchronized (lockA) {
//          }

        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
