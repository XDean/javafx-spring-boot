package xdean.jfx.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Platform;
import xdean.jfx.spring.annotation.FxAsync;
import xdean.jfx.spring.annotation.FxSync;

@Configuration
@EnableAsync
public class FxSchedulerConfiguration {
  @Bean
  @Qualifier(FxSync.SCHEDULER)
  public TaskExecutor sync() {
    return task -> PlatformImpl.runAndWait(task);
  }

  @Bean
  @Qualifier(FxAsync.SCHEDULER)
  public TaskExecutor async() {
    return task -> Platform.runLater(task);
  }
}
