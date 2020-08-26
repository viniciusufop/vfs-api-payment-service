package br.com.vfs.api.payment.service.restaurant;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(nullable = false)
    private String name;
    @Size(min = 1)
    @NotEmpty
    @ManyToMany
    @JoinTable
    private Set<PaymentMethod> paymentMethods;

    public Restaurant(@Email final String name, @Size(min = 1) @NotEmpty final Set<PaymentMethod> paymentMethods) {
        this.name = name;
        this.paymentMethods = paymentMethods;
    }

    public boolean accepted(final PaymentMethod paymentMethod) {
        return paymentMethods.contains(paymentMethod);
    }
}
