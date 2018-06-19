package xdean.jfx.spring.context;

import xdean.jfx.spring.FxApplication;
import xdean.jfx.spring.annotation.FxThread;

/**
 * Hook interface to do actions before and after {@link FxApplication}.
 * 
 * @author Dean Xu (XDean@github.com)
 */
@FxThread
public interface FxContextPostProcessor {
  /**
   * Do something before {@link FxApplication}.
   */
  default void beforeStart() {
  }

  /**
   * Do something after {@link FxApplication}.
   */
  default void afterStart() {
  }
}
