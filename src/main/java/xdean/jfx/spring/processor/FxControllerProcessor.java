package xdean.jfx.spring.processor;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import xdean.jex.log.Logable;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;
import xdean.jfx.spring.annotation.FxThread;
import xdean.jfx.spring.annotation.ThreadScope;

@ThreadScope
@Component
@FxReady
public class FxControllerProcessor
    implements  InstantiationAwareBeanPostProcessor, BeanFactoryAware, Logable {
  @Inject
  @Qualifier(FxThread.SCHEDULER)
  TaskExecutor scheduler;

  private BeanFactory beanFactory;

  private boolean fxmling;

  @Override
  public boolean postProcessAfterInstantiation(Object controller, String beanName) throws BeansException {
    // If fxmling, it means this controller is a sub-controller, it should be inject by
    // parent-fxml-loader.
    Class<? extends Object> beanClass = controller.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(beanClass, FxController.class);
    if (fxController == null) {
      return true;
    }
    if (fxmling) {
      return true;
    }
    debug("Load fxml, class:{}, source:{}.", beanClass, fxController.fxml());
    fxmling = true;
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
    return true;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FxInitializable) {
      scheduler.execute(() -> ((FxInitializable) bean).initAfterFxSpringReady());
    }
    return bean;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
