package Data;

import java.util.ArrayDeque;

/**
 * Created by admin on 10/10/2015.
 */
public class OrdersContainer extends ArrayDeque<Order> {
    public static final int PRICE_SCALE = 100;
    public final int _price;

    private int _qty;
    private final BookLadder _ladder;


    public OrdersContainer(BookLadder ladder, int price) {
        _price = price;
        _ladder = ladder;
    }

    public BookLadder getLadder() {
        return _ladder;
    }

    public int getQty() {
        return _qty;
    }

    public Order addOrder(String orderId, int quantity) {
        Order order = new Order(this, orderId, quantity);
        this.addLast(order);
        _qty += quantity;
        return order;
    }

    public boolean delOrder(Order order) {
        boolean contains = this.removeFirstOccurrence(order);
        if (contains) {
            _qty -= order.getQty();
            if (_qty <= 0) {
                getLadder().remove(_price);
            }
        }

        return contains;
    }

    public int matchQuantity(int newQuantity) {
        int restQty = newQuantity;
        if (restQty > 0 && _qty > 0) {//!this.isEmpty()
            restQty = subQuantity(restQty);
            restQty = matchQuantity(restQty);
        }

        return restQty;
    }

    private int subQuantity(int restQty) {
        Order existOrder = this.getFirst();
        int orderQty = existOrder.getQty();
        int minQty = Math.min(restQty, orderQty);
        _qty -= minQty;
        restQty -= minQty;
        orderQty -= minQty;

        if (orderQty == 0) {
            this.removeFirst();
            getLadder().delOrder(existOrder);
        } else {
            existOrder.setQty(orderQty);
        }

        return restQty;
    }


    public void print() {
        System.out.print(_qty);
        System.out.print("@");
        System.out.print(_price / (float) OrdersContainer.PRICE_SCALE);
    }
}
