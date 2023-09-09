/*
This is the improved self checkout system making better use of OOP principles.
The design focused on improving security using encapsulation. 
*/


import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelfCheckoutSystem extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<Item> items = new ArrayList<>();
    private Map<Item, Integer> basket = new HashMap<>();
    private float total = 0;
    private int count = 0;
    private int discount = 0;
    private int uses = 0;

    private JComboBox<String> productList;
    private JTextArea screen;
    private JTextField payScreen;
    private JLabel totalLabel;
    private JLabel transactionLabel;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton checkoutBtn;
    private JButton discountBtn;

    public SelfCheckoutSystem() {
        initComponents();
        initialiseItems();
    }

    private void initComponents() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        setTitle("Self Checkout System");

        productList = new JComboBox<>();
        screen = new JTextArea(10, 40);
        payScreen = new JTextField(5);
        totalLabel = new JLabel("Total: £0.00 ");
        transactionLabel = new JLabel();

        addBtn = new JButton("Add item");
        removeBtn = new JButton("Remove item");
        checkoutBtn = new JButton("Checkout");
        discountBtn = new JButton("Add discount");

        productList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                productListActionPerformed(evt);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        checkoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                checkoutBtnActionPerformed(evt);
            }
        });

        discountBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                discountBtnActionPerformed(evt);
            }
        });

        // Add components to the JFrame
        JPanel topPanel = new JPanel();
        topPanel.add(productList);
        topPanel.add(transactionLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JScrollPane(screen));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addBtn);
        bottomPanel.add(removeBtn);
        bottomPanel.add(discountBtn);
        bottomPanel.add(totalLabel);
        bottomPanel.add(payScreen);
        bottomPanel.add(checkoutBtn);

        add(topPanel, java.awt.BorderLayout.NORTH);
        add(centerPanel, java.awt.BorderLayout.CENTER);
        add(bottomPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }

    private void initialiseItems() {
    	// Name, Code, Price, Quantity
        items.add(new Item("Pen", 1, 1.00f, 20));
        items.add(new Item("Book", 2, 1.50f, 20));
        items.add(new Item("Water", 3, 1.25f, 20));
        items.add(new Item("Pop", 4, 2.00f, 20));
        items.add(new Item("Snack", 5, 2.00f, 20));
        items.add(new Item("Sweets", 6, 1.00f, 20));
        items.add(new Item("Chocolate", 7, 1.50f, 20));
        items.add(new Item("Biscuits", 8, 1.00f, 20));
        items.add(new Item("Cake", 9, 1.25f, 20));
        
        for (Item item : items) {
            productList.addItem(item.getName());
        }
    }

    private Item findItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void addBtnActionPerformed(ActionEvent evt) {
    	
        String selectedProduct = productList.getSelectedItem().toString();
        Item selectedItem = findItemByName(selectedProduct);
        
        if (selectedItem != null) {
        	int availableQty = selectedItem.getQty();
        	int basketQty = basket.getOrDefault(selectedItem, 0);
        	
        	// Is the item in stock?
        	if (basketQty < availableQty) {
            basket.put(selectedItem, basket.getOrDefault(selectedItem, 0) + 1);
            total += selectedItem.getPrice();
            screen.append("\n" + selectedItem.getName() + " - £" + selectedItem.getPrice());
            totalLabel.setText("Total: £" + total);
        	} 
        	else {
        		// Item out of stock
        		transactionLabel.setText("Item out of stock.");
        	}
        }
    }

    private void removeBtnActionPerformed(ActionEvent evt) {
    	
        String selectedProduct = productList.getSelectedItem().toString();
        Item selectedItem = findItemByName(selectedProduct);
        if (selectedItem != null && basket.containsKey(selectedItem)) {
            int currentQuantity = basket.get(selectedItem);
            if (currentQuantity > 0) {
                basket.put(selectedItem, currentQuantity - 1);
                total -= selectedItem.getPrice();
                screen.setText(screen.getText().replace("\n" + selectedItem.getName() + " - £" + selectedItem.getPrice(), ""));
                totalLabel.setText("Total: £" + total);
            }
        }
    }

    private void checkoutBtnActionPerformed(ActionEvent evt) {
        count++; // Increment the count for checkout attempts
        
        if (count == 1) {
            // Proceed to checkout
            
            if (basket.isEmpty()) {
                transactionLabel.setText("Basket is empty. Add items first.");
            } 
            else {
                // Calculate the total amount to pay
                float paymentTotal = calculateTotal();

                // Display the total amount to pay and prompt the user for payment
                totalLabel.setText("Total: £" + paymentTotal);
                payScreen.setText("");
                transactionLabel.setText("Enter payment amount");
            }
        } else if (count == 2) {
            
        	// Processing payment
            try {
                // Get the user's input payment amount
                float paymentAmount = Float.parseFloat(payScreen.getText());

                // Calculate the change due to the user
                float change = paymentAmount - total;

                if (change >= 0) {
                    // Successful payment
                    transactionLabel.setText("Payment successful. Change: £" + change);

                    // Generate and print a receipt
                    generateReceipt();

                    // Clear the basket, total, and UI elements
                    basket.clear();
                    total = 0;
                    totalLabel.setText("Total: £0.00 ");
                    screen.setText("");
                    payScreen.setText("");
                } else {
                    // Insufficient payment
                    transactionLabel.setText("Insufficient payment. Please enter a valid amount.");
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (non-numeric)
                transactionLabel.setText("Invalid input. Enter a numeric payment amount.");
            }
        } else {
            // Reset the checkout process
            count = 0;
            transactionLabel.setText("");
        }
    }

    private float calculateTotal() {
        float paymentTotal = 0;

        // Calculate the total based on items in the basket
        for (Map.Entry<Item, Integer> entry : basket.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            paymentTotal += item.getPrice() * quantity;
        }

        return paymentTotal;
    }

    private void generateReceipt() {
        // Not implemented yet
    }

    private void discountBtnActionPerformed(ActionEvent evt) {
        if (count >= 1) {
            // Prompt user to pay
            transactionLabel.setText("Enter amount to pay");

            // Counts the number of times the user asks for a discount
            uses++;

            // Apply discount only once before final checkout 
            if (uses == 1) {
                int max = 10;
                int min = 1;
                discount = (int) (Math.random() * (max - min + 1)) + min; // Generates random number from 1 - 10

                if (discount >= 6) {
                    if (total < 5) {
                        // If total < £5 discount, total = 0
                        total = 0;
                    } else {
                        total -= 5; // Otherwise subtract the £5 discount
                    }
                } else {
                    // Apply percentage discount based on the random value
                    float discountPercentage = discount * 10;
                    float discountAmount = (total * discountPercentage) / 100;
                    total -= discountAmount;
                }
            }
            // Update the total label to reflect the discount
            totalLabel.setText("Total: £" + total);
        } else {
            transactionLabel.setText("Head to checkout");
        }
    }

    private void productListActionPerformed(ActionEvent evt) {
    	
        String selectedProduct = productList.getSelectedItem().toString();
        Item selectedItem = findItemByName(selectedProduct);

        if (selectedItem != null) {
            transactionLabel.setText("Price: £" + selectedItem.getPrice());

        } else {
            // In case items haven't been initialised properly
            transactionLabel.setText("Product not found");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelfCheckoutSystem().setVisible(true);
            }
        });
    }

    class Item {
    	
        private String name;
        private int code;
        private float price;
        private int quantity;

        public Item(String name, int code, float price, int quantity) {
            this.name = name;
            this.code = code;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }

        public float getPrice() {
            return price;
        }

        public int getQty() {
            return quantity;
        }
    }
}