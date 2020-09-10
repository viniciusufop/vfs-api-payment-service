package br.com.vfs.api.payment.service.gateways.request;

import br.com.vfs.api.payment.service.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SeyaCardRequest implements Serializable {

    @JsonProperty("num_cartao")
    private final String card;
    @JsonProperty("codigo_seguranca")
    private final int ccv;

    public SeyaCardRequest(final Transaction transaction) {
        final var paymentInformation = transaction.getPaymentInformation();
        this.card = paymentInformation.get("cardNumber");
        this.ccv = Integer.parseInt(paymentInformation.get("ccv"));
    }
}
