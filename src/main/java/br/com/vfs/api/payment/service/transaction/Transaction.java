package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.restaurant.Restaurant;
import br.com.vfs.api.payment.service.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @PastOrPresent
    private LocalDateTime createdAt;
    @NotNull
    private Long orderId;
    @NotNull
    private BigDecimal value;
    @NotNull
    @ManyToOne
    private User user;
    @NotNull
    @ManyToOne
    private Restaurant restaurant;
    @NotNull
    private TransactionStatus status;
    @NotNull
    @ManyToOne
    private PaymentMethod paymentMethod;
    @ElementCollection
    private Set<String> paymentInformation;

    public Transaction(@NotNull Long orderId, @NotNull BigDecimal value, @NotNull User user,
                       @NotNull Restaurant restaurant, @NotNull TransactionStatus status,
                       @NotNull PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.value = value;
        this.user = user;
        this.restaurant = restaurant;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
