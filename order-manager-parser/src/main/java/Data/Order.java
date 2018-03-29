package Data;

/**
 * Created by admin on 10/8/2015.
 */
public class Order {
    private final OrdersContainer _container;
    private int _quantity;

    public final String _orderId;

    public Order(OrdersContainer container, String orderId, int qty) {
        _container = container;
        _orderId = orderId;
        _quantity = qty;
    }

    public int getQty() {
        return _quantity;
    }

    public OrdersContainer getContainer() {
        return _container;
    }

    public void setQty(int qty) {
        this._quantity = qty;
    }
}
