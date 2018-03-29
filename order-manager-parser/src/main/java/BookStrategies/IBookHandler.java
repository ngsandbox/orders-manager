package BookStrategies;

/**
 * Created by admin on 10/11/2015.
 */
public interface IBookHandler {

    void addOrder(String bookName, String orderId, String operation, String price, int quantity);

    void deleteOrder(String bookName, String orderId);

    void print();
}
