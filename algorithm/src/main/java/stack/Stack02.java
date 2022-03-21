package stack;

public class Stack02 {

  static int top = -1;

  public static void main(String[] args) {
    int card[] = new int[52];
    int stack[] = new int[52];
    int i, j, k = 0, test;
    char ascVal = 5;
    int style;

    for (i = 0; i < 52; i++) {
      card[i] = i;
    }

    System.out.println("[洗牌中...请稍等！]");
    while (k < 30) {
      for (i = 0; i < 51; i++) {
        for (j = i + 1; j < 52; j++) {
          if (((int) (Math.random() * 5)) == 2) {
            test = card[i];
            card[i] = card[j];
            card[j] = test;
          }
        }
      }
      k++;
    }

    i = 0;
    while (i != 52) {
      push(stack, 52, card[i]);
      i++;
    }

    System.out.println("逆时针发牌");
    System.out.println("显示各家的牌");

    while (top >= 0) {
      style = stack[top] / 13;
      switch (style) {
        case 0:
          ascVal = 'C';
          break;
        case 1:
          ascVal = 'D';
          break;
        case 2:
          ascVal = 'H';
          break;
        case 3:
          ascVal = 'S';
          break;
      }

      System.out.print(String.valueOf(ascVal) + (stack[top] % 13 + 1));
      System.out.print('\t');
      if (top % 4 == 0) {
        System.out.println();
      }
      top--;
    }


  }

  public static void push(int stack[], int MAX, int val) {
    if (top >= MAX - 1) {
      System.out.println("堆栈已满");
    } else {
      top++;
      stack[top] = val;
    }
  }

  public static int pop(int stack[]) {
    if (top < 0) {
      System.out.println("堆栈已空");
    } else {
      top--;
    }
    return stack[top];
  }

}
