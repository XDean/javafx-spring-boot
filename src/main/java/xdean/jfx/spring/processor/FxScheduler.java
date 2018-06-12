package xdean.jfx.spring.processor;

import org.springframework.core.task.TaskExecutor;

import javafx.application.Platform;

public class FxScheduler implements TaskExecutor {
  @Override
  public void execute(Runnable task) {
    if (Platform.isFxApplicationThread()) {
      task.run();
    } else {
      Platform.runLater(task);
    }
  }
}
