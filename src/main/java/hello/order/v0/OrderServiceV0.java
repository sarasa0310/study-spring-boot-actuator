package hello.order.v0;

import hello.order.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV0 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        int current = stock.decrementAndGet();
        log.info("주문 완료! 현재 재고 = {}", current);
    }

    @Override
    public void cancel() {
        int current = stock.incrementAndGet();
        log.info("취소 완료! 현재 재고 = {}", current);
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

}
