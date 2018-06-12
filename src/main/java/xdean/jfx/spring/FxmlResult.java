package xdean.jfx.spring;

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
