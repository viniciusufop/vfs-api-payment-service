package br.com.vfs.api.payment.service.gateways;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class GatewayCalculate {

    private final BigDecimal rate;
    private final boolean accept;
    private final Gateway gateway;
}
