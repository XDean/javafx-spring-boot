# JavaFX Spring Boot
[![Build Status](https://travis-ci.org/XDean/javafx-spring-boot.svg?branch=master)](https://travis-ci.org/XDean/javafx-spring-boot)
[![codecov.io](http://codecov.io/github/XDean/javafx-spring-boot/coverage.svg?branch=master)](https://codecov.io/gh/XDean/javafx-spring-boot/branch/master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.XDean/javafx-spring-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.XDean/javafx-spring-boot)

Build your JavaFX Application in pure spring way!

# [Hello JavaFX SpringBoot!](src/test/java/xdean/jfx/spring/sample)

1. Write a FXML file `Sample.fxml` with a hello button to say hello.

```xml
<HBox xmlns:fx="http://javafx.com/fxml" fx:controller="xdean.jfx.spring.sample.SampleController">
  <Button fx:id="button" text="sayHello" onAction="#sayHello"/> 
</HBox>
```

2. Create the controller class `SampleController`

```java
public class SampleController {
  @FXML
  Button button;
  
  @FXML
  public void sayHello() {
  }
}
```

3. Let the controller be a javafx controller bean

```java
@FxController(fxml = "/Sample.fxml") // bind to the fxml resource
public class SampleController {
  ...
}
```

4. Inject the hello world service

```java
@FxController(fxml = "/Sample.fxml") // bind to the fxml resource
public class SampleController {
  @FXML
  Button button; // javafx bean
  
  @Inject
  SampleService service; // spring bean
  
  @FXML
  public void sayHello() {
    service.sayHello();
  }
}
```

5. Create an entrance to run the javafx-spring-boot application. `SampleApp`

```java
@SpringFxApplication // indicate this is a spring-fx application
public class SampleApp implements FxApplication { // FxApplication play the entrance role like Application
  public static void main(String[] args) {
    SpringApplication.run(SampleApp.class, args); // run as spring application
  }

  @Inject
  FxmlResult<SampleController, Parent> fxml; // load and inject the controller

  @Override
  public void start(Stage stage) throws Exception {
    Scene scene = new Scene(fxml.getRoot(), 400, 300);
    stage.setTitle("Hello JavaFX-Spring-Boot");
    stage.setScene(scene);
    stage.show();
  }
}
```

## Other Sample

[JavaFX CSS Editor](https://github.com/XDean/CSS-Editor-FX), a complete javafx-spring-boot application.
