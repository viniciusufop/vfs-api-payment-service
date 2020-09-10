package br.com.vfs.api.payment.service.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Order implements Serializable {
    private final Long id;
    private final BigDecimal value;

    public Order(@NotNull Long id, @NotNull OrderResponse orderResponse) {
        this.id = id;
        this.value = orderResponse.getValue();
    }
}
