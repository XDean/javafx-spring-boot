package xdean.jfx.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Platform;
import xdean.jfx.spring.annotation.FxAsync;
import xdean.jfx.spring.annotation.FxSync;

@Configuration
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
