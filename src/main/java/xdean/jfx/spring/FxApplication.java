package xdean.jfx.spring;

import javafx.stage.Stage;
import xdean.jfx.spring.annotation.FxSync;

public interface FxApplication {
  @FxSync
  void start(Stage stage) throws Exception;
}
