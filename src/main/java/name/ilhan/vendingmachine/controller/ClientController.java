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

        if (MaintenanceService.maintenance) {
            Deliver deliver = new Deliver();
            deliver.setMessage("The vending machine is in maintenance mode. Please use it later.");
            return deliver;
        }

        return shelves.insertCoins(insert);
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
