package name.ilhan.vendingmachine.model;

public enum Coin {
    ST10(10),
    ST20(20),
    ST50(50),
    LV1(100),
    LV2(200);

    private final int value;

    Coin(final int stotinki) {
        value = stotinki;
    }

    public int getValue() {
        return value;
    }
}
