package xdean.jfx.spring.splash;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import xdean.jex.log.Logable;

@Component
@ConditionalOnMissingBean(PreloadReporter.class)
public class DefaultPreloadReporter implements PreloadReporter, Logable {

  @Override
  public SubReporter load(String title) {
    debug("preload " + title);
    return new SubReporter() {
      int count = -1;
      int index;

      @Override
      public void setCount(int count) {
        this.count = count;
      }

      @Override
      public void load(String subTitle) {
        index++;
        if (count == -1) {
          debug(String.format("preload %s - %s", title, subTitle));
        } else {
          debug(String.format("preload %s - %s (%d/%d)", title, subTitle, index, count));
        }
      }
    };
  }

  @Override
  public void done() {
    debug("preload done");
  }
}
