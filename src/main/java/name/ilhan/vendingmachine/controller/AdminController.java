package name.ilhan.vendingmachine.controller;

import io.swagger.v3.oas.annotations.Operation;
import name.ilhan.vendingmachine.model.Product;
import name.ilhan.vendingmachine.service.Shelves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private Shelves shelves;

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fill the machine with products")
    public List<Product> insertProducts(@RequestBody List<Product> products) {
        this.shelves.products.addAll(products);
        return this.shelves.products;
    }
}
