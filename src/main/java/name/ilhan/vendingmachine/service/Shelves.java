package name.ilhan.vendingmachine.service;

import name.ilhan.vendingmachine.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Shelves {
    public List<Product> products = new ArrayList<>();

    @Autowired
    public CoinBox coinBox;

    public Deliver insertCoins(Insert insert) {
        Deliver deliver = new Deliver();
        CoinBox.coins.add(insert.getCoin());
        Integer sumOfCoins = CoinBox.coins.stream().mapToInt(Coin::getValue).sum();
        String message = String.format("Coin %s successfully inserted. Total stotinki: %d", insert.coin.name(), sumOfCoins);
        deliver.setMessage(message);
        return deliver;
    }

    public Deliver buyProduct(ProductSelection productSelection) {
        Deliver deliver = new Deliver();
        if (!this.products.contains(productSelection.getProduct())) {
            if (this.products.isEmpty()) {
                deliver.setMessage("The shelves are empty. There is no product to select from.");
            } else {
                deliver.setMessage("Currently that product is not available currently. Select another product.");
            }
            return deliver;
        }

        Integer sumCoins = CoinBox.coins.stream().mapToInt(Coin::getValue).sum();
        if (productSelection.getProduct().getValue() <= sumCoins) {
            this.products.remove(productSelection.getProduct());
            deliver.setProduct(productSelection.getProduct());
            Integer returnToCustomerStotinki = sumCoins - productSelection.getProduct().getValue();
            ArrayList<Coin> coinsToReturn = new ArrayList<>();
            do {
                if (returnToCustomerStotinki >= Coin.LV2.getValue()) {
                    coinsToReturn.add(Coin.LV2);
                    returnToCustomerStotinki = returnToCustomerStotinki - Coin.LV2.getValue();
                } else if (returnToCustomerStotinki >= Coin.LV1.getValue()) {
                    coinsToReturn.add(Coin.LV1);
                    returnToCustomerStotinki = returnToCustomerStotinki - Coin.LV1.getValue();
                } else if (returnToCustomerStotinki >= Coin.ST50.getValue()) {
                    coinsToReturn.add(Coin.ST50);
                    returnToCustomerStotinki = returnToCustomerStotinki - Coin.ST50.getValue();
                } else if (returnToCustomerStotinki >= Coin.ST20.getValue()) {
                    coinsToReturn.add(Coin.ST20);
                    returnToCustomerStotinki = returnToCustomerStotinki - Coin.ST20.getValue();
                } else if (returnToCustomerStotinki >= Coin.ST10.getValue()) {
                    coinsToReturn.add(Coin.ST10);
                    returnToCustomerStotinki = returnToCustomerStotinki - Coin.ST10.getValue();
                }
            }
            while (returnToCustomerStotinki > 0);
            deliver.setCoins(coinsToReturn);
            CoinBox.coins.clear();
        } else {
            deliver.setMessage("Not enough money. Please put more coins.");
            return deliver;
        }
        deliver.setMessage("Please take both the product and the returned coins.");
        return deliver;
    }
}
