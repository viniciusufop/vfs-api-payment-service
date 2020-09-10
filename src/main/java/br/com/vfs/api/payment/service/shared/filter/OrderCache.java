package br.com.vfs.api.payment.service.shared.filter;

import br.com.vfs.api.payment.service.transaction.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderCache {
    private final ThreadLocal<Order> thread = new ThreadLocal<>();

    public Order getOrder() {
        return thread.get();
    }

    public void setOrder(final Order order) {
        thread.set(order);
    }

    public void clean() {
        thread.remove();
    }
}