package xdean.jfx.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@ComponentScan
public class FxApplicationStarter {

}
