package OrderFileReaders;

import Factories.IOrdersFactory;

import java.io.IOException;

/**
 * Created by admin on 10/8/2015.
 */
public interface IOrdersService {

    void processStream(IOrdersFactory ordersFactory) throws Exception;

    /**
     *
     * @throws IOException
     */
    void close() throws IOException;
}
