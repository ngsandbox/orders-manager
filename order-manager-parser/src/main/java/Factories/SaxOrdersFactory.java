package Factories;

import BookStrategies.IBookHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

/**
 * Created by admin on 10/8/2015.
 */
public class SaxOrdersFactory implements IOrdersFactory {
    IBookHandler _bookHandler;


    public SaxOrdersFactory(IBookHandler bookHandler) {
        _bookHandler = bookHandler;
    }

    @Override
    public void process(InputStream stream) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setXIncludeAware(false);
        SAXParser saxParser = factory.newSAXParser();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("AddOrder")) {
                    parseAddOrder(attributes);
                } else if (qName.equalsIgnoreCase("DeleteOrder")) {
                    parseDeleteOrder(attributes);
                }
            }


        };
        saxParser.parse(stream, handler);
    }

    private void parseDeleteOrder(Attributes attributes) {
        String bookName = attributes.getValue("book");
        String orderId = attributes.getValue("orderId");
        getBookHandler().deleteOrder(bookName, orderId);
    }

    private void parseAddOrder(Attributes attributes) {
        String bookName = attributes.getValue("book");
        String strPrice = attributes.getValue("price");
        int vol = Integer.parseInt(attributes.getValue("volume"));
        String orderId = attributes.getValue("orderId");
        String operation = attributes.getValue("operation");
        getBookHandler().addOrder(bookName, orderId, operation, strPrice, vol);
    }

    @Override
    public IBookHandler getBookHandler() {
        return _bookHandler;
    }
}
