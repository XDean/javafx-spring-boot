package xdean.jfx.spring.context;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.stage.Stage;
import xdean.jex.log.Log;
import xdean.jex.log.LogFactory;
import xdean.jex.log.Logable;
import xdean.jfx.spring.annotation.BeanName;
import xdean.jfx.spring.processor.FxScheduler;

/**
 * JavaFX Context. This component will start the javafx platform and provide the
 * primary stage.
 *
 * @author Dean Xu (XDean@github.com)
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component(FxContext.FX_CONTEXT)
public class FxContext implements Logable, InitializingBean {
  /**
   * FxContext.
   */
  @BeanName(FxContext.class)
  public static final String FX_CONTEXT = "fx.context";

  /**
   * JavaFX argument. JavaFX application will starts with the args if exists.
   */
  public static final String FX_ARGS = "fx.args";

  /**
   * JavaFX primary stage.
   */
  @BeanName(Stage.class)
  public static final String FX_PRIMARY_STAGE = "fx.stage.primary";

  /**
   * JavaFX UI thread executor.
   */
  @BeanName(TaskExecutor.class)
  public static final String FX_SCHEDULER = "fx.scheduler";

  private static final Log LOG = LogFactory.from(FxContext.class);
  private static final CountDownLatch STARTED = new CountDownLatch(1);
  private static Stage primaryStage;

  @Value("#{'${" + FX_ARGS + ":}'.split(' ')}")
  private String[] args;

  @Override
  public void afterPropertiesSet() throws Exception {
    start(args);
  }

  public static Stage start(String[] args) throws InterruptedException {
    if (STARTED.getCount() == 0) {
      LOG.debug("Fx Context has been stated.");
      return primaryStage;
    }
    LOG.info("Start to init fx context with args: " + Arrays.toString(args));
    new Thread(() -> Application.launch(ActualFxApplication.class, args), "Fx Start Thread").start();
    STARTED.await();
    LOG.info("Fx Context (UI Thread) started");
    return primaryStage;
  }

  @Bean(FX_PRIMARY_STAGE)
  public Stage primaryStage() {
    debug("Init fx primary stage bean");
    return primaryStage;
  }

  @Bean(FX_SCHEDULER)
  public TaskExecutor scheduler() {
    debug("Init fx scheduler bean");
    return new FxScheduler();
  }

  public static class ActualFxApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
      FxContext.primaryStage = primaryStage;
      STARTED.countDown();
    }
  }
}
