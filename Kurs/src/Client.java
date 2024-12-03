import java.io.Serializable;

class Client extends Person implements Serializable {
    private int contractId;

    public Client(String name, String phone, int contractId) {
        super(name, phone);
        this.contractId = contractId;
    }

    @Override
    public String getContactInfo() {
        return "Client: " + name + ", Phone: " + phone + ", Contract ID: " + contractId;
    }

    public int getContractId() {
        return contractId;
    }
}