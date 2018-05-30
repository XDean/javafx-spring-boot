package xdean.jfx.spring.starter;

import javafx.application.Application;
import javafx.stage.Stage;

public class FxApplication {

  private static InnerApplication instance;

  public void launch(String[] args) {
    Application.launch(InnerApplication.class, args);
  }
  
  

  public static class InnerApplication extends Application {
    
    private Stage stage;
    
    public InnerApplication() {
      if (instance != null) {
        throw new IllegalStateException("Application launch must not be called more than once");
      }
      instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      this.stage = primaryStage;
    }
  }
}
