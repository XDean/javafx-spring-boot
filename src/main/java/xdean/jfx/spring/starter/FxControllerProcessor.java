package xdean.jfx.spring.starter;

import static xdean.jex.util.lang.ExceptionUtil.uncheck;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import xdean.jfx.spring.FxGetRoot;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxControllerProcessor implements InstantiationAwareBeanPostProcessor {
  private final Method getRoot = uncheck(() -> FxGetRoot.class.getMethod("getRoot"));
  private final Map<Object, Object> controllerToRoot = new WeakHashMap<>();

  @Override
  public boolean postProcessAfterInstantiation(Object controller, String beanName) throws BeansException {
    Class<? extends Object> beanClass = controller.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(beanClass, FxController.class);
    if (fxController == null) {
      return true;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(beanClass.getResource(fxController.fxml()));
    fxmlLoader.setControllerFactory(c -> controller);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    }
    Object root = fxmlLoader.getRoot();
    controllerToRoot.put(controller, root);
    return true;
  }

  @Override
  public Object postProcessBeforeInitialization(Object controller, String beanName) throws BeansException {
    Class<? extends Object> beanClass = controller.getClass();
    if (controller instanceof FxGetRoot) {
      Object root = controllerToRoot.remove(controller);
      if (root != null) {
        return Enhancer.create(beanClass, (MethodInterceptor) (obj, method, args, proxy) -> {
          if (method.equals(getRoot)) {
            return root;
          }
          return method.invoke(controller, args);
        });
      }
    }
    return controller;
  }
}
