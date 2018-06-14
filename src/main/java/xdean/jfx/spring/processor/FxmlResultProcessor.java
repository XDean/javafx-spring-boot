package xdean.jfx.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import xdean.jfx.spring.FxmlResult;

@Component
public class FxmlResultProcessor implements BeanFactoryPostProcessor, AutowireCandidateResolver {

  private DefaultListableBeanFactory beanFactory;

  private FxControllerProcessor controllerProcessor;

  private AutowireCandidateResolver delegate;

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    if (!(beanFactory instanceof DefaultListableBeanFactory)) {
      throw new IllegalStateException(
          "CustomAutowireConfigurer needs to operate on a DefaultListableBeanFactory");
    }
    this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    AutowireCandidateResolver oldResolver = this.beanFactory.getAutowireCandidateResolver();
    this.delegate = oldResolver == null ? new SimpleAutowireCandidateResolver() : oldResolver;
    this.beanFactory.setAutowireCandidateResolver(this);
  }

  @Override
  public Object getSuggestedValue(DependencyDescriptor descriptor) {
    ResolvableType type = descriptor.getResolvableType();
    if (!type.getRawClass().equals(FxmlResult.class)) {
      return delegate.getSuggestedValue(descriptor);
    }
    if (controllerProcessor == null) {
      this.controllerProcessor = beanFactory.getBean(FxControllerProcessor.class);
    }
    ResolvableType controllerClass = type.getGeneric(0);
    ResolvableType rootClass = type.getGeneric(1);

    Object controller = beanFactory.getBean(controllerClass.getRawClass());
    Object root = controllerProcessor.getRoot(controller);
    if (!rootClass.isInstance(root)) {
      throw new BeanCreationException(String.format("Root element type of %s is mismatch, expect %s, but was %s.",
          controllerClass.getRawClass(), rootClass.getRawClass(), root == null ? null : root.getClass()));
    }

    return new FxmlResult<>(controller, root);
  }

  @Override
  public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
    return delegate.isAutowireCandidate(bdHolder, descriptor);
  }

  @Override
  public boolean isRequired(DependencyDescriptor descriptor) {
    return delegate.isRequired(descriptor);
  }

  @Override
  public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, String beanName) {
    return delegate.getLazyResolutionProxyIfNecessary(descriptor, beanName);
  }
}
