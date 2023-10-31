package hello.order.v4;

import hello.order.OrderService;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV4 {

    @Bean
    public OrderService orderService() {
        return new OrderServiceV4();
    }

    @Bean // @Timed 동작시키기 위해서 TimedAspect 빈 등록 필요!
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}
