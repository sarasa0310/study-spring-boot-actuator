package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceV1 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);
    private final MeterRegistry registry;

    @Override
    public void order() {
        int current = stock.decrementAndGet();
        log.info("주문 완료! 현재 재고 = {}", current);

        Counter counter = Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry);

        counter.increment();
    }

    @Override
    public void cancel() {
        int current = stock.incrementAndGet();
        log.info("취소 완료! 현재 재고 = {}", current);

        Counter counter = Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancel") // 태그로 구분 가능
                .description("order")
                .register(registry);

        counter.increment();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

}
