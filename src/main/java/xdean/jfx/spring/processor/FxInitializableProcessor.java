package xdean.jfx.spring.processor;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxAsync;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxInitializableProcessor implements BeanPostProcessor {

  @Inject
  @Qualifier(FxAsync.SCHEDULER)
  TaskExecutor fxAsync;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FxInitializable) {
      if (Platform.isFxApplicationThread()) {
        ((FxInitializable) bean).initAfterFxSpringReady();
      } else {
        fxAsync.execute(() -> ((FxInitializable) bean).initAfterFxSpringReady());
      }
    }
    return bean;
  }
}
