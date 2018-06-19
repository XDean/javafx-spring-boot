package xdean.jfx.spring.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.scheduling.annotation.Async;

import xdean.jfx.spring.starter.FxContext;

/**
 * Indicate the method should be executed in javafx UI thread.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Async(FxContext.FX_SCHEDULER)
public @interface FxThread {
}
