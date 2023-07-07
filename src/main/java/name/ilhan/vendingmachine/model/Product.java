package name.ilhan.vendingmachine.model;

public enum Product {
    OREO(80),
    DEYVIN(50),
    MORENI(70);

    private final int value;

    Product(final int stotinki) {
        value = stotinki;
    }

    public int getValue() {
        return value;
    }
}
