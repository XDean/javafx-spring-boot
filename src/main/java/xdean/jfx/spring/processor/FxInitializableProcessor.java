package xdean.jfx.spring.processor;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxReady;
import xdean.jfx.spring.annotation.FxThread;

@Component
@FxReady
public class FxInitializableProcessor implements BeanPostProcessor {

  @Inject
  @Qualifier(FxThread.SCHEDULER)
  TaskExecutor scheduler;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FxInitializable) {
      scheduler.execute(() -> ((FxInitializable) bean).initAfterFxSpringReady());
    }
    return bean;
  }
}
