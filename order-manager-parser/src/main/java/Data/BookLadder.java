package Data;

import java.util.*;

public class BookLadder extends TreeMap<Integer, OrdersContainer> {

    private final Map<String, Order> _existingOrders;

    public BookLadder(Comparator<? super Integer> comparator) {
        super(comparator);
        _existingOrders = new HashMap<>();
    }

    public void addOrder(Integer price, String orderId, int quantity) {
        OrdersContainer container = this.get(price);
        if (container == null) {
            container = new OrdersContainer(this, price);
            this.put(price, container);
        }

        _existingOrders.put(orderId, container.addOrder(orderId, quantity));
    }

    public boolean delOrder(String orderId) {
        Order order = _existingOrders.get(orderId);
        boolean contains;
        if (contains = order != null) {
            order.getContainer().delOrder(order);
            delOrder(order);
        }

        return contains;
    }

    final protected boolean delOrder(Order order) {
        return _existingOrders.remove(order._orderId) != null;
    }

    public int matchPrice(int newPrice, int newQuantity) {
        int restQuantity = newQuantity;
        if (restQuantity > 0 && !isEmpty()) {
            Map.Entry<Integer, OrdersContainer> firstEntry = firstEntry();
            if (comparator().compare(newPrice, firstEntry.getKey()) > -1) {
                OrdersContainer aggregate = firstEntry.getValue();
                restQuantity = aggregate.matchQuantity(restQuantity);
                if (aggregate.getQty() == 0) {
                    this.pollFirstEntry();
                }

                restQuantity = matchPrice(newPrice, restQuantity);
            }
        }

        return restQuantity;
    }
}
