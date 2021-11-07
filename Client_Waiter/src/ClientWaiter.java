//customizable-restaurant-operating-system-with-Java-MySQL v1.0.0
//by lrex93497 @github
//Client_Waiter
//release under GPLv2

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.logging.*;

import java.sql.*;
import java.text.SimpleDateFormat;

class ClientWaiter implements ActionListener{

    DefaultTableModel tableModelFood;
    DefaultTableModel tableModelOrder;
    JMenuItem itemfilMenuDatabaseChooseTable;
    JMenuItem itemfilMenuDatabaseSummizeUsed;
    JMenuItem itemfilMenuCheckCurrentIngredients;
    JMenuItem itemFilMenuOthersExit;
    JMenuItem filMenuRefreshRefresh;
    JTable foodTable;
    JTable orderTable;
    String timeStamp = "";
    String conInf; 
    String account;
    String password;
    Connection con;
    int totalTableNumber = 0;
    JLabel orderAmountLabel;
    JButton minusAmountButton;
    JButton plusAmounButton;
    JComboBox customerTableNoComboBox;
    JComboBox foodtypeComboBox;
    String[] customerTableNo;
    JPanel customerTableNoPanel;
    Font DefaultFontSetting = new Font("Arial", Font.BOLD, 22);
    GridBagConstraints gbc = new GridBagConstraints();
    Boolean started = false;
    int ispaid = 0;
    int nextid;
    String[] ingredientsNameArray;
    Double[] ingredientAmountArray;
    Double[] ingredientNeededAmountOneArray;
    Double orderAmount;
    int ingredientnumber;
    int id_foodSelectedInt;
    int id_orderInt;
    int id_customerInt;
    Double priceDouble;
    JLabel currentMoneyAmountLabel;
    int cancelCheckIsMaking;
    

    ClientWaiter(String tempConInf, String tempAccount, String tempPassword){
        DefaultFontSetting = new Font("Arial", Font.BOLD, 22);

        conInf = tempConInf;
        account = tempAccount;
        password = tempPassword;

        //check how many table present first
        getTableNumber();

        
        JFrame waiterFrame = new JFrame(); 
        waiterFrame.setSize(700,700);
        waiterFrame.setMinimumSize(new Dimension(1000,700));
        waiterFrame.setLayout(new GridBagLayout());  
        waiterFrame.setTitle("Waiter Client");
        waiterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        waiterFrame.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        waiterFrame.setJMenuBar(menuBar);

        JMenu filMenuRefresh = new JMenu("Refresh");
        menuBar.add(filMenuRefresh);

        filMenuRefreshRefresh = new JMenuItem("Refresh table now");
        filMenuRefreshRefresh.addActionListener(this);
        filMenuRefreshRefresh.setActionCommand("refresh");
        filMenuRefresh.add(filMenuRefreshRefresh);

        filMenuRefreshRefresh = new JMenuItem("Refresh customer table Combobox");
        filMenuRefreshRefresh.addActionListener(this);
        filMenuRefreshRefresh.setActionCommand("refreshCustomerTableNumber");
        filMenuRefresh.add(filMenuRefreshRefresh);

        JMenu filMenuOthers = new JMenu("Others");
        menuBar.add(filMenuOthers);

        itemFilMenuOthersExit = new JMenuItem("Exit");
        itemFilMenuOthersExit.addActionListener(this);
        itemFilMenuOthersExit.setActionCommand("exit");
        filMenuOthers.add(itemFilMenuOthersExit);


        JPanel timePanel = new JPanel();
        //timePanel.setBackground(Color.gray);
        timePanel.setLayout(new GridLayout(1,1));
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 3;
        gbc.fill = gbc.BOTH;
        waiterFrame.add(timePanel, gbc);

        JPanel foodTypePanel = new JPanel();
        foodTypePanel.setLayout(new GridBagLayout());
        //foodTypePanel.setBackground(Color.orange);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 5.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        waiterFrame.add(foodTypePanel, gbc);

        customerTableNoPanel = new JPanel();
        customerTableNoPanel.setLayout(new GridBagLayout());
        //customerTableNoPanel.setBackground(Color.yellow);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 5.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        waiterFrame.add(customerTableNoPanel, gbc);

        JPanel tableFoodPanel = new JPanel();
        tableFoodPanel.setLayout(new GridBagLayout());
        //tableFoodPanel.setBackground(Color.black);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 5.0;
        gbc.weighty = 2.0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = gbc.BOTH;
        waiterFrame.add(tableFoodPanel, gbc);

        JPanel tableOrderPanel = new JPanel();
        tableOrderPanel.setLayout(new GridBagLayout());
        //tableOrderPanel.setBackground(Color.green);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 5.0;
        gbc.weighty = 2.0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = gbc.BOTH;
        waiterFrame.add(tableOrderPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1,1,35));
        //buttonPanel.setBackground(Color.red);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 2.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        waiterFrame.add(buttonPanel, gbc);

        JPanel currentMoneyPanel = new JPanel();
        currentMoneyPanel.setLayout(new GridBagLayout());
        //currentMoneyPanel.setBackground(Color.red);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 5.0;
        gbc.weighty = 0.1;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        waiterFrame.add(currentMoneyPanel, gbc);


        JLabel foodtypeLabel = new JLabel("Food type: ");
        foodtypeLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        foodTypePanel.add(foodtypeLabel, gbc);

        String[] foodtype = {"all", "appetizer", "main_course", "dessert", "beverage", "others", "special"};
        foodtypeComboBox = new JComboBox<>(foodtype);
        foodtypeComboBox.setFont(DefaultFontSetting);
        foodtypeComboBox.addActionListener(this);
        foodtypeComboBox.setActionCommand("reselectFoodType");
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        foodTypePanel.add(foodtypeComboBox, gbc);

        JLabel customerTableNoLabel = new JLabel("Table Number: ");
        customerTableNoLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        customerTableNoPanel.add(customerTableNoLabel, gbc);
        
        customerTableNoComboBox = new JComboBox<>(customerTableNo);
        customerTableNoComboBox.addActionListener(this);
        customerTableNoComboBox.setActionCommand("reselectTable");
        customerTableNoComboBox.setFont(DefaultFontSetting);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        customerTableNoPanel.add(customerTableNoComboBox, gbc);


        String timeStamp = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss   EEE").format(Calendar.getInstance().getTime());
        JLabel timeLabel = new JLabel(timeStamp);
        //System.out.println(Calendar.getInstance().getTime());
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    String timeStampInloop = getTime();
                    timeLabel.setText(timeStampInloop);
                    try {
                      Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                      e.printStackTrace();
                    }
                  }
            }

            private String getTime() {
                String time = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss   EEE").format(Calendar.getInstance().getTime());
                return time;
            }
            
        };

        Thread t = new Thread(runnable);
        t.start();
        
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timePanel.add(timeLabel);


        tableModelFood = new DefaultTableModel();
        foodTable = new JTable(tableModelFood);
        foodTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        foodTable.setFont(DefaultFontSetting);
        foodTable.setRowHeight(30);
        JTableHeader dataTableHeader = foodTable.getTableHeader();
        dataTableHeader.setFont(DefaultFontSetting);

        JScrollPane jps = new JScrollPane(foodTable);
        jps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        jps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        tableFoodPanel.add(jps, gbc);


        tableModelOrder = new DefaultTableModel();
        orderTable = new JTable(tableModelOrder);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        orderTable.setFont(DefaultFontSetting);
        orderTable.setRowHeight(30);
        JTableHeader orderTableHeader = orderTable.getTableHeader();
        orderTableHeader.setFont(DefaultFontSetting);

        JScrollPane jpso = new JScrollPane(orderTable);
        jps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        jps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        tableOrderPanel.add(jpso, gbc);


        JLabel currentMoneyLabel = new JLabel("Food only: ");
        currentMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        currentMoneyPanel.add(currentMoneyLabel, gbc);

        currentMoneyAmountLabel = new JLabel("$xxxxxxx.00");
        currentMoneyAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        currentMoneyPanel.add(currentMoneyAmountLabel, gbc);


        JButton addButton = new JButton("Order");
        addButton.setFont(DefaultFontSetting);
        addButton.addActionListener(this);
        addButton.setActionCommand("order");


        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new GridLayout(1,3));

        plusAmounButton = new JButton("+");
        plusAmounButton.addActionListener(this);
        plusAmounButton.setFont(DefaultFontSetting);
        minusAmountButton = new JButton("-");
        minusAmountButton.addActionListener(this);
        minusAmountButton.setFont(DefaultFontSetting);
        orderAmountLabel = new JLabel("0");
        orderAmountLabel.setHorizontalAlignment(JTextField.CENTER);
        orderAmountLabel.setFont(DefaultFontSetting);

        amountPanel.add(plusAmounButton);
        amountPanel.add(orderAmountLabel);
        amountPanel.add(minusAmountButton);

        JButton deselectButton = new JButton("Deselect all");
        deselectButton.setFont(DefaultFontSetting);
        deselectButton.addActionListener(this);
        deselectButton.setActionCommand("deselect");

        JButton delButton = new JButton("Cancel");
        delButton.setFont(DefaultFontSetting);
        delButton.addActionListener(this);
        delButton.setActionCommand("cancel");

        buttonPanel.add(addButton);
        buttonPanel.add(amountPanel);
        buttonPanel.add(deselectButton);
        buttonPanel.add(delButton);
        
        refreshorderTable();
        refreshfoodTable();

        waiterFrame.setVisible(true);
        started = true;
    }

    private void getTableNumber() {
        totalTableNumber = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            int i = 1;

            //if table present, continue loop and +1 totalTableNumber, until cannot get table(error detected). 
            while(true){
                String targetTable = "table_"+String.valueOf(i);

                try{
                ResultSet rs = stmt.executeQuery("select * FROM "+ targetTable +";");
                }
                catch (SQLException ex) {
                    break;
                }
                i +=1;
                totalTableNumber +=1;
            }
            //System.out.println(totalTableNumber);
            //build customerTableNoComboBox according to physical table exist in resturant
            customerTableNo = new String[totalTableNumber];
            
            for(i = 0; i<totalTableNumber; i++){
                customerTableNo[i] = "table_" + String.valueOf(i+1);
            }

            if(started == true){    //as in first time open the customerTableNo already applied once
                customerTableNoComboBox.removeAllItems();
                for(String n : customerTableNo){
                    customerTableNoComboBox.addItem(n);
                }
            }
        
            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s == "exit"){
            System.exit(0);
        }
        if(s == "refresh"){
            refreshorderTable();
            refreshfoodTable();
        }
        if(s == "refreshCustomerTableNumber"){
            getTableNumber();
            customerTableNoComboBox.doLayout();
        }

        //below is order button related
        if(s == "order"){
            //System.out.println(orderAmountLabel.getText());
            if(Integer.valueOf(orderAmountLabel.getText()) == 0){
                JOptionPane.showMessageDialog(null, "Order amount cannot be 0.", "Amount is 0", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            checkIfNeedNewCustomerIDAndApply();// if new id_customer needed, add it
            if(checkIngredient() == false){
                return;
            }
            else{
            addOrderToTables();
            }
        }
        if(s == "+"){
            int originalAmount =Integer.valueOf(orderAmountLabel.getText());
            originalAmount += 1;
            orderAmountLabel.setText(String.valueOf(originalAmount));
        }
        if(s == "-"){
            int originalAmount =Integer.valueOf(orderAmountLabel.getText());
            if(originalAmount == 0){
                originalAmount = 0;
            }
            else{
                originalAmount -= 1;
            }
            orderAmountLabel.setText(String.valueOf(originalAmount));
        }

        //below is order button/jmenuitem related
        if(s == "cancel"){
            cancelOrder();
        }
        if(s == "deselect"){
            foodTable.getSelectionModel().clearSelection();
            orderTable.getSelectionModel().clearSelection();
        }
        if(s == "reselectTable"){
            refreshorderTable();
        }
        if(s == "reselectFoodType"){
            refreshfoodTable();
        }
        
    }

    private void cancelOrder() {
        try {
            int currentTableInt = Integer.valueOf(customerTableNoComboBox.getSelectedIndex())+1;
            int currentSelectedid_orderRow = orderTable.getSelectedRow();

            if(currentSelectedid_orderRow == -1){
                return;
            }

            int currentSelectedid_orderInt = Integer.valueOf(String.valueOf(orderTable.getValueAt(currentSelectedid_orderRow, 0)));
            //cancelCheckIsMaking;

            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();

            //update order_record iscancel to 1
            stmt.executeUpdate("UPDATE order_record SET iscancel= 1 WHERE id_order =" + currentSelectedid_orderInt + ";");

            //update at table_n iscancel to 1
            stmt.executeUpdate("UPDATE table_"+ currentTableInt +" SET iscancel= 1 WHERE id_order =" + currentSelectedid_orderInt + ";");

            //delete order from kitchen according to id_order
            stmt.executeUpdate("UPDATE kitchen SET iscancel = 1 WHERE id_order = " + currentSelectedid_orderInt + ";");

            //edit time_last_modified in order_record
            String tempTime = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss").format(Calendar.getInstance().getTime());
            stmt.executeUpdate("UPDATE order_record SET time_last_modified = '" + tempTime + "' WHERE id_order = " + currentSelectedid_orderInt + ";");

            //remove order from ingredient_temporary if not made yet, otherwise let it as is 
            ResultSet rs = stmt.executeQuery("SELECT ismaking FROM ingredient_temporary WHERE id_order =" + currentSelectedid_orderInt + ";");
            if(rs.next()){
                cancelCheckIsMaking = rs.getInt(1);
            }

            if(cancelCheckIsMaking == 1){
                refreshorderTable();
                stmt.close();
                return;
            }
            if(cancelCheckIsMaking == 0){
                stmt.executeUpdate("DELETE FROM ingredient_temporary WHERE id_order =" + currentSelectedid_orderInt + ";");
            }

            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        refreshorderTable();

    }

    private void checkIfNeedNewCustomerIDAndApply() {
        // if new id_customer needed, add it, ispaid =1 means new id is needed as new customer came
        try {
            int currentTableInt = Integer.valueOf(customerTableNoComboBox.getSelectedIndex())+1;
            
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ispaid FROM customer_status WHERE customer_table ="+ currentTableInt +";");
            while(rs.next()){
                ispaid = rs.getInt(1);
            }

            if(ispaid == 1){
                ResultSet rs2 = stmt.executeQuery("SELECT id_customer_next FROM customer_status WHERE customer_table =1;");
                while(rs2.next()){
                    //get next_id on database
                    nextid = rs2.getInt(1);
                }
                //insert the new id_customer into the selected customer_table
                String statement = "UPDATE customer_status SET id_customer= " + nextid + " WHERE customer_table ="+ currentTableInt +";";
                stmt.executeUpdate(statement);

                //id_customer_next +1 in database
                nextid += 1;
                String statement2 = "UPDATE customer_status SET id_customer_next= " + nextid + " WHERE customer_table =1;";
                stmt.executeUpdate(statement2);
                //ispaid turn back to 0
                String statement3 = "UPDATE customer_status SET ispaid =0 WHERE customer_table ="+ currentTableInt +";";
                stmt.executeUpdate(statement3);

                stmt.executeUpdate("TRUNCATE TABLE table_" + currentTableInt + ";");
            }
            stmt.close();

            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean checkIngredient() {
        int selectedRow = foodTable.getSelectedRow();
        id_foodSelectedInt =Integer.valueOf(String.valueOf(foodTable.getModel().getValueAt(selectedRow, 0)));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
        
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ingredient_temporary;");
            ResultSetMetaData rsmd = rs.getMetaData();

            ingredientnumber = rsmd.getColumnCount() - 2;

            ingredientsNameArray = new String[ingredientnumber];
            ingredientAmountArray = new Double[ingredientnumber];
            ingredientNeededAmountOneArray = new Double[ingredientnumber];
            
            for(int i = 3; i < ingredientnumber+3; i++){   //get ingredient name in ingredient_temporary
                ingredientsNameArray[i-3] = rsmd.getColumnName(i);
            }

            for(int i = 0; i < ingredientnumber; i++){
                //need id_order -1
                ResultSet rs2 = stmt.executeQuery("SELECT " + ingredientsNameArray[i] + " FROM ingredient_temporary;");
                //initialize ingredientsAmountsArray[] to 0.0 each
                ingredientAmountArray[i] = 0.0;
                while(rs2.next()){
                    ingredientAmountArray[i] += rs2.getDouble(1);  //ingreident total amount at that moment
                }
            }

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM food_list Where id_food=" + id_foodSelectedInt + ";");
            if(rs2.next()){
                for(int i = 7; i < ingredientnumber+7;i++){
                    ingredientNeededAmountOneArray[i-7] = rs2.getDouble(i);
                }
            }

            orderAmount = Double.valueOf(orderAmountLabel.getText());
            int foodCanMakeAmount = 99999999;
            int foodCanMakeAmountLowest = 99999999;
            Boolean isNotEnough = false;

            //ingredientNeededAmountOneArray is for 1 food to make, so need *orderAmount
            for(int i = 0; i < ingredientnumber;i++){
                if(ingredientAmountArray[i] - (ingredientNeededAmountOneArray[i]*orderAmount) <0 ){
                    isNotEnough = true;
                    foodCanMakeAmount = (int) (ingredientAmountArray[i] / ingredientNeededAmountOneArray[i]);

                    if(foodCanMakeAmountLowest > foodCanMakeAmount){
                        foodCanMakeAmountLowest = foodCanMakeAmount;
                    }
                }
            }

            if(isNotEnough == true){
                JOptionPane.showMessageDialog(null, "Not enough ingredient: Maximium possible amount is " + String.valueOf(foodCanMakeAmountLowest), 
                "Not enough ingredient", JOptionPane.WARNING_MESSAGE);
                return false;  //false= not enough ingredient
            }

            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private void addOrderToTables() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
        
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            /*ResultSet rs = stmt.executeQuery("SELECT * FROM ingredient_temporary;"); --*/

            //first put all needed value into order_record  with new id_order
            
            //id_order is auto increment in order_record, below add new row with new id_order
            stmt.executeUpdate("INSERT INTO order_record VALUES();");
            ResultSet rs2 = stmt.executeQuery("SELECT id_order FROM order_record ORDER BY id_order DESC LIMIT 1;");
            while(rs2.next()){
                id_orderInt = rs2.getInt(1);
            }

            //get table_n at customer_status and update id_customer then customer_table in order_record according to respective id_order
            int selectedTableInt = customerTableNoComboBox.getSelectedIndex() + 1;
            ResultSet rs3 = stmt.executeQuery("SELECT id_customer FROM customer_status WHERE customer_table = '" + selectedTableInt + "';");
            while(rs3.next()){
                id_customerInt = rs3.getInt(1);            
            }

            stmt.executeUpdate("UPDATE order_record SET customer_table=" + selectedTableInt + " WHERE id_order =" + id_orderInt + ";");
            stmt.executeUpdate("UPDATE order_record SET id_customer=" + id_customerInt + " WHERE id_order =" + id_orderInt + ";");

            // id_food is id_foodSelectedInt, update id_food in order_record according to respective id_order
            stmt.executeUpdate("UPDATE order_record SET id_food=" + id_foodSelectedInt + " WHERE id_order =" + id_orderInt + ";");

            // food_type from foodTable, update food_type in order_record according to respective id_order
            int selectedRow = foodTable.getSelectedRow();
            String food_typeString =String.valueOf(foodTable.getModel().getValueAt(selectedRow, 1));
            stmt.executeUpdate("UPDATE order_record SET food_type='" + food_typeString + "' WHERE id_order =" + id_orderInt + ";");

            // food_name from foodTable, update food_name in food_nameString according to respective id_order
            String food_nameString =String.valueOf(foodTable.getModel().getValueAt(selectedRow, 2));
            stmt.executeUpdate("UPDATE order_record SET food_name='" + food_nameString + "' WHERE id_order =" + id_orderInt + ";");

            // amount from orderAmount -> orderAmountInt, update amount in order_record according to respective id_order
            int orderAmountInt = orderAmount.intValue();
            stmt.executeUpdate("UPDATE order_record SET amount=" + orderAmountInt + " WHERE id_order =" + id_orderInt + ";");
            
            // summed_price from get price from food_list and *orderAmount, update amount with summed_priceDouble in order_record according to respective id_order
            stmt.executeUpdate("UPDATE order_record SET amount=" + orderAmount + " WHERE id_order =" + id_orderInt + ";");
            ResultSet rs4 = stmt.executeQuery("SELECT price FROM food_list WHERE id_food = " + id_foodSelectedInt + ";");
            while(rs4.next()){
                priceDouble = rs4.getDouble(1);
            }
            
            Double summed_priceDouble = priceDouble * orderAmount;
            stmt.executeUpdate("UPDATE order_record SET summed_price=" + summed_priceDouble + " WHERE id_order =" + id_orderInt + ";");

            // use timeNowSting to set time_added and time_last_modified
            String timeNowSting = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss").format(Calendar.getInstance().getTime());
            stmt.executeUpdate("UPDATE order_record SET time_added='" + timeNowSting + "' WHERE id_order =" + id_orderInt + ";");
            stmt.executeUpdate("UPDATE order_record SET time_last_modified='" + timeNowSting + "' WHERE id_order =" + id_orderInt + ";");

            //ismaking, isserved, iscancel = 0
            stmt.executeUpdate("UPDATE order_record SET ismaking=0 WHERE id_order =" + id_orderInt + ";");
            stmt.executeUpdate("UPDATE order_record SET isserved=0 WHERE id_order =" + id_orderInt + ";");
            stmt.executeUpdate("UPDATE order_record SET iscancel=0 WHERE id_order =" + id_orderInt + ";");


            //Then, add record to kitchen for make the food, new line first
            stmt.executeUpdate("INSERT INTO kitchen VALUES ();");
            //default id_order is -11
            stmt.executeUpdate("UPDATE kitchen SET id_order=" + id_orderInt + " WHERE id_order =-11;");
            //set customer_table in kitchen
            stmt.executeUpdate("UPDATE kitchen SET customer_table=" + selectedTableInt + " WHERE id_order =" + id_orderInt + ";");
            //set food_name in kitchen
            stmt.executeUpdate("UPDATE kitchen SET food_name='" + food_nameString + "' WHERE id_order =" + id_orderInt + ";");
            //set amount in kitchen
            stmt.executeUpdate("UPDATE kitchen SET amount=" + orderAmountInt + " WHERE id_order =" + id_orderInt + ";");
            //ismaking, isserved, iscancel is default = 0, no need to update


            //Then, add a record in ingredient_temporary to reserve ingredient, as ismaking is 0, these item will not bue summed up when management client do that
            //until kitchen set ismaking to 1

            //new row
            stmt.executeUpdate("INSERT INTO ingredient_temporary VALUES();");

            //change id_order to id_orderInt, default is -99
            stmt.executeUpdate("UPDATE ingredient_temporary SET id_order=" + id_orderInt + " WHERE id_order =-99;");
            //add ismaking as 0
            stmt.executeUpdate("UPDATE ingredient_temporary SET ismaking=0 WHERE id_order =" + id_orderInt+ ";"); 
            
            //then add rest of ingreident
            Double[] ingredientNeededTotalAmountArrayNev = new Double[ingredientNeededAmountOneArray.length];

            for(int i = 0; i < ingredientNeededAmountOneArray.length;i++){
                ingredientNeededTotalAmountArrayNev[i] = -(ingredientNeededAmountOneArray[i]*orderAmount); 
                //get -ve total value each
            }

            for(int i = 0; i < ingredientNeededTotalAmountArrayNev.length;i++){
                stmt.executeUpdate("UPDATE ingredient_temporary SET " + ingredientsNameArray[i] + "=" + ingredientNeededTotalAmountArrayNev[i] + " WHERE id_order =" + id_orderInt+ ";");
            }

            stmt.close();

            //then, add to table "table_n" for cashier client
            //id_order, food_name, amount, summed_price, ismaking, isserved, iscancel 
            PreparedStatement stmt2 = con.prepareStatement("INSERT INTO table_" + String.valueOf(selectedTableInt) + " VALUES(?,?,?,?,?,?,?)");
            stmt2.setInt(1, id_orderInt);
            stmt2.setString(2, food_nameString);
            stmt2.setInt(3, orderAmountInt);
            stmt2.setDouble(4, summed_priceDouble);
            stmt2.setInt(5, 0);
            stmt2.setInt(6, 0);
            stmt2.setInt(7, 0);
            stmt2.execute();
            
            stmt2.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshorderTable();
    }

    private void refreshfoodTable() {
        //below is for foodtable refresh
        String foodtypeSelected= String.valueOf(foodtypeComboBox.getSelectedItem());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();

            if(foodtypeSelected == "all"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list;");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
                
            }
            if(foodtypeSelected == "appetizer"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'appetizer';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
                
            }
            if(foodtypeSelected == "main_course"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'main_course';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
            }
            if(foodtypeSelected == "dessert"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'dessert';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
            }
            if(foodtypeSelected == "beverage"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'beverage';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
            }
            if(foodtypeSelected == "others"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'others';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
            }
            if(foodtypeSelected == "special"){
                ResultSet rs = stmt.executeQuery("SELECT * FROM food_list WHERE food_type = 'special';");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                tableModelFood.setRowCount(0);
                tableModelFood.setColumnCount(0);
    
                for(int i=0; i<3; i++){
                    tableModelFood.addColumn(rsmd.getColumnName(i+1));
                }
                tableModelFood.addColumn(rsmd.getColumnName(6)); //price column

                while(rs.next()){
                    Object[] obj = new Object[4];
    
                    for(int i=0; i<3; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    obj[3] = rs.getInt(6);
                    tableModelFood.addRow(obj);
                }
                stmt.close();
    
                //set column lenght
                foodTable.getColumnModel().getColumn(0).setMinWidth(90);
                foodTable.getColumnModel().getColumn(1).setMinWidth(150);
                foodTable.getColumnModel().getColumn(2).setMinWidth(200);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void refreshorderTable() {    
        
        //below is for orderTable refresh
        String customerTableSelected= String.valueOf(customerTableNoComboBox.getSelectedItem());
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");  
                con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                Statement stmt = con.createStatement();
                try{
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + customerTableSelected + ";");
                ResultSetMetaData rsmd = rs.getMetaData();
                
                int columnCount = rsmd.getColumnCount();
        
                
                tableModelOrder.setRowCount(0);
                tableModelOrder.setColumnCount(0);

                for(int i=0; i<columnCount; i++){
                    tableModelOrder.addColumn(rsmd.getColumnName(i+1));
                }
                
                while(rs.next()){
                    Object[] obj = new Object[columnCount];

                    for(int i=0; i<columnCount; i++){
                        obj[i] = rs.getString(i+1);
                    }
                    tableModelOrder.addRow(obj);
                }

            }
            catch(Exception ex){
                return;
            }
            stmt.close();

            for(int i=0; i<7; i++){
                orderTable.getColumnModel().getColumn(i).setMinWidth(100);
            }
                orderTable.getColumnModel().getColumn(1).setMinWidth(140); //food_name
                orderTable.getColumnModel().getColumn(3).setMinWidth(30); //price
                orderTable.getColumnModel().getColumn(4).setMinWidth(110); //ismaking

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        int refreshorderTableRowCount = orderTable.getRowCount();

        if(refreshorderTableRowCount == 0){
            currentMoneyAmountLabel.setText("$0");
            currentMoneyAmountLabel.repaint();
            return;
        }
        else{
            double priceToShow = 0;
            
            for(int i = 0; i < refreshorderTableRowCount; i++){
                if(Integer.valueOf(String.valueOf(orderTable.getValueAt(i, 6))) == 1){  //pass cancelled item
                    continue;
                }
                priceToShow += Double.parseDouble(String.valueOf(orderTable.getValueAt(i, 3)));
            }

            currentMoneyAmountLabel.setText("$" + String.valueOf(Math.round(priceToShow*10.0)/10.0));
            currentMoneyAmountLabel.repaint();
        }
    }
}    