package xdean.jfx.spring.starter;

import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javafx.application.Application;
import javafx.stage.Stage;
import xdean.jex.log.Logable;

@Component(FxContext.FX_CONTEXT)
public class FxContext implements Logable {
  public static final String FX_CONTEXT = "fx.context";
  public static final String FX_ARGS = "fx.args";
  public static final String FX_PRIMARY_STAGE = "fx.stage.primary";

  private static final CountDownLatch STARTED = new CountDownLatch(1);
  private static FxContext context;
  private static Stage primaryStage;

  public FxContext() {
    Assert.state(context == null, "FxContext must be unique.");
    context = this;
  }

  @Inject
  public void launchFxApplication(@Named(FX_ARGS) @Autowired(required=false) String[] args) throws InterruptedException {
    info("Start to init fx context.");
    new Thread(() -> Application.launch(ActualFxApplication.class, args), "Fx Start Thread").start();
    STARTED.await();
  }

  @Bean(FX_PRIMARY_STAGE)
  public Stage primaryStage() {
    return primaryStage;
  }

  public static class ActualFxApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
      FxContext.primaryStage = primaryStage;
      STARTED.countDown();
    }
  }
}
