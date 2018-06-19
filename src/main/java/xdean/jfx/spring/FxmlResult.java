package xdean.jfx.spring;

/**
 * Bean to auto inject a fxml load result.
 * 
 * Consider you have a fxml resource whose root element is `HBox` and controlled by
 * `YourController`. You can use following bean definition to get the controller and root element
 * together.
 * 
 * <pre>
 * &#64;Inject
 * FxmlResult&#60;YourController, HBox&#62; // If you are not interested of root element type, you can replace HBox by Parent
 * </pre>
 * 
 * 
 * @author Dean Xu (XDean@github.com)
 *
 * @param <C> controller type
 * @param <R> root type
 */
public class FxmlResult<C, R> {
  private final C controller;
  private final R root;

  public FxmlResult(C controller, R root) {
    this.controller = controller;
    this.root = root;
  }

  public C getController() {
    return controller;
  }

  public R getRoot() {
    return root;
  }
}
