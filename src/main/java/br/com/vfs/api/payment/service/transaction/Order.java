package br.com.vfs.api.payment.service.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Order implements Serializable {
    private Long id;
    private BigDecimal value;
}
