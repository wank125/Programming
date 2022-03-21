package com.mike.annotation;

import java.lang.annotation.Annotation;

@BugReport(msg = "报告基类Bug")
class Base {
}

class Derived extends Base {

  @BugReport(serverity = 1, msg = "name字段的bug")
  private String name;

  @BugReport(serverity = 2, msg = "action方法的Bug")
  public void action() {
  }
}

public class ReflectAnnotation {
  public static void main(String[] args) {
    Class<Derived> clz = Derived.class;
    Annotation[] annotations = clz.getAnnotations();
    for (Annotation anno : annotations) {
      Class<? extends Annotation> annoClz = anno.annotationType();
      System.out.println("注解的类型：" + annoClz);
      if (BugReport.class.equals(annoClz)) {
        BugReport br = (BugReport) anno;
        System.out.printf("--元素: serverity=%d, msg=%s%n", br.serverity(), br.msg());
      }

    }


  }
}
