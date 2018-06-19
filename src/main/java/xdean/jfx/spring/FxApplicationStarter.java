package xdean.jfx.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Dean Xu (XDean@github.com)
 */
@EnableAsync(proxyTargetClass = true)
@ComponentScan
public class FxApplicationStarter {

}
