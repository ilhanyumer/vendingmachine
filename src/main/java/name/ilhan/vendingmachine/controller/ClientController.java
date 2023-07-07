package name.ilhan.vendingmachine.controller;

import io.swagger.v3.oas.annotations.Operation;
import name.ilhan.vendingmachine.model.*;
import name.ilhan.vendingmachine.service.Shelves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    private List<Coin> coins = new ArrayList<>();

    @Autowired
    private Shelves shelves;

    @GetMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Return coins")
    public List<Coin> allContacts() {
        return List.of(Coin.LV1);
    }

    @PostMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Insert a coin. Please insert one coin at a time. One by one.")
    public Deliver insertCoin(@RequestBody Insert insert) {
        coins.add(insert.getCoin());
        Deliver deliver = new Deliver();
        deliver.setMessage("Success");
        return deliver;
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Select a product")
    public Deliver selectProduct(@RequestBody ProductSelection productSelection) {
        Deliver deliver = new Deliver();
        if (!shelves.products.contains(productSelection.getProduct())) {
            deliver.setMessage("The product is not available");
            return deliver;
        }

        Integer sumCoins = coins.stream().mapToInt(Coin::getValue).sum();
        if (productSelection.getProduct().getValue() <= sumCoins) {
            shelves.products.remove(productSelection.getProduct());
            deliver.setProduct(productSelection.getProduct());
            System.out.println("Now we have: " + shelves.products);
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
        } else {
            deliver.setMessage("Not enough money. Please put more coins.");
            return deliver;
        }
        deliver.setMessage("Please take the product");
        return deliver;
    }

}
