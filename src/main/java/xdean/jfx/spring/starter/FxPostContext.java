package xdean.jfx.spring.starter;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;

import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.annotation.FxReady;

@Configuration
@FxReady
public class FxPostContext {
  @Inject
  public void start(FxApplication app, Stage stage) throws Exception {
    app.start(stage);
  }
}
