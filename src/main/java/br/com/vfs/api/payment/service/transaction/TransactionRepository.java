package br.com.vfs.api.payment.service.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    boolean existsByOrderIdAndStatus(final Long orderId, final TransactionStatus status);
}
