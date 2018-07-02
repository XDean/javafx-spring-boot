package xdean.jfx.spring.splash;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xdean.jfxex.support.DragSupport;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ProgressBar;

public class DefaultSplash implements SplashService, PreloadReporter {

  private @FXML AnchorPane root;
  private @FXML AnchorPane container;
  private @FXML Label majorTaskLabel;
  private @FXML Label majorProgressLabel;
  private @FXML Label minorTaskLabel;
  private @FXML Label minorProgressLabel;
  private @FXML ProgressBar progressBar;

  @Override
  public void createSplash(Stage primaryStage) {
    Stage stage = new Stage(StageStyle.UNDECORATED);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DefaultSplash.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      throw new Error("Load default splash fxml fail.", e);
    }

    stage.setScene(new Scene(root));

    stage.setAlwaysOnTop(true);
    stage.setWidth(700);
    stage.setHeight(400);
    stage.show();

    DragSupport.bind(stage);
  }

  @Override
  public SubReporter load(String title) {
    return null;
  }

  @Override
  public void done() {

  }
}
