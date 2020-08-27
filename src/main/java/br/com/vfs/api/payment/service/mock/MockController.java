package br.com.vfs.api.payment.service.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Random;

@RestController
public class MockController {

    @GetMapping("/api/pedidos/{idPedido}")
    public MockOrder order(@PathVariable("idPedido") @NotNull @Valid final Long idOrder){
        if(invalidOrder(idOrder)) throw new IllegalArgumentException("invalid order");
        final Random random = new Random();
        double value = random.nextDouble()*1000;
        return new MockOrder(idOrder, new BigDecimal(value).setScale(2, RoundingMode.DOWN));
    }

    private boolean invalidOrder(final Long idOrder) {
        return idOrder % 2 == 0;
    }
}
