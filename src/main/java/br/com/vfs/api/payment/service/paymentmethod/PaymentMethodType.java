package br.com.vfs.api.payment.service.paymentmethod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentMethodType {
    CARD(true), MONEY(false), MACHINE(false), CHECK(false);

    @Getter
    private final boolean online;

    public boolean isOffline(){
        return !online;
    }
}
