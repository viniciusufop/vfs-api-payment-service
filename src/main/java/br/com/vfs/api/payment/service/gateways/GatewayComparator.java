package br.com.vfs.api.payment.service.gateways;

import java.util.Comparator;

public class GatewayComparator implements Comparator<GatewayCalculate> {
    @Override
    public int compare(GatewayCalculate o1, GatewayCalculate o2) {
        return o1.getRate().compareTo(o2.getRate());
    }
}
