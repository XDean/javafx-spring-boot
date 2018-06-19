package xdean.jfx.spring.sample;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
  @Override
  public void sayHello() {
    System.out.println("Hello javafx-spring-boot!");
  }
}
