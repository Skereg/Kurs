import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Main {
    private static final String DATA_FILE = "data.ser";
    private JFrame frame;
    private JTextArea outputArea;
    private List<Manager> managers;
    private List<Client> clients;
    private List<Contract> contracts;

    public Main() {
        managers = new ArrayList<>();
        clients = new ArrayList<>();
        contracts = new ArrayList<>();

        loadData();

        frame = new JFrame("Contract Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 540);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton createManagerButton = new JButton("Create Manager");
        createManagerButton.addActionListener(e -> createManager());

        JButton createClientButton = new JButton("Create Client");
        createClientButton.addActionListener(e -> createClient());

        JButton createContractButton = new JButton("Create Contract");
        createContractButton.addActionListener(e -> createContract());

        JButton showContractsButton = new JButton("Show Contracts");
        showContractsButton.addActionListener(e -> displayAllContracts());

        JButton showManagersButton = new JButton("Show Managers");
        showManagersButton.addActionListener(e -> displayAllManagers());

        JButton showClientsButton = new JButton("Show Clients");
        showClientsButton.addActionListener(e -> displayAllClients());

        JButton countShopsButton = new JButton("Count Shops");
        countShopsButton.addActionListener(e -> countShops());

        JButton getProductTypesButton = new JButton("Get Product Types");
        getProductTypesButton.addActionListener(e -> getProductTypes());

        JButton longestContractButton = new JButton("Longest Contract");
        longestContractButton.addActionListener(e -> longestContract());

        JButton highestPriceButton = new JButton("Highest Price");
        highestPriceButton.addActionListener(e -> highestPrice());

        JButton avgLengthButton = new JButton("Average Length");
        avgLengthButton.addActionListener(e -> avgLength());

        JButton deleteManagerButton = new JButton("Delete Manager");
        deleteManagerButton.addActionListener(e -> deleteManager());

        JButton deleteClientButton = new JButton("Delete Client");
        deleteClientButton.addActionListener(e -> deleteClient());

        JButton deleteContractButton = new JButton("Delete Contract");
        deleteContractButton.addActionListener(e -> deleteContract());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        buttonPanel.add(createManagerButton);
        buttonPanel.add(createClientButton);
        buttonPanel.add(createContractButton);
        buttonPanel.add(countShopsButton);
        buttonPanel.add(showManagersButton);
        buttonPanel.add(showClientsButton);
        buttonPanel.add(showContractsButton);
        buttonPanel.add(getProductTypesButton);
        buttonPanel.add(deleteManagerButton);
        buttonPanel.add(deleteClientButton);
        buttonPanel.add(deleteContractButton);
        buttonPanel.add(longestContractButton);
        buttonPanel.add(highestPriceButton);
        buttonPanel.add(avgLengthButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(managers);
            out.writeObject(clients);
            out.writeObject(contracts);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                managers = (List<Manager>) in.readObject();
                clients = (List<Client>) in.readObject();
                contracts = (List<Contract>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Error loading data from file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void countShops() {
        outputArea.setText("Total number of shops: " + Contract.getTotalContractsCount() + "\n");
    }

    private void getProductTypes() {
        outputArea.setText("Unique Product Types:\n");

        List<String> products = new ArrayList<>();
        for (Contract contract : contracts) {
            if (!products.contains(contract.product)) {
                products.add(contract.product);
            }
        }

        if (products.isEmpty()) {
            outputArea.append("No products available.\n");
        } else {
            for (String product : products) {
                outputArea.append(product + "\n");
            }
        }
    }

    private void deleteManager() {
        String phone = JOptionPane.showInputDialog(frame, "Enter Manager's Phone Number to Delete:");

        if (phone != null && !phone.trim().isEmpty()) {
            Manager managerToRemove = null;
            for (Manager manager : managers) {
                if (manager.phone.equals(phone)) {
                    managerToRemove = manager;
                    break;
                }
            }

            if (managerToRemove != null) {
                managers.remove(managerToRemove);
                outputArea.append("Manager with phone number " + phone + " deleted.\n");
                saveData();
            } else {
                JOptionPane.showMessageDialog(frame, "Manager with phone number not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteClient() {
        String phone = JOptionPane.showInputDialog(frame, "Enter Client's Phone Number to Delete:");

        if (phone != null && !phone.trim().isEmpty()) {
            Client clientToRemove = null;
            for (Client client : clients) {
                if (client.phone.equals(phone)) {
                    clientToRemove = client;
                    break;
                }
            }

            if (clientToRemove != null) {
                clients.remove(clientToRemove);
                outputArea.append("Client with phone number " + phone + " deleted.\n");
                saveData();
            } else {
                JOptionPane.showMessageDialog(frame, "Client with phone number not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteContract() {
        String contractIdStr = JOptionPane.showInputDialog(frame, "Enter Contract ID to Delete:");

        if (contractIdStr != null && !contractIdStr.trim().isEmpty()) {
            try {
                int contractId = Integer.parseInt(contractIdStr);
                Contract contractToRemove = null;
                for (Contract contract : contracts) {
                    if (contract.contractId == contractId) {
                        contractToRemove = contract;
                        break;
                    }
                }

                if (contractToRemove != null) {
                    contracts.remove(contractToRemove);
                    outputArea.append("Contract with ID " + contractId + " deleted.\n");
                    saveData();
                } else {
                    JOptionPane.showMessageDialog(frame, "Contract with ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Contract ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void longestContract() {
        if (contracts.isEmpty()) {
            outputArea.setText("No contracts available.\n");
            return;
        }

        Contract longest = contracts.get(0);
        long maxLength = longest.endDate.getTime() - longest.startDate.getTime();

        for (Contract contract : contracts) {
            long length = contract.endDate.getTime() - contract.startDate.getTime();
            if (length > maxLength) {
                longest = contract;
                maxLength = length;
            }
        }

        long days = maxLength / (1000 * 60 * 60 * 24); // Convert milliseconds to days
        outputArea.setText("Longest Contract (ID " + longest.contractId + "): " + days + " days\n");
    }

    private void highestPrice() {
        if (contracts.isEmpty()) {
            outputArea.setText("No contracts available.\n");
            return;
        }

        Contract highest = contracts.get(0);
        for (Contract contract : contracts) {
            if (contract.price > highest.price) {
                highest = contract;
            }
        }

        outputArea.setText("Highest Price Contract (ID " + highest.contractId + "): " + highest.price + " units\n");
    }

    private void avgLength() {
        if (contracts.isEmpty()) {
            outputArea.setText("No contracts available.\n");
            return;
        }

        long totalDays = 0;
        for (Contract contract : contracts) {
            long length = contract.endDate.getTime() - contract.startDate.getTime();
            totalDays += length;
        }

        long averageDays = totalDays / contracts.size() / (1000 * 60 * 60 * 24);
        outputArea.setText("Average Contract Length: " + averageDays + " days\n");
    }


    private void createManager() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField marketField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Market Name:"));
        inputPanel.add(marketField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Create Manager", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String marketName = marketField.getText();
            managers.add(new Manager(name, phone, marketName));
            outputArea.append("Manager created.\n");
            saveData();
        }
    }

    private void createClient() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField contractIdField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Contract ID:"));
        inputPanel.add(contractIdField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Create Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            int contractId = Integer.parseInt(contractIdField.getText());
            clients.add(new Client(name, phone, contractId));
            outputArea.append("Client created.\n");
            saveData();
        }
    }

    private void createContract() {
        JTextField contractIdField = new JTextField();
        JTextField marketNameField = new JTextField();
        JTextField phoneManagerField = new JTextField();
        JTextField phoneClientField = new JTextField();
        JTextField shopIdField = new JTextField();
        JTextField productField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(9, 2));
        inputPanel.add(new JLabel("Contract ID:"));
        inputPanel.add(contractIdField);
        inputPanel.add(new JLabel("Market Name:"));
        inputPanel.add(marketNameField);
        inputPanel.add(new JLabel("Manager Phone:"));
        inputPanel.add(phoneManagerField);
        inputPanel.add(new JLabel("Client Phone:"));
        inputPanel.add(phoneClientField);
        inputPanel.add(new JLabel("Shop ID:"));
        inputPanel.add(shopIdField);
        inputPanel.add(new JLabel("Product:"));
        inputPanel.add(productField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        inputPanel.add(endDateField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Create Contract", JOptionPane.OK_CANCEL_OPTION);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startDateField.getText());
            endDate = sdf.parse(endDateField.getText());
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(frame, "Start date must be before end date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (result == JOptionPane.OK_OPTION) {
            String marketName = marketNameField.getText();

            String phoneManager = phoneManagerField.getText();
            Manager manager = null;

            for (Manager m : managers) {
                if (m.marketName.equals(marketName) && m.phone.equals(phoneManager)) {
                    manager = m;
                    break;
                }
            }
            if (manager == null) {
                JOptionPane.showMessageDialog(frame, "Manager phone number does not match the market name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String phoneClient = phoneClientField.getText();

            Client client = null;
            for (Client c : clients) {
                if (c.phone.equals(phoneClient)) {
                    client = c;
                    break;
                }
            }

            if (client == null) {
                JOptionPane.showMessageDialog(frame, "Client with this phone number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int contractId = Integer.parseInt(contractIdField.getText());
                if (contractId != client.getContractId()) {
                    JOptionPane.showMessageDialog(frame, "Contract ID does not belong to the provided client phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Contract ID must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int contractId = Integer.parseInt(contractIdField.getText());
                int shopId = Integer.parseInt(shopIdField.getText());
                String product = productField.getText();
                int price = Integer.parseInt(priceField.getText());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate1 = sdf.parse(startDateField.getText());
                Date endDate1 = sdf.parse(endDateField.getText());

                contracts.add(new Contract(contractId, marketName, phoneManager, phoneClient, shopId, product, price, startDate, endDate));
                outputArea.append("Contract created.\n");
                saveData();

            } catch (NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Error in input data. Please check contract ID, shop ID, price, or dates.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayAllContracts() {
        outputArea.setText("");
        if (contracts.isEmpty()) {
            outputArea.append("No contracts available.\n");
        } else {
            for (Contract contract : contracts) {
                outputArea.append(contract.toString() + "\n\n");
            }
        }
    }

    private void displayAllManagers() {
        outputArea.setText("");
        if (managers.isEmpty()) {
            outputArea.append("No managers available.\n");
        } else {
            for (Manager manager : managers) {
                outputArea.append(manager.getContactInfo() + "\n\n");
            }
        }
    }

    private void displayAllClients() {
        outputArea.setText("");
        if (clients.isEmpty()) {
            outputArea.append("No clients available.\n");
        } else {
            for (Client client : clients) {
                outputArea.append(client.getContactInfo() + "\n\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}