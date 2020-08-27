package br.com.vfs.api.payment.service.mock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MockOrder implements Serializable {
    private Long id;
    private BigDecimal value;
}
