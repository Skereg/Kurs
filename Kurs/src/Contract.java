import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Contract implements Serializable {
    protected int contractId;
    protected String marketName;
    protected String phoneManager;
    protected String phoneClient;
    protected int shopId;
    protected String product;
    protected int price;
    protected Date startDate;
    protected Date endDate;

    private static List<Contract> allContracts = new ArrayList<>();

    public Contract(int contractId, String marketName, String phoneManager, String phoneClient,
                    int shopId, String product, int price, Date startDate, Date endDate) {
        this.contractId = contractId;
        this.marketName = marketName;
        this.phoneManager = phoneManager;
        this.phoneClient = phoneClient;
        this.shopId = shopId;
        this.product = product;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        allContracts.add(this);
    }

    public static int getTotalContractsCount() {
        return allContracts.size();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(allContracts);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        if (allContracts == null) {
            allContracts = new ArrayList<>();
        }
        allContracts = (List<Contract>) ois.readObject();
    }


    @Override
    public String toString() {
        return "Contract ID: " + contractId + "\n" +
                "Market: " + marketName + "\n" +
                "Manager Phone: " + phoneManager + "\n" +
                "Client Phone: " + phoneClient + "\n" +
                "Shop ID: " + shopId + "\n" +
                "Product: " + product + "\n" +
                "Price: " + price + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate;
    }

}