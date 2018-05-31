package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.scheduling.annotation.Async;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Async(FxSync.SCHEDULER)
public @interface FxSync {
  String SCHEDULER = "scheduler.fx.sync";
}