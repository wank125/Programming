package thread;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayDate implements Delayed {
  private Integer number;
  private final long delayTime = 5000 + System.currentTimeMillis();
 // private final long delayTime = 5000;

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public long getDelay(TimeUnit unit) {
   // System.out.println(unit.convert(this.delayTime,TimeUnit.MILLISECONDS));
   // System.out.println(unit.convert((this.delayTime1-System.currentTimeMillis()),TimeUnit.MILLISECONDS));
    //return unit.convert(this.delayTime,TimeUnit.MILLISECONDS);
    return unit.convert((this.delayTime - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
    //  return this.delayTime;
  }

  @Override
  public int compareTo(Delayed o) {
    DelayDate compare = (DelayDate) o;
    return this.number.compareTo(compare.getNumber());
  }

  public static void main(String[] args) {
    DelayQueue<DelayDate> queue = new DelayQueue<DelayDate>();
    DelayDate date1 = new DelayDate();
    date1.setNumber(100);
    queue.add(date1);

   // while (true) {
      try {
        System.out.println(queue.size());
        DelayDate date = queue.take();
        System.out.println(date.getNumber());
        System.exit(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
       // break;
      }
   // }


  }
}
