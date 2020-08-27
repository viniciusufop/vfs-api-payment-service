package br.com.vfs.api.payment.service.transaction.confirm;

import br.com.vfs.api.payment.service.transaction.TransactionRepository;
import br.com.vfs.api.payment.service.transaction.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class NotExistConfirmTransactionValidator implements Validator {
    private final TransactionRepository transactionRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ConfirmTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var confirmTransaction = (ConfirmTransaction) target;
        if(transactionRepository
                .existsByOrderIdAndStatus(confirmTransaction.getOrderId(), TransactionStatus.CONFIRM)){
            errors.rejectValue("orderId", null, " exist transaction payment by confirm status");
        }
    }
}
