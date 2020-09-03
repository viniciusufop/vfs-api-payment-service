package br.com.vfs.api.payment.service.transaction.online;

import br.com.vfs.api.payment.service.gateways.Gateway;
import br.com.vfs.api.payment.service.gateways.GatewayCalculate;
import br.com.vfs.api.payment.service.gateways.GatewayComparator;
import br.com.vfs.api.payment.service.gateways.GatewayInformation;
import br.com.vfs.api.payment.service.paymentmethod.CreditCardType;
import br.com.vfs.api.payment.service.paymentmethod.PaymentMethod;
import br.com.vfs.api.payment.service.paymentmethod.PaymentMethodRepository;
import br.com.vfs.api.payment.service.restaurant.RestaurantRepository;
import br.com.vfs.api.payment.service.transaction.ExistOrderValidator;
import br.com.vfs.api.payment.service.transaction.NewOrder;
import br.com.vfs.api.payment.service.transaction.OrderIntegration;
import br.com.vfs.api.payment.service.transaction.PaymentOfflineValidator;
import br.com.vfs.api.payment.service.transaction.PaymentValidByUserAndRestaurantValidator;
import br.com.vfs.api.payment.service.transaction.Transaction;
import br.com.vfs.api.payment.service.transaction.TransactionRepository;
import br.com.vfs.api.payment.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;

@RestController
@RequestMapping("/api/transactions/creditcard")
@RequiredArgsConstructor
public class OnlineTransactionController {


    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionRepository transactionRepository;
    private final OrderIntegration orderIntegration;
    private final PaymentGateway paymentGateway;


    @InitBinder("newOrderCreditCard")
    public void init(final WebDataBinder dataBinder){
        dataBinder.addValidators(new ExistOrderValidator(orderIntegration));
        dataBinder.addValidators(new PaymentCreditCardValidator(paymentMethodRepository));
        dataBinder.addValidators(new PaymentValidByUserAndRestaurantValidator(userRepository, restaurantRepository));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(CREATED)
    public ResponseEntity<Void> create(final NewOrderCreditCard newOrderCreditCard) {
        final var transaction = newOrderCreditCard.toTransaction(userRepository, restaurantRepository, orderIntegration);
        final var result = paymentGateway.execute(transaction);
        transactionRepository.save(result);
        if(result.isCancel()){
            return ResponseEntity.status(PAYMENT_REQUIRED).build();
        }
        return ResponseEntity.status(CREATED).build();
    }
}
