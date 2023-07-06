package name.ilhan.vendingmachine.controller;

import io.swagger.v3.oas.annotations.Operation;
import name.ilhan.vendingmachine.model.Coin;
import name.ilhan.vendingmachine.model.Product;
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

    private List<Product> products = List.of(Product.OREO, Product.DEYVIN, Product.OREO);

    @GetMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Return coins")
    public List<Coin> allContacts() {
        return List.of(Coin.LV1);
    }

    @PostMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Insert a coin")
    public String insertCoin(@RequestBody Coin coin) {
        coins.add(coin);
        return "Success";
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Select a product")
    public String selectProduct(@RequestBody Product product) {
        if(!products.contains(product)) {
            return "The product is not available";
        }

        Integer sumCoins = coins.stream().mapToInt(Coin::getValue).sum();
        if(product.getValue() <= sumCoins) {
            products.remove(product);
            System.out.println("Now we have: " + products);
            Integer returnToCustomerStotinki = sumCoins - product.getValue();

        }
        else {
            return "Not enough money. Please put more coins.";
        }
        return "efewfwe";
    }

}
