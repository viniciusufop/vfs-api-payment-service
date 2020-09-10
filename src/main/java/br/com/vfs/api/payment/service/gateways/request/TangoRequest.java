package br.com.vfs.api.payment.service.gateways.request;

import br.com.vfs.api.payment.service.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TangoRequest implements Serializable {

    @JsonProperty("num_cartao")
    private final String card;
    @JsonProperty("codigo_seguranca")
    private final int ccv;
    @JsonProperty("valor_compra")
    private final BigDecimal value;

    public TangoRequest(final Transaction transaction) {
        final var paymentInformation = transaction.getPaymentInformation();
        this.card = paymentInformation.get("cardNumber");
        this.ccv = Integer.parseInt(paymentInformation.get("ccv"));
        this.value = transaction.getValue();
    }
}
