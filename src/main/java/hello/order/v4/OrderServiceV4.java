package hello.order.v4;

import hello.order.OrderService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV4 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    @Timed("my.order")
    public void order() {
        int current = stock.decrementAndGet();
        log.info("주문 완료! 현재 재고 = {}", current);
        sleep(500); // 0.5 ~ 0.7초 지연
    }

    @Override
    @Timed("my.order")
    public void cancel() {
        int current = stock.incrementAndGet();
        log.info("취소 완료! 현재 재고 = {}", current);
        sleep(200); // 0.2 ~ 0.4초 지연
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
