package xdean.jfx.spring;

import xdean.jfx.spring.annotation.FxController;
import xdean.jfx.spring.annotation.FxThread;

/**
 * Interface to be implemented by beans that need to react once both javafx and spring beans set.
 * It's usually used in {@link FxController} class.
 *
 * @author Dean Xu (XDean@github.com)
 */
@FxThread
public interface FxInitializable {
  /**
   * Invoked by BeanFactory after it has been set all javafx beans and spring beans.
   */
  void initAfterFxSpringReady();
}
