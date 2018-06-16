package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Inherited
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@FxComponent
public @interface FxController {
  String fxml();

  @AliasFor(annotation = FxComponent.class)
  String value() default "";
}
