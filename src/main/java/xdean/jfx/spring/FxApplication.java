package xdean.jfx.spring;

import javafx.stage.Stage;
import xdean.jfx.spring.annotation.FxThread;

public interface FxApplication {
  @FxThread
  void start(Stage stage) throws Exception;
}
