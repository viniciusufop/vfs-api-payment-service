package br.com.vfs.api.payment.service.paymentmethod;


import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentMethodDetail implements Serializable {
    private Long id;
    private String description;

    public PaymentMethodDetail(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }
}
