package xdean.jfx.spring;

import xdean.jfx.spring.processor.FxControllerProcessor;

public interface FxGetRoot<T> {
  default T getRoot() {
    return FxControllerProcessor.getRoot(this);
  }
}
