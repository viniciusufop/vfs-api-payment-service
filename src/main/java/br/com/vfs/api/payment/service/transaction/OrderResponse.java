package br.com.vfs.api.payment.service.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderResponse implements Serializable {
    @JsonProperty("valor")
    private BigDecimal value;
}
