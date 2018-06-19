package xdean.jfx.spring.sample;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import xdean.jfx.spring.annotation.FxController;

@FxController(fxml = "/Sample.fxml")
public class SampleController {

  @FXML
  Button button;

  @Inject
  SampleService service;

  @FXML
  public void sayHello() {
    service.sayHello();
  }
}
