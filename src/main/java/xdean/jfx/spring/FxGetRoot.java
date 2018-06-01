package xdean.jfx.spring;

public interface FxGetRoot<T> {
  default T getRoot() {
    throw new UnsupportedOperationException();
  }
}
