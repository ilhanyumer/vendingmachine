package name.ilhan.vendingmachine.controller;

import io.swagger.v3.oas.annotations.Operation;
import name.ilhan.vendingmachine.model.Coin;
import name.ilhan.vendingmachine.model.Deliver;
import name.ilhan.vendingmachine.model.Insert;
import name.ilhan.vendingmachine.model.ProductSelection;
import name.ilhan.vendingmachine.service.CoinBox;
import name.ilhan.vendingmachine.service.MaintenanceService;
import name.ilhan.vendingmachine.service.Shelves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
public class ClientController {

    private CoinBox coinBox;

    @Autowired
    private Shelves shelves;

    @GetMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Show how much money is inserted.")
    public Deliver showCoins() {
        Integer sumOfCoins = CoinBox.coins.stream().mapToInt(Coin::getValue).sum();
        Deliver deliver = new Deliver();
        String message = String.format("You have inserted %d stotinki.", sumOfCoins);
        deliver.setMessage(message);
        return deliver;
    }

    @PostMapping(value = "/coins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Insert a coin. Please insert one coin at a time. One by one.")
    public Deliver insertCoin(@RequestBody Insert insert) {
        Deliver deliver = new Deliver();

        if (MaintenanceService.maintenance) {
            deliver.setMessage("The vending machine is in maintenance mode. Please use it later.");
            return deliver;
        }

        CoinBox.coins.add(insert.getCoin());
        Integer sumOfCoins = CoinBox.coins.stream().mapToInt(Coin::getValue).sum();
        String message = String.format("Coin %s successfully inserted. Total stotinki: %d", insert.coin.name(), sumOfCoins);
        deliver.setMessage(message);
        return deliver;
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Select a product.")
    public Deliver selectProduct(@RequestBody ProductSelection productSelection) {

        if (MaintenanceService.maintenance) {
            Deliver deliver = new Deliver();
            deliver.setMessage("The vending machine is in maintenance mode. Please use it later.");
            return deliver;
        }

        return shelves.buyProduct(productSelection);
    }

}
