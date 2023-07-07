package name.ilhan.vendingmachine.service;

import name.ilhan.vendingmachine.model.Coin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinBox {
    public static List<Coin> coins = new ArrayList<>();
}
