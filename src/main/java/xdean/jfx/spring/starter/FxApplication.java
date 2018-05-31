package xdean.jfx.spring.starter;

import java.util.concurrent.CountDownLatch;

import org.springframework.util.Assert;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class FxApplication {

  private static FxApplication application;
  private static InnerApplication innerApp;

  private static final CountDownLatch STARTED = new CountDownLatch(1);
  private Stage primaryStage;

  public final void launch(String[] args) throws InterruptedException {
    Assert.state(application == null, "Application launch must not be called more than once");
    application = this;
    new Thread(() -> Application.launch(InnerApplication.class, args), "Fx Starter Thread").start();
    STARTED.await();
  }

  protected void init() throws Exception {
  }

  protected abstract void start(Stage primaryStage) throws Exception;

  protected void stop() throws Exception {
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static final class InnerApplication extends Application {

    private final FxApplication app;

    public InnerApplication() {
      Assert.state(innerApp == null, "Application launch must not be called more than once.");
      Assert.state(application != null, "Application must launch by FxApplication.");
      innerApp = this;
      this.app = application;
    }

    @Override
    public void init() throws Exception {
      app.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      app.primaryStage = primaryStage;
      app.start(primaryStage);
      STARTED.countDown();
    }

    @Override
    public void stop() throws Exception {
      app.stop();
    }
  }
}
