package Factories;

import BookStrategies.IBookHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

/**
 * Created by admin on 10/8/2015.
 */
public interface IOrdersFactory {
    IBookHandler getBookHandler();
    void process(InputStream stream) throws Exception;
}
