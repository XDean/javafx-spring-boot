package xdean.jfx.spring.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xdean.jfx.spring.config.FxSchedulerConfiguration;

@Configuration
@Import({
    FxContext.class,
    FxSchedulerConfiguration.class,
    FxControllerProcessor.class,
    FxPostContext.class
})
public class FxApplicationStarter {

}
