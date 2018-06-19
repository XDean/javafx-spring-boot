package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * Indicate the class is a javafx component. JavaFx component default has prototype scope.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface FxComponent {

  @AliasFor(annotation = Component.class)
  String value() default "";
}
