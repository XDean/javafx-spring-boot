//package xdean.jfx.spring.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.annotation.EnableAsync;
//
//import javafx.application.Platform;
//import xdean.jex.log.Logable;
//import xdean.jfx.spring.annotation.FxReady;
//import xdean.jfx.spring.annotation.FxThread;
//
//@Configuration
//@EnableAsync(proxyTargetClass = true)
//@FxReady
//public class FxSchedulerConfiguration implements Logable {
//  @Bean
//  @Qualifier(FxThread.SCHEDULER)
//  public TaskExecutor fxScheduler() {
//    debug("Init fx sync TaskExecutor");
//    return task -> {
//      if (Platform.isFxApplicationThread()) {
//        task.run();
//      } else {
//        Platform.runLater(task);
//      }
//    };
//  }
//}
