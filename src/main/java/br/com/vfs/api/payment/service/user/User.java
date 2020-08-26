package br.com.vfs.api.payment.service.user;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.paymentmethod.fraudster.PaymentFraudster;
import br.com.vfs.api.payment.service.restaurant.Restaurant;
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
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(nullable = false)
    private String email;
    @Size(min = 1)
    @NotEmpty
    @ManyToMany
    @JoinTable
    private Set<PaymentMethod> paymentMethods;

    public User(@Email final String email, @Size(min = 1) @NotEmpty final Set<PaymentMethod> paymentMethods) {
        this.email = email;
        this.paymentMethods = paymentMethods;
    }

    public Set<PaymentMethod> filterPaymentMethods(final Restaurant restaurant, final Collection<PaymentFraudster> paymentFraudsters){
        return paymentMethods
                .stream()
                .filter(restaurant::accepted)
                .filter(paymentMethod -> validatePaymentFraudsters(paymentFraudsters, paymentMethod))
                .collect(Collectors.toSet());
    }

    private boolean validatePaymentFraudsters(Collection<PaymentFraudster> paymentFraudsters, PaymentMethod p) {
        return paymentFraudsters
                .stream()
                .allMatch(fraudster -> fraudster.validPaymentMethods(p, this));
    }
}
