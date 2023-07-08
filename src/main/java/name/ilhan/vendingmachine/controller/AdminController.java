package name.ilhan.vendingmachine.controller;

import io.swagger.v3.oas.annotations.Operation;
import name.ilhan.vendingmachine.model.Maintenance;
import name.ilhan.vendingmachine.model.Product;
import name.ilhan.vendingmachine.service.MaintenanceService;
import name.ilhan.vendingmachine.service.Shelves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private Shelves shelves;

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fill the vending machine with products. The products are: OREO, MORENI, and DEYVIN.")
    public List<Product> insertProducts(@RequestBody List<Product> products) throws Exception {

        for (Product product : products) {
            Integer occurrences = Collections.frequency(this.shelves.products, product);
            final Integer LIMIT = 10;
            if (occurrences < LIMIT) {
                this.shelves.products.add(product);
            } else {
                String errorMessage = String.format("The machine can hold at most %d %ss.", LIMIT, product);
                throw new Exception(errorMessage);
            }
        }

        return this.shelves.products;
    }

    @DeleteMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove products from the vending machine.")
    public List<Product> deleteProducts(@RequestBody List<Product> products) {
        for (Product product : products) {
            this.shelves.products.remove(product);
        }
        return this.shelves.products;
    }

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Show products that are placed into the vending machine.")
    public List<Product> showProducts() {
        return this.shelves.products;
    }

    @PostMapping(value = "/maintenance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Switch maintenance mode on or off.")
    public Maintenance changeMaintenanceMode(@RequestBody Maintenance maintenance) {
        MaintenanceService.maintenance = maintenance.getMaintenanceMode();
        return maintenance;
    }
}
