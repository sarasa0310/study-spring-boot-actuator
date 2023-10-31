package hello.order.v3;

import hello.order.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceV3 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);
    private final MeterRegistry registry;

    @Override
    public void order() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry);

        timer.record(() -> {
            int current = stock.decrementAndGet();
            log.info("주문 완료! 현재 재고 = {}", current);
            sleep(500); // 0.5 ~ 0.7초 지연
        });
    }

    @Override
    public void cancel() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancel")
                .description("order")
                .register(registry);

        timer.record(() -> {
            int current = stock.incrementAndGet();
            log.info("취소 완료! 현재 재고 = {}", current);
            sleep(200); // 0.2 ~ 0.4초 지연
        });

    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

    private void sleep(int millis) {
        try { // 측정을 위해 의도적으로 지연
            Thread.sleep(millis + new Random().nextInt(200));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
