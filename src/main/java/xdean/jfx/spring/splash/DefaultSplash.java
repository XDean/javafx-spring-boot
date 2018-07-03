package xdean.jfx.spring.splash;

import java.io.IOException;

import org.springframework.util.Assert;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import xdean.jex.log.Logable;
import xdean.jfxex.support.DragSupport;

public class DefaultSplash implements SplashService, PreloadReporter, Logable {

  private @FXML AnchorPane root;
  private @FXML AnchorPane container;
  private @FXML Label majorTaskLabel;
  private @FXML Label majorProgressLabel;
  private @FXML Label minorTaskLabel;
  private @FXML Label minorProgressLabel;
  private @FXML ProgressBar progressBar;

  private Stage primaryStage;
  private Stage splashStage;
  private boolean done;

  @Override
  public void createSplash(Stage primaryStage) {
    debug("Create default splash");
    this.primaryStage = primaryStage;
    splashStage = new Stage(StageStyle.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DefaultSplash.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      throw new Error("Load default splash fxml fail.", e);
    }

    splashStage.setScene(new Scene(root));
    splashStage.setAlwaysOnTop(true);
    splashStage.setWidth(700);
    splashStage.setHeight(400);
    splashStage.show();

    DragSupport.bind(splashStage);

    primaryStage.setOnShowing(e -> hideSplash(false));
  }

  @Override
  public SubReporter load(String title) {
    return new SubReporter() {
      int count = -1;
      int task;

      @Override
      public void setCount(int count) {
        Assert.isTrue(this.count == -1, "Can't set count twice");
        this.count = count;
      }

      @Override
      public void load(String subTitle) {
        run(() -> {
          this.task++;
          majorTaskLabel.setText(title);
          minorTaskLabel.setText(subTitle);
          if (count >= task) {
            minorProgressLabel.setText(String.format("(%d/%d)", task, count));
          } else {
            minorProgressLabel.setText("");
          }
        });
      }
    };
  }

  @Override
  public void done() {
    hideSplash(true);
  }

  public void hideSplash(boolean showPrimary) {
    if (!done) {
      run(() -> {
        if (!done) {
          done = true;
          debug("Close splash stage");
          Timeline timeline = new Timeline();
          timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500),
              new KeyValue(splashStage.getScene().getRoot().opacityProperty(), 0.3)));
          timeline.setOnFinished(e -> {
            if (showPrimary) {
              primaryStage.show();
            }
            splashStage.close();
          });
          timeline.play();
        }
      });
    }
  }

  private void run(Runnable task) {
    if (Platform.isFxApplicationThread()) {
      task.run();
    } else {
      Platform.runLater(task);
    }
  }
}
