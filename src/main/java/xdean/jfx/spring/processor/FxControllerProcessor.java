package xdean.jfx.spring.processor;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.inject.Named;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import xdean.jex.log.Logable;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;
import xdean.jfx.spring.context.FxContext;

@Component
@FxReady
public class FxControllerProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, Logable {

  @Autowired
  @Named(FxContext.FX_SCHEDULER)
  private TaskExecutor scheduler;

  private BeanFactory beanFactory;

  private ThreadLocal<Map<Object, Object>> controllerToRoot = threadLocal(WeakHashMap::new);

  private ThreadLocal<Boolean> fxmling = threadLocal(() -> false);

  private ThreadLocal<Deque<Object>> subControllers = threadLocal(ArrayDeque::new);

  @Override
  public Object postProcessBeforeInitialization(Object controller, String beanName) throws BeansException {
    // If fxmling, it means this controller is a sub-controller, it should be
    // inject by parent-fxml-loader.
    Class<? extends Object> beanClass = controller.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(beanClass, FxController.class);
    if (fxController == null || fxmling.get()) {
      return controller;
    }
    debug("Load fxml, class:{}, source:{}.", beanClass, fxController.fxml());
    fxmling.set(true);
    FXMLLoader fxmlLoader = new FXMLLoader(beanClass.getResource(fxController.fxml()));
    fxmlLoader.setControllerFactory(c -> {
      if (c == beanClass) {
        return controller;
      } else {
        Object sub = beanFactory.getBean(c);
        subControllers.get().push(sub);
        return sub;
      }
    });
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    } finally {
      fxmling.set(false);
    }
    subControllers.get().forEach(this::invokeFxInit);
    subControllers.get().clear();
    controllerToRoot.get().put(fxmlLoader.getController(), fxmlLoader.getRoot());
    return controller;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    invokeFxInit(bean);
    return bean;
  }

  public void invokeFxInit(Object bean) {
    if (bean instanceof FxInitializable) {
      if (fxmling.get()) {
        return;
      } else {
        scheduler.execute(() -> ((FxInitializable) bean).initAfterFxSpringReady());
      }
    }
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Nullable
  public Object getRoot(Object controller) {
    return controllerToRoot.get().get(controller);
  }

  private static <T> ThreadLocal<T> threadLocal(Supplier<T> factory) {
    return new ThreadLocal<T>() {
      @Override
      protected T initialValue() {
        return factory.get();
      }
    };
  }
}
