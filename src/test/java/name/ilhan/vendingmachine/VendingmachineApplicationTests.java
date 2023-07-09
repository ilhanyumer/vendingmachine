package name.ilhan.vendingmachine;

import name.ilhan.vendingmachine.model.*;
import name.ilhan.vendingmachine.service.CoinBox;
import name.ilhan.vendingmachine.service.Shelves;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class VendingmachineApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testShowCoins() {
        Insert insert = new Insert();
        insert.setCoin(Coin.ST20);
        Shelves shelves = new Shelves();
        shelves.insertCoins(insert);
        Deliver deliver = shelves.showCoins();
        String message = deliver.getMessage();
        Product product = deliver.getProduct();
        ArrayList<Coin> coins = deliver.getCoins();

        assertEquals(message, "You have inserted 20 stotinki.");
        assertNull(product);
        assertNull(coins);

        CoinBox.coins.clear();
    }

    @Test
    void testShelves() {
        Insert insert = new Insert();
        insert.setCoin(Coin.LV1);
        Shelves shelves = new Shelves();
        shelves.products.add(Product.OREO);
        shelves.insertCoins(insert);
        ProductSelection productSelection = new ProductSelection();
        productSelection.setProduct(Product.OREO);
        Deliver deliver = shelves.buyProduct(productSelection);
        String message = deliver.getMessage();
        ArrayList<Coin> coins = deliver.getCoins();
        Product product = deliver.getProduct();

        assertEquals(message, "Please take both the product and the returned coins.");
        assertEquals(coins.size(), 1);
        assertEquals(coins.get(0).getValue(), Coin.ST20.getValue());
        assertEquals(product, Product.OREO);
    }

}
