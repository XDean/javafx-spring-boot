package xdean.jfx.spring.starter;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import xdean.jex.log.Logable;
import xdean.jfx.spring.FxGetRoot;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxControllerProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, Logable {
  private static final Map<Object, Object> CONTROLLER_TO_ROOT = new WeakHashMap<>();

  private BeanFactory beanFactory;

  @Override
  public boolean postProcessAfterInstantiation(Object controller, String beanName) throws BeansException {
    Class<? extends Object> beanClass = controller.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(beanClass, FxController.class);
    if (fxController == null) {
      return true;
    }
    debug("Load fxml, class:{}, source:{}.", beanClass, fxController.fxml());
    FXMLLoader fxmlLoader = new FXMLLoader(beanClass.getResource(fxController.fxml()));
    fxmlLoader.setControllerFactory(c -> {
      if (c == beanClass) {
        return controller;
      } else {
        return beanFactory.getBean(c);
      }
    });
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    }
    Object root = fxmlLoader.getRoot();
    CONTROLLER_TO_ROOT.put(controller, root);
    return true;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FxInitializable) {
      Platform.runLater(() -> ((FxInitializable) bean).initialize());
    }
    return bean;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getRoot(FxGetRoot<T> controller) {
    return (T) CONTROLLER_TO_ROOT.get(controller);
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
