package name.ilhan.vendingmachine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Deliver {
    public String message;
    public Product product;
    public ArrayList<Coin> coins;
}
