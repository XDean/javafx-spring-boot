package xdean.jfx.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Platform;
import xdean.jex.log.Logable;
import xdean.jfx.spring.annotation.FxAsync;
import xdean.jfx.spring.annotation.FxReady;
import xdean.jfx.spring.annotation.FxSync;

@Configuration
@EnableAsync
@FxReady
public class FxSchedulerConfiguration implements Logable{
  @Bean
  @Qualifier(FxSync.SCHEDULER)
  public TaskExecutor sync() {
    debug("Init fx sync TaskExecutor");
    return task -> PlatformImpl.runAndWait(task);
  }

  @Bean
  @Qualifier(FxAsync.SCHEDULER)
  public TaskExecutor async() {
    debug("Init fx async TaskExecutor");
    return task -> Platform.runLater(task);
  }
}
