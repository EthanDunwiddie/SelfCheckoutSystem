/**
 * @author ethan
 * 
 * Prototype self checkout system.

Requirements:
Products can be purchased by entering the code of an item and payment by entering an amount at least equal to the cost of the selected products.
Should allow a customer to select a list of products in stock.
Before the payment, the system offers discount raffle draw of numbers; a customer rolls a number randomly  (between 1 – 10); 1 = 10% discount, 2 = 20%, 3 = 30%, 4 = 40%, and 5 = 50%. the remaining numbers between 6 and 10 correspond to a discount of £5.
After entering payment, if it is greater than or equal to the total price, the machine approves of the transaction by providing a receipt of the products and display change if any.
Otherwise, the system rejects the transaction if either the entered amount is insufficient or if any of the selected products are sold out.

0. Drop down list of items
1. Text area for displaying selected products
2. Button for generating discount
3. A text-field for entering an amount for payment
4. A label for displaying one of the following transactions:
	- “Please take your items” (successful transaction)
	- “Sold out” (for unavailable products)
	- “Insufficient amount” (When entered amount is less than the cost of selected items)
5. After successful transaction, the system prints a file named receipt.txt or reciept.pdf
	- list of purchased items
	- total cost
	- discount
	- entered amount and change (if possible)
6. After a successful transaction, the program reduces the quantity of purchased items from stock.
7. Test cases for the following scenarios:
	a. A customer gets products with an amount of money that is greater than or equal to the 	   price of the selected products
	b. A customer should not get products with an amount of money that is less than the price of 	   selected products.


---------------------------------------------------------------------------------------------------------------------

Algorithm

Start

0. List of items
	- Item code
	- Product
	- Price(£)
	- Quantity
	- User picks an item from the list

1. Text area for displaying selected products
	- User inputs item code
	- Display item code
	- Display product
	- Display Price(£)
	- Display Quantity

2. Button to generate discount

	- Generate a random number from 1-10
	- Numbers 1-5 = number*10 (e.g. 3 = 30% discount)
	- Numbers 6-10 = £5 discount

3. Text-field for entering an amount for payment
	- User enters amount to be paid after selecting all their items

2. Button to generate discount
	- Generate a random number from 1-10
	- Numbers 1-5 = number*10 for discount e.g. 3 = 30% discount)
	- Numbers 6-10 = £5 discount

4. Label displaying one of the following transactions
	- “Please take your items” (successful transaction)
	- “Sold out” (for unavailable products)
	- “Insufficient amount” (When entered amount is less than the cost of selected items)

5. After successful transaction
	- The system prints a receipt:
		- Held as a file named receipt.txt or reciept.pdf
		- Lists purchased items
		- Displays the total cost
		- Shows the discount and final cost - discount
		- Displays entered amount and change (if possible)
	- The program reduces the quantity of purchased items from stock
	- The  goes back to its default state	

6. After an unsuccessful transactions
	- The system rejects the transaction
	- Tells the user either insufficiency funds were added or items were out of stock

7. Test cases for the following scenarios:
	a. A customer gets products with an amount of money that is greater than or equal to the 	   price of the selected products
	b. A customer should not get products with an amount of money that is less than the price of 	   selected products.


 * 
 * 
 */

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class selfCheckoutSystem extends javax.swing.JFrame {
    
    	//Arrays used to hold the menu items name, code, price, and quantity
	static ArrayList<String>  itemName  = new ArrayList<String>();//Name
	static ArrayList<Integer> itemCode  = new ArrayList<Integer>();//Code
	static ArrayList<Float>   itemPrice = new ArrayList<Float>();//Price
	static ArrayList<Integer> itemQty   = new ArrayList<Integer>();//Quantity
        
            	//Arrays used to hold the menu items name, code, price, and quantity
	static ArrayList<String>  basketName  = new ArrayList<String>();//Name
	static ArrayList<Integer> basketCode  = new ArrayList<Integer>();//Code
	static ArrayList<Float>   basketPrice = new ArrayList<Float>();//Price
	static ArrayList<Integer> basketQty   = new ArrayList<Integer>();//Quantity
        

    int uses, max = 10, min = 1, discount, count1, 
        count2, multiplier, tempQty, tempCode, input;
    String product;
    float total;
    
    /**
     * Creates new form selfCheckoutSystem
     */
    public selfCheckoutSystem() {
        initComponents();
        
        // Adds items to combo box and sets other values such as code, price, and quantity for the items
        itemName.add("Pen");
        itemCode.add(1);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
	
	itemName.add("Book");
	itemCode.add(2);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
	
	itemName.add("Water");
	itemCode.add(3);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
	
	itemName.add("Drink");
	itemCode.add(4);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
	
	itemName.add("Snack");
	itemCode.add(5);
	itemPrice.add((float) 2.00);
	itemQty.add(20);
		
	itemName.add("Sweet");
	itemCode.add(6);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
		
	itemName.add("Chocolate");
	itemCode.add(7);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
		
	itemName.add("Biscuits");
	itemCode.add(8);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
		
	itemName.add("Cake");
	itemCode.add(9);
	itemPrice.add((float) 1.00);
	itemQty.add(20);
        
        // Writes products to the drop down menu
        for( int i=0; i< itemName.size(); i++ ) {
            productList.addItem(itemName.get(i));
        }
     
        // Sets transaction label to blank temporarily   
        transactionLabel.setText("");
        
        // Sets pay screen 
        payScreen.setText("Enter payment here");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        discountBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        productList = new javax.swing.JComboBox<>();
        totalLabel = new javax.swing.JLabel();
        checkoutBtn = new javax.swing.JButton();
        transactionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        screen = new javax.swing.JTextArea();
        payScreen = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        discountBtn.setText("Add discount");
        discountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountBtnActionPerformed(evt);
            }
        });

        addBtn.setText("Add item");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setText("Remove item");
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        productList.setToolTipText("Choose items");
        productList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productListActionPerformed(evt);
            }
        });

        totalLabel.setText("Total: ");

        checkoutBtn.setText("Checkout");
        checkoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutBtnActionPerformed(evt);
            }
        });

        transactionLabel.setText("jLabel1");

        screen.setColumns(20);
        screen.setRows(5);
        screen.setText("Code          Product          Price (£)          Quantity\n   01              Pen                        1                            20\n   02              Book                     1                            20\n   03              Water                   1                            20\n   04              Drink                     2                            20\n   05              Snack                    1                           20\n   06              Sweet                   1                           20\n   07              Chocolate          1                           20\n   08              Biscuits                1                           20\n   09              Cake                      1                           20\n");
        jScrollPane1.setViewportView(screen);

        payScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payScreenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(totalLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(payScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(addBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkoutBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(discountBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(productList, 0, 146, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(transactionLabel)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transactionLabel)
                        .addGap(56, 56, 56)
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(discountBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalLabel)
                    .addComponent(checkoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(payScreen, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void discountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountBtnActionPerformed
        
        // Checks if user is heading to checkout - if so they may add discount
        if(count2 >= 1){
            // Tells user to pay to continue
            transactionLabel.setText("Enter amount to pay");
            
            // Counts number of times user asks for discount
            uses = uses + 1;
        
            // Adds discount to the total before final payment - discount may only be added once
            if(uses == 1){
                discount = (int)(Math.random() * ((max - min) + 1)) + min;// Generates random number from 1 - 10
                
                if(discount >= 6){
                    if(total < 5){ // If total < £5 discount, total = 0
                        total = 0;
                    }
                    else{
                        total = total - 5; // Otherwise subtract the £5 discount               
                    }
                }
                else{ // If random number is 1 - 5 then generate discount accordingly
                    if (discount == 1){
                        total = total - (total/10); // 10% discount
                    }
                    else if(discount == 2){
                        total = total - ((total/10)*2); // 20% discount
                    }
                    else if(discount == 3){
                        total = total - ((total/10)*3); // 30% discount
                    }
                    else if(discount == 4){
                        total = total - ((total/10)*4); // 40% discount
                    }
                    else if(discount == 5){
                        total = total - ((total/10)*5); // 50% discount
                    }
                }// Couldnt get switch case to work with this but it would have been better
            }
        }
        // If user has not headed to checkout then they cant add discount
        else{
            transactionLabel.setText("Head to checkout");
        }

    }//GEN-LAST:event_discountBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed

        // Resets the screen when the user adds an item (shows basket)
        count1 = count1 + 1;
        if(count1 == 1){
            screen.setText("Your basket: \n");
        }
        
        // Every item added we will increase the quantity by 1
        tempQty = tempQty + 1;
        
        // Append item to screen
        if (tempQty <= 1){
            screen.append("\n" + tempQty + "x " + product);
        }
        // If multiple products are added then increase the multiplier
        else if (tempQty > 1){
            screen.setText(screen.getText().replace("\n" + (tempQty - 1) + "x " + product,"\n" + tempQty + "x " + product));
        }
        
        
        // Adds items to basket
        basketCode.add(tempCode + 1);
        basketName.add(itemName.get(tempCode));
        basketQty.add(tempQty);
        basketPrice.add(tempQty * itemPrice.get(tempCode));
        

        
        for(int i=0; i<basketName.size(); i++){
            for(int j=i+1; j<basketName.size(); j++){
                if(j!=i && basketName.get(j).equals(basketName.get(i))){
                    basketCode.remove(basketCode.get(i));
                    basketName.remove(basketName.get(i));
                    basketQty.remove(basketQty.get(i));
                    basketPrice.remove(basketPrice.get(i));
                }
            }
        }
        
        //screen.append("\nCODE add: " + basketCode);
        //screen.append("\nNAME add: " + basketName);
        //screen.append("\nQTY add: " + basketQty);
        //screen.append("\nPRICE add: " + basketPrice);
    }//GEN-LAST:event_addBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        
        // Removes 1 from quantity
        tempQty = tempQty - 1;
        
        // Remove 1 from the quantity        
        if (tempQty >= 1){
            screen.setText(screen.getText().replace("\n" + (tempQty + 1) + "x " + product,"\n" + tempQty + "x " + product));
        }
        // If 1 item is left on screen then remove it
        else if (tempQty < 1){
            screen.setText(screen.getText().replace("\n" + (tempQty + 1) + "x " + product, ""));
            tempQty = 0; // Makes sure user cant add negative items
        }
       
        // Removes items from basket
        for(int i=0; i<basketName.size(); i++){
            for(int j=i+1; j<basketName.size(); j++){
                if(j!=i && basketName.get(j).equals(basketName.get(i))){
                    basketCode.remove(basketCode.get(i));
                    basketName.remove(basketName.get(i));
                    basketQty.remove(basketQty.get(i));
                    basketPrice.remove(basketPrice.get(i));
                }
            }
        }
    }//GEN-LAST:event_removeBtnActionPerformed

    private void productListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productListActionPerformed
        
        // Reset tempQty
        tempQty = 0;
        
        // Get selectd item
        product = productList.getSelectedItem().toString();
        
        // Get item code
        tempCode = itemName.indexOf(product); 
        
    }//GEN-LAST:event_productListActionPerformed

    private void checkoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutBtnActionPerformed

        count2 = count2 + 1;
        
        if (count2 <= 1){
            // Find the total amount ot pay
            for(int i = 0; i < basketPrice.size(); i++){
                total = total + basketPrice.get(i);
            }
            // Set pay screen to blacnk for user input
            payScreen.setText("");
            
            // Display total to the user
            totalLabel.setText("Total: £" + total);
        }
        else if (count2 == 2){
            // Takes user input as string and converts it to float
            input = Integer.parseInt(payScreen.getText());
            
            // Final payment 
            if(input >= total){
                transactionLabel.setText("Please take your items");
            }
            else if(input < total){
                transactionLabel.setText("Insufficient amount");
            }
            //else if(basketQty.get(tempCode) > itemQty.get(tempCode)){
            //    transactionLabel.setText("Sold out");
            //}
            checkoutBtn.setText("Continue");
            screen.setText("Please take your receipt");
        }
        else{
            // Resets screen
            
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("receipt.txt", "UTF-8");
                writer.println("");
                writer.close();
                
                // Writes receipt to file
                
                for(int i=0; i<basketName.size(); i++){
                writer.append("\n");
                writer.append(basketCode.get(i).toString() + "  ");
                writer.append(basketName.get(i) + "  ");
                writer.append(basketPrice.get(i).toString() + "  ");
                writer.append("x" + basketQty.get(i).toString());
                }
                writer.append("------------------------------\n------------------------------");
                writer.append("\nTotal: " + total);
                writer.append("\nDiscount: " + discount);
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(selfCheckoutSystem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(selfCheckoutSystem.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
            
            // Removes items from basket
            for(int i=0; i<basketName.size(); i++){
                for(int j=i+1; j<basketName.size(); j++){
                    if(j!=i && basketName.get(j).equals(basketName.get(i))){
                        basketCode.set(i, null);
                        basketName.set(i, null);
                        basketQty.set(i, null);
                        basketPrice.set(i, null);
                    }
                }
            }
            // Resets check button and screen
            checkoutBtn.setText("Checkout");    
            screen.setText("");    
            totalLabel.setText("Total: ");
            payScreen.setText("Enter payment here");
            transactionLabel.setText("");
        }
        
    }//GEN-LAST:event_checkoutBtnActionPerformed

    private void payScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payScreenActionPerformed

    }//GEN-LAST:event_payScreenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(selfCheckoutSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(selfCheckoutSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(selfCheckoutSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(selfCheckoutSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new selfCheckoutSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton checkoutBtn;
    private javax.swing.JButton discountBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField payScreen;
    private javax.swing.JComboBox<String> productList;
    private javax.swing.JButton removeBtn;
    private javax.swing.JTextArea screen;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel transactionLabel;
    // End of variables declaration//GEN-END:variables
}
