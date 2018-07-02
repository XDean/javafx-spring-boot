package xdean.jfx.spring;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entrance of javafx spring-boot application.
 *
 * @author Dean Xu (XDean@github.com)
 */
public interface FxApplication {
  /**
   * The main entry point for all JavaFX-spring-boot applications. The start method is called after
   * the system is ready for the application to begin running.
   *
   * NOTE: This method is called on the JavaFX Application Thread.
   *
   * @see Application#start(Stage)
   * @param stage the primary stage for this application, onto which the application scene
   *          can be set. The primary stage will be embedded in the browser if the application was
   *          launched as an applet. Applications may create other stages, if needed, but they will
   *          not be primary stages and will not be embedded in the browser.
   */
  void start(Stage stage) throws Exception;
}
