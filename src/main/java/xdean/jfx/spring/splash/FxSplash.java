package xdean.jfx.spring.splash;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;

import com.sun.javafx.application.PlatformImpl;

import javafx.stage.Stage;
import xdean.jex.log.Logable;
import xdean.jfx.spring.annotation.Splash;
import xdean.jfx.spring.context.FxContext;
import xdean.spring.auto.AutoSpringFactories;

@AutoSpringFactories(ApplicationListener.class)
public class FxSplash implements ApplicationListener<ApplicationStartingEvent>, ApplicationContextAware, Logable {

  private SplashService splash;

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {
    SpringApplication application = event.getSpringApplication();
    Class<?> mainClass = application.getMainApplicationClass();
    Splash anno = AnnotationUtils.getAnnotation(mainClass, Splash.class);
    if (anno == null) {
      debug("Not find @Splash, try find system properties.");
      if (!Boolean.getBoolean(Splash.SPLASH)) {
        debug("Not open splash, skip it.");
        return;
      }
    }
    debug("Splash enabled. Start fx context.");
    String[] args = System.getProperties().getProperty(FxContext.FX_ARGS, "").split(" ");
    Stage stage = null;
    try {
      stage = FxContext.start(args);
    } catch (InterruptedException e) {
      warn("Start fx context interrupted.");
      System.exit(1);
    }
    List<String> splashNames = SpringFactoriesLoader.loadFactoryNames(SplashService.class, getClass().getClassLoader());
    Assert.isTrue(splashNames.size() <= 1, "There should be only one splash service.");
    splash = SpringFactoriesLoader.loadFactories(SplashService.class, getClass().getClassLoader()).stream()
        .findFirst()
        .orElseGet(DefaultSplash::new);
    Stage primaryStage = stage;
    PlatformImpl.runAndWait(() -> splash.createSplash(primaryStage));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (splash != null) {
      Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext, "Application context must be configurable.");
      ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
      beanFactory.registerSingleton(splash.getClass().getName(), splash);
    }
  }
}