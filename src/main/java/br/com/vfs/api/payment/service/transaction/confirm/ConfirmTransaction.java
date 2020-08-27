package br.com.vfs.api.payment.service.transaction.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ConfirmTransaction {

    @NotNull
    private Long orderId;
}
