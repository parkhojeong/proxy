package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;

@Slf4j
public class ProxyFactoryConfigV2 {
    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace) {
        OrderControllerV2 target = new OrderControllerV2(orderService(logTrace));

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();
        log.info("proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV2 orderService(LogTrace logTrace) {
        OrderServiceV2 target = new OrderServiceV2(repositoryV1(logTrace));

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();
        log.info("proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV2 repositoryV1(LogTrace logTrace) {
        OrderRepositoryV2 target = new OrderRepositoryV2();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();
        log.info("proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    private static @NonNull Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }
}
