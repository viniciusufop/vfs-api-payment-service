package br.com.vfs.api.payment.service.gateways;

import br.com.vfs.api.payment.service.paymentmethod.CreditCardType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class GatewayInformation {

    @Positive
    @NotNull
    private final BigDecimal value;

    @NotNull
    private final CreditCardType creditCardType;
}
