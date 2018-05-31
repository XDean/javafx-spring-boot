package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import xdean.jfx.spring.starter.FxApplicationStarter;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@SpringBootApplication
@Import(FxApplicationStarter.class)
public @interface SpringFxApplication {

}
