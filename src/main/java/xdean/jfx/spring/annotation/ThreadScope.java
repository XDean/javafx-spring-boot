package xdean.jfx.spring.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Scope("thread")
public @interface ThreadScope {

}
