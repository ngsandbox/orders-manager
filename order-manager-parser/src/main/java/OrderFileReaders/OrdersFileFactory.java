package OrderFileReaders;

import java.io.IOException;

/**
 * Created by admin on 10/8/2015.
 */
public class OrdersFileFactory {
    public static IOrdersService getReader(String fileName) throws IOException, NullPointerException, UnsupportedOperationException {
        if(fileName == null || fileName.isEmpty()){
            throw new NullPointerException("FIle name is not specified!");
        }

        if(fileName.endsWith(".zip")){
            return new ZipOrdersService(fileName);
        }
        if(fileName.endsWith(".xml")){
            return new XmlOrdersService(fileName);
        }

        throw new UnsupportedOperationException(String.format("For fileName %1 extension type is not recognized", fileName));
    }
}
