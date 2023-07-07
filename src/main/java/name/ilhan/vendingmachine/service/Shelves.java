package name.ilhan.vendingmachine.service;

import name.ilhan.vendingmachine.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Shelves {
    public List<Product> products = new ArrayList<>();
}
