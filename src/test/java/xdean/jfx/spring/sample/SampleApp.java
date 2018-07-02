package xdean.jfx.spring.sample;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.FxmlResult;
import xdean.jfx.spring.annotation.Splash;
import xdean.jfx.spring.annotation.SpringFxApplication;
import xdean.jfx.spring.splash.PreloadReporter;
import xdean.jfx.spring.splash.PreloadReporter.SubReporter;

@Splash
@SpringFxApplication
public class SampleApp implements FxApplication {
  public static void main(String[] args) {
    SpringApplication.run(SampleApp.class, args);
  }

  private @Inject PreloadReporter preloadReporter;

  private @Inject FxmlResult<SampleController, Parent> controller;

  @Override
  public void start(Stage stage) throws Exception {
    Scene scene = new Scene(controller.getRoot(), 400, 300);
    stage.setTitle("Hello JavaFX-Spring-Boot");
    stage.setScene(scene);
    stage.show();
  }

  @PostConstruct
  public void mockPreload() throws InterruptedException {
    int task = (int) (4 + 3 * Math.random());
    for (int i = 1; i < task; i++) {
      SubReporter subReporter = preloadReporter.load("Preload task " + i);
      int subTask = (int) (4 + 3 * Math.random());
      subReporter.setCount(subTask);
      for (int s = 1; s < subTask; s++) {
        subReporter.load("Sub preload task " + i + "." + s);
        Thread.sleep((long) (Math.random() * 300 + 100));
      }
    }
    preloadReporter.done();
  }
}
