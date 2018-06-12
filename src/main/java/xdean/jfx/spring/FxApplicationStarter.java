package xdean.jfx.spring;

import org.springframework.context.annotation.Import;

import xdean.jfx.spring.config.FxSchedulerConfiguration;
import xdean.jfx.spring.processor.FxControllerProcessor;
import xdean.jfx.spring.processor.FxmlResultProcessor;
import xdean.jfx.spring.starter.FxContext;
import xdean.jfx.spring.starter.FxPostContext;

@Import({
    FxContext.class,
    FxSchedulerConfiguration.class,
    FxPostContext.class,
    FxControllerProcessor.class,
    FxmlResultProcessor.class
})
public class FxApplicationStarter {

}
