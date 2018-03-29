package OrderFileReaders;

import Factories.IOrdersFactory;

import java.io.*;

/**
 * Created by admin on 10/8/2015.
 */
public class XmlOrdersService implements IOrdersService {

    private InputStream _stream;
    long _startedAt = System.currentTimeMillis();

    public XmlOrdersService(String fileName) throws FileNotFoundException {
        FileInputStream fileStream = new FileInputStream(fileName);
        _stream = new BufferedInputStream(fileStream);
    }

    public XmlOrdersService(InputStream stream) throws IOException {
        if(stream == null){
            throw new NullPointerException("Stream is not specified");
        }

        _stream = new BufferedInputStream(stream);
    }

    @Override
    public void processStream(IOrdersFactory ordersFactory) throws Exception {
        ordersFactory.process(_stream);
    }

    @Override
    public void close() throws IOException {
        if(_stream != null){
            _stream.close();
            _stream = null;
            System.out.println("XmlOrdersService worked for " + ((System.currentTimeMillis() - _startedAt) / 1000));
        }
    }
}
