package xdean.jfx.spring.context;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.FxInitializable;
import xdean.jfx.spring.annotation.FxReady;

/**
 * This class is to start the {@link FxApplication} after javafx context ready.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@FxReady
@Configuration
public class FxPostContext implements FxInitializable {

  @Autowired(required = false)
  List<FxContextPostProcessor> processors = Collections.emptyList();

  @Inject
  FxApplication app;

  @Inject
  @Named(FxContext.FX_PRIMARY_STAGE)
  Stage stage;

  @Override
  public void initAfterFxSpringReady() {
    processors.forEach(p -> p.beforeStart());
    try {
      app.start(stage);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    processors.forEach(p -> p.afterStart());
  }
}
