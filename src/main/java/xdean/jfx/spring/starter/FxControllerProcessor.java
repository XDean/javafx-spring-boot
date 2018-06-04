package xdean.jfx.spring.starter;

import static xdean.jex.util.lang.ExceptionUtil.uncheck;

import java.io.IOException;
import java.lang.reflect.Method;

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

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
    FxController fxController = AnnotationUtils.getAnnotation(beanClass, FxController.class);
    if (fxController == null) {
      return null;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(beanClass.getResource(fxController.fxml()));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    }
    Object controller = fxmlLoader.getController();
    Object root = fxmlLoader.getRoot();
    if (controller instanceof FxGetRoot) {
      return Enhancer.create(beanClass, (MethodInterceptor) (obj, method, args, proxy) -> {
        if (method.equals(getRoot)) {
          return root;
        }
        return proxy.invokeSuper(obj, args);
      });
    } else {
      return controller;
    }
  }
}
