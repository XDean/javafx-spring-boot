package xdean.jfx.spring.context;

public interface FxContextPostProcessor {
  default void beforeStart() {
  }

  default void afterStart() {
  }
}
