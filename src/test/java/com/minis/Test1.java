package com.minis;

import java.lang.reflect.InvocationTargetException;
import org.dom4j.DocumentException;


public class Test1 {
  public static void main(String[] args)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException,
             DocumentException, NoSuchMethodException, NoSuchFieldException {
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
    AService aService = (AService) ctx.getBean("aservice");
    aService.sayHello();
  }
}
