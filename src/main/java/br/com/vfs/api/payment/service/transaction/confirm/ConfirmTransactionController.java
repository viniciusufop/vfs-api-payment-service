package br.com.vfs.api.payment.service.transaction.confirm;

import br.com.vfs.api.payment.service.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ConfirmTransactionController {

    private final TransactionRepository transactionRepository;

    @InitBinder("confirmTransaction")
    public void init(final WebDataBinder dataBinder){
        dataBinder.addValidators(new ExistPendingTransactionValidator(transactionRepository));
        dataBinder.addValidators(new NotExistConfirmTransactionValidator(transactionRepository));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(CREATED)
    @RequestMapping("/api/transactions/confirm")
    public Long create(@RequestBody @Valid final ConfirmTransaction confirmTransaction) {
        final var transaction = transactionRepository.findById(confirmTransaction.getOrderId()).orElseThrow();
        return transactionRepository.save(transaction.toConfirm()).getId();
    }
}
