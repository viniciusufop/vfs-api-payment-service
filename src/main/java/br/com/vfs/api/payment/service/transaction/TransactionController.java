package br.com.vfs.api.payment.service.transaction;

import br.com.vfs.api.payment.service.paymentmethod.PaymentMethodRepository;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderIntegration orderIntegration;

    @InitBinder("newOrder")
    public void init(final WebDataBinder dataBinder){
        dataBinder.addValidators(new ExistOrderValidator(orderIntegration));
        dataBinder.addValidators(new PaymentOfflineValidator(paymentMethodRepository));
        dataBinder.addValidators(new PaymentValidByUserAndRestaurantValidator(userRepository, restaurantRepository));
    }

    @PostMapping
    @ResponseStatus(OK)
    public Long create(@RequestBody  @Valid final NewOrder newOrder) {
        final var transaction = newOrder.toTransaction(userRepository, restaurantRepository, orderIntegration);
        return transaction.getId();
    }
}
