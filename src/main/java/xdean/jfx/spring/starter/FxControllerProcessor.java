package xdean.jfx.spring.starter;

import static xdean.jex.util.lang.ExceptionUtil.uncheck;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import xdean.jfx.spring.FxGetRoot;
import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxReady;

@Component
@FxReady
public class FxControllerProcessor extends AbstractAdvisingBeanPostProcessor implements BeanPostProcessor {

  private final FxGetRootAdvisor fxGetRootAdvisor;
  
  public FxControllerProcessor() {
    this.fxGetRootAdvisor = new FxGetRootAdvisor();
    this.advisor = fxGetRootAdvisor;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Class<?> clz = bean.getClass();
    FxController fxController = AnnotationUtils.getAnnotation(clz, FxController.class);
    if (fxController == null) {
      return bean;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(clz.getResource(fxController.fxml()));
    fxmlLoader.setControllerFactory(c -> bean);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new BeanCreationException("Fail to load fxml: " + fxmlLoader.getLocation(), e);
    }
    if(bean instanceof FxGetRoot) {
      fxGetRootAdvisor.controllerToRoot.put(bean, fxmlLoader.getRoot());
    }
    return bean;
  }

  public static class FxGetRootAdvisor implements PointcutAdvisor {

    private final Map<Object, Object> controllerToRoot = new WeakHashMap<>();
    private final Method getRoot = uncheck(() -> FxGetRoot.class.getMethod("getRoot"));

    @Override
    public Advice getAdvice() {
      return (MethodInterceptor) invocation -> controllerToRoot.get(invocation.getThis());
    }

    @Override
    public Pointcut getPointcut() {
      return new Pointcut() {
        @Override
        public MethodMatcher getMethodMatcher() {
          return new MethodMatcher() {
            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
              return true;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass) {
              return Objects.equals(method, getRoot);
            }

            @Override
            public boolean isRuntime() {
              return false;
            }
          };
        }

        @Override
        public ClassFilter getClassFilter() {
          return clz -> Objects.equals(clz, FxGetRoot.class);
        }
      };
    }

    @Override
    public boolean isPerInstance() {
      return false;
    }
  }
}
