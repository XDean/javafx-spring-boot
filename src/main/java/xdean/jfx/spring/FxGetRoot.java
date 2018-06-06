package xdean.jfx.spring;

import xdean.jfx.spring.starter.FxControllerProcessor;

public interface FxGetRoot<T> {
  default T getRoot() {
    return FxControllerProcessor.getRoot(this);
  }
}
