package xdean.jfx.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxInitializableProcessor implements BeanPostProcessor {
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FxInitializable) {
      Platform.runLater(() -> ((FxInitializable) bean).initialize());
    }
    return bean;
  }
}
