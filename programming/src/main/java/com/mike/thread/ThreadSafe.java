package thread;

public class ThreadSafe extends Thread {
  public volatile boolean exit = false;

//  @Override
//  public void run() {
//    while (!exit) {
//      System.out.println("1111");
//    }
//    System.out.println("exit");
//  }


  @Override
  public void run() {
    //super.run();
    while (!isInterrupted()) {

      System.out.println("111");
//      try {
//        Thread.sleep(5 * 1000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//        System.out.println("外部中断");
//        break;
//      }
    }
    System.out.println("收到中断请求");
  }

  public static void main(String[] args) {
    ThreadSafe threadSafe = new ThreadSafe();
    threadSafe.start();
    try {
      Thread.sleep(1000);
      threadSafe.interrupt();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    threadSafe.exit = true;
  }
}
