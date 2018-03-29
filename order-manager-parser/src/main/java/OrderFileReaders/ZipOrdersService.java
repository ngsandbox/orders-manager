package OrderFileReaders;

import Factories.IOrdersFactory;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by admin on 10/8/2015.
 */
public class ZipOrdersService implements IOrdersService {
    ZipFile _zip;
    long _startedAt = System.currentTimeMillis();

    public ZipOrdersService(String fileName) throws IOException {
        File file = new File(fileName);
        _zip = new ZipFile(file);
    }

    @Override
    public void processStream(IOrdersFactory ordersFactory) throws Exception {
        Enumeration<? extends ZipEntry> entries = _zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            XmlOrdersService ordersService = null;
            try {
                ordersService = new XmlOrdersService(_zip.getInputStream(entry));
                ordersService.processStream(ordersFactory);
            } finally {
                if (ordersService != null) {
                    ordersService.close();
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (_zip != null) {
            _zip.close();
            _zip = null;
            long finishedAt = System.currentTimeMillis();
            System.out.println("ZipOrdersService worked for " + ((System.currentTimeMillis() - _startedAt) / 1000));
        }
    }
}
