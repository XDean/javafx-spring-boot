package xdean.jfx.spring.starter;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javafx.stage.Stage;
import xdean.jfx.spring.config.FxSchedulerConfiguration;

@Configuration
@Import(FxSchedulerConfiguration.class)
public class FxApplicationStarter {

  public static final String FX_ARGS = "fx.args";

  @Bean(FX_ARGS)
  public String[] fxArgs(@Value(FX_ARGS) String args) {
    return args.split(" ");
  }

  @Bean
  public Stage primaryStage(FxApplication app, @Named(FX_ARGS) String[] args) throws InterruptedException {
    app.launch(args);
    return app.getPrimaryStage();
  }
}
