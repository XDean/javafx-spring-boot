package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xdean.deannotation.checker.CheckField;
import xdean.deannotation.checker.CheckType;

/**
 * Indicate the string value is used as a bean name.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@CheckField(type = @CheckType(String.class))
public @interface BeanName {
  /**
   * The bean type.
   */
  Class<?> value();
}
