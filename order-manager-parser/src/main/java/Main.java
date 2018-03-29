import BookStrategies.IBookHandler;
import BookStrategies.OrderBookHandler;
import Factories.IOrdersFactory;
import Factories.SaxOrdersFactory;
import OrderFileReaders.IOrdersService;
import OrderFileReaders.OrdersFileFactory;


public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("Please provide full path to XML or ZIP file");
            System.exit(0);
        }

        String filePath = args[0];
        IBookHandler bookHandler = new OrderBookHandler();
        IOrdersService fileFactory = OrdersFileFactory.getReader(filePath);
        IOrdersFactory fileProcessor = new SaxOrdersFactory(bookHandler);
        try {
            fileFactory.processStream(fileProcessor);
        } finally {
            if (fileFactory != null) {
                fileFactory.close();
            }
        }

        bookHandler.print();
    }
}

