package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AliasFor;

import xdean.jfx.spring.FxInitializable;

/**
 * Indicate the class is javafx controller, which will be initialized by fxml loader.
 * 
 * @see FxInitializable
 * @author Dean Xu (XDean@github.com)
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Lazy
@FxComponent
public @interface FxController {
  /**
   * The fxml resource path. The path rule is same as {@link Class#getResource(String)}.
   */
  String fxml();

  @AliasFor(annotation = FxComponent.class, attribute = "value")
  String name() default "";
}
