package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.DependsOn;

import xdean.jfx.spring.starter.FxContext;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@DependsOn(FxContext.FX_CONTEXT)
public @interface FxReady {

}
