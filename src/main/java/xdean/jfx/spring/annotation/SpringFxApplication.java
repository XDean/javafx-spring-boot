package xdean.jfx.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import xdean.jfx.spring.FxApplicationStarter;

/**
 * Indicate the class is a javafx application.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
@SpringBootApplication
@Import(FxApplicationStarter.class)
public @interface SpringFxApplication {

}
