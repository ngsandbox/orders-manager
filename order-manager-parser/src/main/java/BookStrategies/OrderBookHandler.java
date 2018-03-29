package BookStrategies;

import Data.OrderBook;
import Data.OrdersContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderBookHandler implements IBookHandler {
    public final Map<String, OrderBook> ORDER_BOOKS = new HashMap<>();
    public final Map<String, Integer> _pricesCash = new HashMap<>();

    @Override
    public void addOrder(String bookName, String orderId, String operation, String strPrice, int quantity) {
        OrderBook book = ORDER_BOOKS.get(bookName);
        if (book == null) {
            book = new OrderBook(bookName);
            ORDER_BOOKS.put(bookName, book);
        }

        int price = getPrice(strPrice);
        book.addOrder(orderId, operation, price, quantity);
    }

    final protected int getPrice(String strPrice) {
        int price;
        if (_pricesCash.containsKey(strPrice)) {
            price = _pricesCash.get(strPrice);
        } else {
            price = convertPrice(strPrice);
            _pricesCash.put(strPrice, price);
        }
        return price;
    }

    final protected int convertPrice(String strPrice) {
        return Math.round(OrdersContainer.PRICE_SCALE * Float.valueOf(strPrice));
    }

    @Override
    public void deleteOrder(String bookName, String orderId) {
        OrderBook book = ORDER_BOOKS.get(bookName);
        if (book != null) {
            book.deleteOrder(orderId);
        }
    }

    public OrdersContainer getContainer(String bookName, String operation, int price) {
        OrderBook orderBook = ORDER_BOOKS.get(bookName);
        if (orderBook != null) {
            return orderBook.getContainer(operation, price);
        }

        return null;
    }

    @Override
    public void print() {
        Collection<OrderBook> books = ORDER_BOOKS.values();
        books.forEach(Data.OrderBook::print);
    }
}
