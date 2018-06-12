package xdean.jfx.spring.starter;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;

import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.annotation.FxReady;

@Configuration
@FxReady
public class FxPostContext {

  @Inject
  List<FxContextPostProcessor> processors;

  @Inject
  public void start(FxApplication app, Stage stage) throws Exception {
    processors.forEach(p -> p.beforeStart());
    app.start(stage);
    processors.forEach(p -> p.afterStart());
  }
}
