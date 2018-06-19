package xdean.jfx.spring.context;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.annotation.FxReady;

@Configuration
@FxReady
public class FxPostContext {

  @Autowired(required = false)
  List<FxContextPostProcessor> processors = Collections.emptyList();

  @Inject
  public void start(FxApplication app, Stage stage) throws Exception {
    processors.forEach(p -> p.beforeStart());
    app.start(stage);
    processors.forEach(p -> p.afterStart());
  }
}
