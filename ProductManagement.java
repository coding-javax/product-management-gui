import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ProductManagement {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Management Application");

        JLabel label = new JLabel("Java X: Product Management");
        label.setBounds(70, 10, 300, 30);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.BLUE);

        JLabel productID = new JLabel("Product ID:");
        productID.setBounds(30, 60, 100, 30);
        productID.setFont(new Font("Serif", Font.PLAIN, 16));
        JTextField productIDField = new JTextField();
        productIDField.setBounds(140, 60, 210, 30);

        JLabel productName = new JLabel("Product Name:");
        productName.setBounds(30, 100, 100, 30);
        productName.setFont(new Font("Serif", Font.PLAIN, 16));
        JTextField productNameField = new JTextField();
        productNameField.setBounds(140, 100, 210, 30);

        JLabel productPrice = new JLabel("Product Price:");
        productPrice.setBounds(30, 140, 100, 30);
        productPrice.setFont(new Font("Serif", Font.PLAIN, 16));
        JTextField productPriceField = new JTextField();
        productPriceField.setBounds(140, 140, 210, 30);

        JLabel productQty = new JLabel("Quantity:");
        productQty.setBounds(30, 180, 100, 30);
        productQty.setFont(new Font("Serif", Font.PLAIN, 16));
        JTextField productQtyField = new JTextField();
        productQtyField.setBounds(140, 180, 210, 30);

        JButton addButton = new JButton("Add");
        addButton.setBounds(90, 230, 100, 30);
        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(210, 230, 100, 30);
        updateButton.setBackground(Color.BLUE);
        updateButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(90, 270, 100, 30);
        deleteButton.setBackground(Color.BLUE);
        deleteButton.setForeground(Color.WHITE);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(210, 270, 100, 30);
        searchButton.setBackground(Color.BLUE);
        searchButton.setForeground(Color.WHITE);

        JLabel copyright = new JLabel("Copyright @Java X");
        copyright.setBounds(130, 310, 300, 30);
        copyright.setFont(new Font("Serif", Font.ITALIC, 16));
        copyright.setForeground(Color.BLUE);

        frame.setLayout(null);
        frame.add(label);
        frame.add(productID);
        frame.add(productIDField);
        frame.add(productName);
        frame.add(productNameField);
        frame.add(productPrice);
        frame.add(productPriceField);
        frame.add(productQty);
        frame.add(productQtyField);
        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(searchButton);
        frame.add(copyright);

        HandleEvent handleEvent = new HandleEvent(productIDField, productNameField, productPriceField, productQtyField,
                addButton, updateButton, deleteButton, searchButton);
        addButton.addActionListener(handleEvent);
        updateButton.addActionListener(handleEvent);
        deleteButton.addActionListener(handleEvent);
        searchButton.addActionListener(handleEvent);

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class HandleEvent implements ActionListener {

    JTextField id, name, price, quantity;
    JButton add, update, delete, search;
    public static Connection con;
    public static PreparedStatement statement;

    public HandleEvent(JTextField id, JTextField name, JTextField price, JTextField quantity, JButton add,
            JButton update, JButton delete, JButton search) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.add = add;
        this.update = update;
        this.delete = delete;
        this.search = search;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            connect();
            String productID = id.getText();
            String productName = name.getText();
            String productPrice = price.getText();
            String productQty = quantity.getText();

            try {
                statement = con.prepareStatement(
                        "insert into products(productID, productName, productPrice, productQty)values(?,?,?, ?)");
                int proID = Integer.parseInt(productID);
                statement.setInt(1, proID);
                statement.setString(2, productName);
                int proPrice = Integer.parseInt(productPrice);
                statement.setInt(3, proPrice);
                int proQuantity = Integer.parseInt(productQty);
                statement.setInt(4, proQuantity);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Successfully Added", "Record Added",
                        JOptionPane.INFORMATION_MESSAGE);

                id.setText(" ");
                name.setText(" ");
                price.setText(" ");
                quantity.setText(" ");

                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == update) {
            connect();
            String productID = id.getText();
            String productName = name.getText();
            String productPrice = price.getText();
            String productQty = quantity.getText();

            try {
                statement = con.prepareStatement(
                        "update products set productID = ?, productName = ?, productPrice = ?, productQty = ? where productID = ?");
                int proID = Integer.parseInt(productID);
                statement.setInt(1, proID);
                statement.setString(2, productName);
                int proPrice = Integer.parseInt(productPrice);
                statement.setInt(3, proPrice);
                int proQuantity = Integer.parseInt(productQty);
                statement.setInt(4, proQuantity);
                statement.setInt(5, proID);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Successfully Updated", "Record Updated",
                        JOptionPane.INFORMATION_MESSAGE);

                id.setText(" ");
                name.setText(" ");
                price.setText(" ");
                quantity.setText(" ");

                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == delete) {
            connect();
            String productID = id.getText();

            try {
                statement = con.prepareStatement("delete from products where productID = ?");
                int proID = Integer.parseInt(productID);
                statement.setInt(1, proID);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Successfully Deleted", "Record Deleted",
                        JOptionPane.INFORMATION_MESSAGE);

                id.setText(" ");
                name.setText(" ");
                price.setText(" ");
                quantity.setText(" ");

                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == search) {
            connect();
            String productID = id.getText();

            try {
                statement = con.prepareStatement(
                        "select productName, productPrice, productQty from products where productID = ?");
                int proID = Integer.parseInt(productID);
                statement.setInt(1, proID);
                ResultSet rs = statement.executeQuery();

                if (rs.next() == true) {
                    String proName = rs.getString(1);
                    String proPrice = rs.getString(2);
                    String proQty = rs.getString(3);

                    name.setText(proName);
                    price.setText(proPrice);
                    quantity.setText(proQty);
                } else {
                    name.setText(" ");
                    price.setText(" ");
                    quantity.setText(" ");
                    JOptionPane.showMessageDialog(null, "Invalid Product ID", "Error", JOptionPane.ERROR_MESSAGE);
                }

                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void connect() {
        String URL = "jdbc:mysql://localhost:3306/ProductData";
        String username = "root";
        String password = "YOUR_PASSWORD";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, username, password);
            System.out.println("Connected to Database...");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}