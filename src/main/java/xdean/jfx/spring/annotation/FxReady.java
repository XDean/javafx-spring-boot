package xdean.jfx.spring.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.DependsOn;

import xdean.jfx.spring.context.FxContext;

/**
 * Indicate the bean depends on javafx context. It will be created after javafx context(UI thread)
 * initialized.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@DependsOn(FxContext.FX_CONTEXT)
public @interface FxReady {
}
