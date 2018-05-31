package xdean.jfx.spring.starter;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxControllerProcessor implements BeanPostProcessor {
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Class<?> clz = bean.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(clz, FxController.class);
    if (fxController == null) {
      return bean;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(clz.getResource(fxController.fxml()));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    }
    return fxmlLoader.getController();
  }
}
