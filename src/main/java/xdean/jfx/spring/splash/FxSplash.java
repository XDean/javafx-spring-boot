package xdean.jfx.spring.splash;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;

import xdean.jex.log.Logable;
import xdean.jfx.spring.annotation.Splash;
import xdean.jfx.spring.context.FxContext;
import xdean.spring.auto.AutoSpringFactories;

@AutoSpringFactories(ApplicationListener.class)
public class FxSplash implements ApplicationListener<ApplicationStartingEvent>, Logable {

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {
    Class<?> mainClass = event.getSpringApplication().getMainApplicationClass();
    Splash splash = AnnotationUtils.getAnnotation(mainClass, Splash.class);
    if (splash == null) {
      debug("Not find @Splash, try find system properties.");
      if (!Boolean.getBoolean(Splash.SPLASH)) {
        debug("Not open splash, skip it.");
        return;
      }
    }
    debug("Splash enabled. Start fx context.");
    String[] args = System.getProperties().getProperty(FxContext.FX_ARGS, "").split(" ");
    try {
      FxContext.start(args);
    } catch (InterruptedException e) {
      warn("Start fx context interrupted.");
      System.exit(1);
    }
  }
}
