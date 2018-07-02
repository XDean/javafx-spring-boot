package xdean.jfx.spring.splash;

public interface PreloadReporter {

  SubReporter load(String title);

  void done();

  interface SubReporter {
    void setCount(int count);

    void load(String subTitle);
  }
}
