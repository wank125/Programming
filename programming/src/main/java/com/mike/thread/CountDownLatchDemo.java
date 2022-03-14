package thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
  public static final CountDownLatch latch = new CountDownLatch(2);


  public static void main(String[] args) throws InterruptedException {
    new Thread() {
      @Override
      public void run() {
        try {
          System.out.println("子线程1" + "正在执行");
          Thread.sleep(3000);
          System.out.println("子线程1" + "执行完毕");
          latch.countDown();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }.start();

    new Thread() {
      @Override
      public void run() {
        try {
          System.out.println("子线程2" + "正在执行");
          Thread.sleep(3000);
          System.out.println("子线程2" + "执行完毕");
          latch.countDown();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }.start();
    //latch.await();
    System.out.println(latch.getCount());

  }
}
