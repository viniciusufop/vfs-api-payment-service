package br.com.vfs.api.payment.service.paymentmethod;

import br.com.vfs.api.payment.service.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PaymentMethod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType type;
    @Enumerated(EnumType.STRING)
    @Column
    private CreditCardType creditCardType;


    public PaymentMethod(@NotBlank final String name, @NotBlank final String description,
                         @NotNull final PaymentMethodType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public void setCreditCardType(final CreditCardType creditCardType) {
        Assert.isTrue(type == PaymentMethodType.CARD, "Only card payment method has credit card type");
        this.creditCardType = creditCardType;
    }
}
