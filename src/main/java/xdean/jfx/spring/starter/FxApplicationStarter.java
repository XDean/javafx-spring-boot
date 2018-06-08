package xdean.jfx.spring.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xdean.jfx.spring.config.FxSchedulerConfiguration;
import xdean.jfx.spring.processor.FxControllerProcessor;
import xdean.jfx.spring.processor.FxInitializableProcessor;

@Configuration
@Import({
    FxContext.class,
    FxSchedulerConfiguration.class,
    FxInitializableProcessor.class,
    FxControllerProcessor.class,
    FxPostContext.class
})
public class FxApplicationStarter {

}
