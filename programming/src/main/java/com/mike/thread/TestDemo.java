package thread;

public class TestDemo {
  public static void main(String[] args) {
    String threadName = Thread.currentThread().getName();
    System.out.println(threadName + "start.");
    BThread bThread = new BThread();

    try {
      //  bThread.join();
      bThread.start();
      // Thread.sleep(2000);
      Thread.currentThread().join();

    } catch (Exception e) {
      System.out.println("Exception from main");
    }
    System.out.println(threadName + "end!");
  }
}
