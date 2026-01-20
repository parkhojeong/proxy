package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
        client.execute();
        client.execute();
    }
    @Test
    void decorator() {
        Component component = new MessageDecorator(new RealComponent());
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    void decorator2() {
        Component component = new TimeDecorator(new MessageDecorator(new RealComponent()));
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }
}
