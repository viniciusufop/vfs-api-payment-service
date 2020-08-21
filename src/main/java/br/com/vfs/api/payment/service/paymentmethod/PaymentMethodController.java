package br.com.vfs.api.payment.service.paymentmethod;

import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @GetMapping
    @ResponseStatus(OK)
    public Set<PaymentMethodDetail> findByUserAndRestaurant(
            @Valid final FindPaymentMethod findPaymentMethod) {
        return findPaymentMethod.find(userRepository, restaurantRepository);
    }
}
