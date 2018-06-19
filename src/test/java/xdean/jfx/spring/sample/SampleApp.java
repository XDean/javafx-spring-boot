package xdean.jfx.spring.sample;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.FxmlResult;
import xdean.jfx.spring.annotation.SpringFxApplication;

@SpringFxApplication
public class SampleApp implements FxApplication {
  public static void main(String[] args) {
    SpringApplication.run(SampleApp.class, args);
  }

  @Inject
  FxmlResult<SampleController, Parent> controller;

  @Override
  public void start(Stage stage) throws Exception {
    Scene scene = new Scene(controller.getRoot(), 400, 300);

    stage.setTitle("Hello JavaFX-Spring-Boot");
    stage.setScene(scene);
    stage.show();
  }
}
