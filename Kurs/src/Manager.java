import java.io.Serializable;

class Manager extends Person implements Serializable {
    protected String marketName;

    public Manager(String name, String phone, String marketName) {
        super(name, phone);
        this.marketName = marketName;
    }

    @Override
    public String getContactInfo() {
        return "Manager: " + name + ", Phone: " + phone + ", Market: " + marketName;
    }

    public String getMarketName() {
        return marketName;
    }
}