package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicate the application should enable the splash.
 *
 * @author Dean Xu (XDean@github.com)
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Splash {
  String SPLASH = "fx.spring.splash";
}
