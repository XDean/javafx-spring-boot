package xdean.jfx.spring.starter;

public interface FxContextPostProcessor {
  default void beforeStart() {
  }

  default void afterStart() {
  }
}
