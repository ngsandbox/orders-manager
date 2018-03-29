package Data;

import java.util.Comparator;

public class OrderBook {
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    private String _name;
    private BookLadder _bid;
    private BookLadder _ask;

    public OrderBook(String name) {
        _name = name;
        _bid = new BookLadder(Comparator.naturalOrder());
        _ask = new BookLadder(Comparator.reverseOrder());
    }

    public void addOrder(String orderId, String operation, int price, int quantity) {
        BookLadder storeLadder, matchLadder;
        switch (operation) {
            case BUY:
                storeLadder = _bid;
                matchLadder = _ask;
                break;
            case SELL:
                storeLadder = _ask;
                matchLadder = _bid;
                break;
            default:
                throw new UnsupportedOperationException(String.format("The operation type '%1' is not supported.", operation));
        }

        int restQuantity = matchLadder.matchPrice(price, quantity);
        if (restQuantity > 0) {
            storeLadder.addOrder(price, orderId, restQuantity);
        }
    }

    public void deleteOrder(String orderId) {
        if (!_bid.delOrder(orderId)) {
            _ask.delOrder(orderId);
        }
    }

    public void print() {
        System.out.print("Order book: ");
        System.out.println(_name);
        System.out.println("BIN\t - \tASK");
        System.out.println("Qty@Price\t - \tQty@Price");
        int bidSize = _bid.size();
        int askSize = _ask.size();
        OrdersContainer[] bidColl = _bid.values().toArray(new OrdersContainer[bidSize]);
        OrdersContainer[] askColl = _ask.values().toArray(new OrdersContainer[askSize]);
        int maxCount = Math.max(bidSize, askSize);
        for (int i = 0; i < maxCount; i++) {
            printCollPart(bidColl, i);

            System.out.print("\t - \t");
            printCollPart(askColl, i);

            System.out.println();
        }
        System.out.println();
        System.out.println("************************");
    }

    private void printCollPart(OrdersContainer[] coll, int i) {
        if (i < coll.length) {
            coll[i].print();
        } else {
            System.out.print("---------");
        }
    }

    public OrdersContainer getContainer(String operation, int price) {
        switch (operation) {
            case BUY:
                return _bid.get(price);
            case SELL:
                return _ask.get(price);
            default:
                throw new UnsupportedOperationException(String.format("The operation type '%1' is not supported.", operation));
        }
    }
}
