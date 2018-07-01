package xdean.jfx.spring.splash;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import xdean.spring.auto.AutoSpringFactories;

@AutoSpringFactories(ApplicationListener.class)
public class FxSplash implements ApplicationListener<ApplicationStartingEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {
    System.out.println(event.getSpringApplication().getMainApplicationClass());
  }
}
