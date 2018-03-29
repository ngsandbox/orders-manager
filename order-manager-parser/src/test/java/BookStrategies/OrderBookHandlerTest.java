package BookStrategies;

import Data.OrderBook;
import Data.OrdersContainer;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class OrderBookHandlerTest {

    OrderBookHandler handler;

    @Before
    public void beforeStart() {
        handler = new OrderBookHandler();
        handler.addOrder("book1", "123456", OrderBook.BUY, "567.6", 100);
        handler.addOrder("book2", "123458", OrderBook.BUY, "567.5", 70);
        handler.addOrder("book2", "123477", OrderBook.BUY, "567.5", 80);
        handler.addOrder("book1", "123459", OrderBook.SELL, "567.6", 120);
        handler.addOrder("book2", "123410", OrderBook.BUY, "567.8", 50);
    }

    @Test()
    public void testPriceConvert() throws Exception {
        int price = handler.convertPrice("599.67");
        assertEquals(price, 59967);
    }

    @Test()
    public void testEmptyOrder() throws Exception {
        OrdersContainer container = handler.getContainer("book2", OrderBook.SELL, handler.convertPrice("567.6"));
        assertEquals(container, null);
    }

    @Test()
    public void testSellLadderQuantity() throws Exception {
        OrdersContainer container = handler.getContainer("book1", OrderBook.SELL, handler.convertPrice("567.6"));
        assertEquals(container.getQty(), 20);
    }

    @Test()
    public void testBuyLadderQuantity() throws Exception {
        OrdersContainer container = handler.getContainer("book2", OrderBook.BUY, handler.convertPrice("567.5"));
        assertEquals("The quantity of the book2 does not equal to the sum of two orders", container.getQty(), 150);
    }

    @Test()
    public void testDeletedPrice() throws Exception {
        handler.deleteOrder("book1", "123459");
        OrdersContainer container = handler.getContainer("book1", OrderBook.SELL, handler.convertPrice("567.6"));
        assertNull("The container should be null after deleting last order", container);
    }
}