package com.minis;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ClassPathXmlApplicationContext {
  private List<BeanDefinition> beanDefinitions = new ArrayList<>();
  private Map<String, Object> singletons = new HashMap<>();

  public ClassPathXmlApplicationContext(String xmlPath)
      throws DocumentException, NoSuchFieldException, ClassNotFoundException, InstantiationException,
             IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    this.readXml(xmlPath);
    this.instanceBeans();
  }

  private void readXml(String xmlPath) throws DocumentException, NoSuchFieldException {
    SAXReader reader = new SAXReader();
    URL path = this.getClass().getClassLoader().getResource(xmlPath);
    Document document = reader.read(path);
    Element rootElement = document.getRootElement();
    for (Object element : rootElement.elements()) {
      String beanID = ((Element) element).attributeValue(BeanDefinition.class.getDeclaredField("id").getName());
      String beanClassName =
          ((Element) element).attributeValue(BeanDefinition.class.getDeclaredField("className").getName());
      BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
      beanDefinitions.add(beanDefinition);
    }
  }

  private void instanceBeans()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException,
             InvocationTargetException {
    for (BeanDefinition beanDefinition : beanDefinitions) {
      singletons.put(beanDefinition.getId(),
          Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance());
    }
  }

  public Object getBean(String beanName) {
    return singletons.get(beanName);
  }
}
