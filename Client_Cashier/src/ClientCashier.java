//customizable-restaurant-operating-system-with-Java-MySQL v1.0.1 
//by lrex93497 @github
//Client_Cahsier v1.0.0
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

class ClientCashier implements ActionListener{

    Connection con;
    DefaultTableModel tableModelOrder;
    JMenuItem itemFilMenuRefreshRefreshTable;
    JMenuItem itemFilMenuRefreshRefreshCustomerTable;
    JMenuItem itemFilMenuOthersExit;
    JTable foodTable;
    JTable orderTable;
    String timeStamp = "";
    JLabel originalMoneyAmountLabel;
    JLabel othereChargeAmountLabel;
    JLabel serviceChargeAmountLabel;
    JLabel MoneyAmountAfterChargeLabel;
    JLabel discountAmountLabel;
    JLabel finalMoneyAmountAfterChargeLabel;
    JLabel receivedMoneyAmountLabel;
    JLabel changeMoneyAmountLabel;
    JButton[] numberButtons = new JButton[9];
    String conInf; 
    String account; 
    String password;
    int totalTableNumber;
    String[] customerTableNo;
    JComboBox customerTableNoComboBox;
    boolean newvalue =true;
    String valueShowed = "0.0";
    String temp;
    boolean dotUsed = false;
    Double service_charge_percent;
    Double discount_percent;
    Double priceToShow;
    Double originalPrice;
    Double finaltotalPrice;
    int cancelCheckIsMaking;
    Double receivedValue;
    int ispaid;
    JLabel ispaidLabel;


    ClientCashier(String tempConInf, String tempAccount, String tempPassword){
        conInf = tempConInf;
        account = tempAccount;
        password = tempPassword;

        Font DefaultFontSetting = new Font("Arial", Font.BOLD, 22);
        Font buttonsFontSetting = new Font("Arial", Font.BOLD, 18);

        JFrame cashierFrame = new JFrame(); 
        cashierFrame.setSize(700,700);
        cashierFrame.setMinimumSize(new Dimension(1200,800));
        cashierFrame.setLayout(new GridBagLayout());  
        cashierFrame.setTitle("Cashier Client");
        cashierFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        cashierFrame.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        cashierFrame.setJMenuBar(menuBar);

        JMenu filMenuRefresh = new JMenu("Refresh now");
        menuBar.add(filMenuRefresh);

        JMenu filMenuOthers = new JMenu("Others");
        menuBar.add(filMenuOthers);

        itemFilMenuRefreshRefreshTable = new JMenuItem("Refresh data now");
        itemFilMenuRefreshRefreshTable.addActionListener(this);
        itemFilMenuRefreshRefreshTable.setActionCommand("refresh");
        filMenuRefresh.add(itemFilMenuRefreshRefreshTable);

        itemFilMenuRefreshRefreshCustomerTable = new JMenuItem("Refresh customer table Combobox now");
        itemFilMenuRefreshRefreshCustomerTable.addActionListener(this);
        itemFilMenuRefreshRefreshCustomerTable.setActionCommand("refreshCustomerTable");
        filMenuRefresh.add(itemFilMenuRefreshRefreshCustomerTable);

        itemFilMenuOthersExit = new JMenuItem("Exit");
        itemFilMenuOthersExit.addActionListener(this);
        itemFilMenuOthersExit.setActionCommand("exit");
        filMenuOthers.add(itemFilMenuOthersExit);


        GridBagConstraints gbc = new GridBagConstraints();
        JPanel timePanel = new JPanel();
        //timePanel.setBackground(Color.gray);
        timePanel.setLayout(new GridLayout(1,1));
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        cashierFrame.add(timePanel, gbc);

        JPanel customerTableNoPanel = new JPanel();
        customerTableNoPanel.setLayout(new GridBagLayout());
        //customerTableNoPanel.setBackground(Color.yellow);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 5.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        cashierFrame.add(customerTableNoPanel, gbc);

        JPanel tableOrderPanel = new JPanel();
        tableOrderPanel.setLayout(new GridBagLayout());
        //tableOrderPanel.setBackground(Color.green);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 5.0;
        gbc.weighty = 10.0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = gbc.BOTH;
        cashierFrame.add(tableOrderPanel, gbc);
        
        JPanel originalMoneyPanel = new JPanel();
        originalMoneyPanel.setLayout(new GridBagLayout());
        //originalMoneyPanel.setBackground(Color.red);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 5.0;
        gbc.weighty = 0.05;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cashierFrame.add(originalMoneyPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,3,3,3));
        //buttonPanel.setBackground(Color.red);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 5.0;
        gbc.weighty = 2.0;
        gbc.fill = gbc.BOTH;
        gbc.gridheight = 3;
        cashierFrame.add(buttonPanel, gbc);

        JPanel changPanel = new JPanel();
        changPanel.setLayout(new GridBagLayout());
        changPanel.setBackground(Color.pink);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 5.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        gbc.gridheight = 1;
        cashierFrame.add(changPanel, gbc);


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

        Integer[] customerTableNoIntegers = {};  
        // the array used desn't matter, the item will be refresh anyway in refreshCustomerTable()
        //but by applied another array first, no need to add boolean isstart like waiter client 
        customerTableNoComboBox = new JComboBox<>(customerTableNoIntegers); 
        customerTableNoComboBox.setFont(DefaultFontSetting);
        customerTableNoComboBox.addActionListener(this);
        customerTableNoComboBox.setActionCommand("selectTable");
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
        
        timeLabel.setFont(new Font("Arial", Font.BOLD, 42));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timePanel.add(timeLabel);


        tableModelOrder = new DefaultTableModel();
        orderTable = new JTable(tableModelOrder);
        orderTable.setFont(DefaultFontSetting);
        orderTable.setRowHeight(30);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JTableHeader orderTableHeader = orderTable.getTableHeader();
        orderTableHeader.setFont(DefaultFontSetting);


        JScrollPane jpso = new JScrollPane(orderTable);
        //jps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        //jps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        tableOrderPanel.add(jpso, gbc);

        JLabel currentMoneyLabel = new JLabel("Food only: ");
        currentMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(currentMoneyLabel, gbc);

        JLabel ServiceChargeLabel = new JLabel("Service charge: ");
        ServiceChargeLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(ServiceChargeLabel, gbc);

        /*JLabel OtherChargeLabel = new JLabel("Other charge: ");
        OtherChargeLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(OtherChargeLabel, gbc);
        --*/

        JLabel originalTotalMoneyLabel = new JLabel("Original $: ");
        originalTotalMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(originalTotalMoneyLabel, gbc);


        originalMoneyAmountLabel = new JLabel("xxxxxxx.00");
        originalMoneyAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(originalMoneyAmountLabel, gbc);

        /*othereChargeAmountLabel = new JLabel("$xxxxxxx.00");
        othereChargeAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(othereChargeAmountLabel, gbc);
        --*/

        serviceChargeAmountLabel = new JLabel("xxx%");
        serviceChargeAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(serviceChargeAmountLabel, gbc);

        MoneyAmountAfterChargeLabel = new JLabel("xxxxxxx.00");
        MoneyAmountAfterChargeLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = gbc.BOTH;
        originalMoneyPanel.add(MoneyAmountAfterChargeLabel, gbc);


        JLabel discountLabel = new JLabel("Discount: ");
        discountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(discountLabel, gbc);

        discountAmountLabel = new JLabel("xx%off");
        discountAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(discountAmountLabel, gbc);

        JLabel finalTotalMoneyLabel = new JLabel("Total $: ");
        finalTotalMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(finalTotalMoneyLabel, gbc);

        finalMoneyAmountAfterChargeLabel = new JLabel("xxxxxxx.00");
        finalMoneyAmountAfterChargeLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(finalMoneyAmountAfterChargeLabel, gbc);

        JLabel recivedMoneyLabel = new JLabel("Recived  $: ");
        recivedMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(recivedMoneyLabel, gbc);

        receivedMoneyAmountLabel = new JLabel(valueShowed);
        receivedMoneyAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(receivedMoneyAmountLabel, gbc);

        JLabel changeMoneyLabel = new JLabel("Change: ");
        changeMoneyLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(changeMoneyLabel, gbc);

        changeMoneyAmountLabel = new JLabel("n/a");
        changeMoneyAmountLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        changPanel.add(changeMoneyAmountLabel, gbc);


        for (int n = 0; n<9; n++){
            numberButtons[n] = new JButton();
            numberButtons[n].setText(String.valueOf(n+1));
            numberButtons[n].addActionListener(this);
            numberButtons[n].setFont(buttonsFontSetting);
            buttonPanel.add(numberButtons[n]);
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setFont(buttonsFontSetting);
        zeroButton.addActionListener(this);
        zeroButton.setActionCommand("0");

        JButton dotButton = new JButton(".");
        dotButton.setFont(buttonsFontSetting);
        dotButton.addActionListener(this);
        dotButton.setActionCommand("dot");

        JButton delButton = new JButton("del");
        delButton.setFont(buttonsFontSetting);
        delButton.addActionListener(this);
        delButton.setActionCommand("del");

        JButton cancelItemButton = new JButton("<html><body style='text-align: center'><p>Cancel<br/>Item</p></html>");
        cancelItemButton.setFont(buttonsFontSetting);
        cancelItemButton.addActionListener(this);
        cancelItemButton.setActionCommand("cancel");

        JButton receiveButton = new JButton("Receive");
        receiveButton.setFont(buttonsFontSetting);
        receiveButton.addActionListener(this);
        receiveButton.setActionCommand("receive");

        ispaidLabel = new JLabel("NOT PAID", SwingConstants.CENTER);
        ispaidLabel.setFont(DefaultFontSetting);
        ispaidLabel.setOpaque(true);
        ispaidLabel.setBackground(new Color(255,130,130));

        buttonPanel.add(zeroButton);
        buttonPanel.add(dotButton);
        buttonPanel.add(delButton);
        buttonPanel.add(cancelItemButton);
        buttonPanel.add(receiveButton);
        buttonPanel.add(ispaidLabel);

        refreshCustomerTable();
        refreshdata();

        cashierFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        for(int i=0;i<9;i++) {
            if(e.getSource() == numberButtons[i]){
                if(newvalue == true){
                    valueShowed = "";
                    newvalue = false;
                    receivedMoneyAmountLabel.setText(valueShowed);
                }
                temp = Integer.toString(i+1);
                valueShowed = valueShowed.concat(temp);
                receivedMoneyAmountLabel.setText(valueShowed);
                
            }
        }
        
        if(s == "0"){
            if(newvalue == true){
                valueShowed = "";
                newvalue = false;
                receivedMoneyAmountLabel.setText(valueShowed);
            }
            valueShowed = valueShowed.concat("0");
            receivedMoneyAmountLabel.setText(valueShowed); 
        }
        if(s == "dot"){
            if(dotUsed == false){
                if(valueShowed == "0.0" | valueShowed == ""){
                    valueShowed = "0";
                    newvalue = false;
                }
                valueShowed = valueShowed.concat(".");
                receivedMoneyAmountLabel.setText(valueShowed);
                dotUsed = true;
                return;
            }
            if(dotUsed == true){
                JOptionPane.showMessageDialog(null, "dot already present", "Error", JOptionPane.ERROR_MESSAGE);
            }        }
        if(s == "del"){
            if(valueShowed == ""){
                return;
            }
            else{
                if(valueShowed.substring(valueShowed.length() - 1).equals(".")){
                    dotUsed = false;
                }
                valueShowed = valueShowed.substring(0, valueShowed.length() - 1);
                receivedMoneyAmountLabel.setText(valueShowed);
            }
        }
        if(s == "cancel"){
            cancelOrder();
        }
        if(s == "receive"){
            receive();
        }

        if(s == "refresh"){
            refreshdata();
        }
        if(s == "refreshCustomerTable"){
            refreshCustomerTable();
        }

        if(s == "selectTable"){
            refreshdata();
        }
        
        if(s == "exit"){
            System.exit(0);
        }
        
    }

    private void receive() {
        String receivedString = receivedMoneyAmountLabel.getText();
        if(receivedString.equals("")){
            JOptionPane.showMessageDialog(null, "Received $ is nothing", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if( receivedString.substring(receivedString.length() - 1).equals(".")){
            JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        else{
            Double change = 0.0;
            Double needToPay = Double.valueOf(finalMoneyAmountAfterChargeLabel.getText());
            change = Double.valueOf(receivedString) - needToPay;

            if(change >= 0){
                changeMoneyAmountLabel.setText(String.valueOf(Math.round(change*10.0)/10.0));

                int currentTableInt = Integer.valueOf(customerTableNoComboBox.getSelectedIndex())+1;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();
                    //change ispaid to 1 for waiter client to reset bill and add new customer
                    stmt.executeUpdate("UPDATE customer_status SET ispaid=1 WHERE customer_table=" + currentTableInt + ";");
                    stmt.close();
        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
                }

                ispaidLabel.setText("PAID");
                ispaidLabel.setBackground(new Color(130,255,184));

                //reset valueShowed dotUsed for other input
                valueShowed = "";
                dotUsed = false;
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Payment not enough", "Payment not enough", JOptionPane.WARNING_MESSAGE);
                return;
            }

        }
        
        //receivedValue = receivedMoneyAmountLabel.getText();

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

            //remove order from ingredient_temporary if not made yet, otherwise let it as is 
            ResultSet rs = stmt.executeQuery("SELECT ismaking FROM ingredient_temporary WHERE id_order =" + currentSelectedid_orderInt + ";");
            if(rs.next()){
                cancelCheckIsMaking = rs.getInt(1);
            }

            if(cancelCheckIsMaking == 1){
                refreshdata();
                stmt.close();
                return;
            }
            if(cancelCheckIsMaking == 0){
                stmt.executeUpdate("DELETE FROM ingredient_temporary WHERE id_order =" + currentSelectedid_orderInt + ";");
            }

            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
        }

        refreshdata();
    }

    private void refreshCustomerTable() {
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
           

            customerTableNo = new String[totalTableNumber];
            
            for(i = 0; i<totalTableNumber; i++){
                customerTableNo[i] = "table_" + String.valueOf(i+1);
            }

            customerTableNoComboBox.removeAllItems();
            for(String n : customerTableNo){
                customerTableNoComboBox.addItem(n);
            }
        
            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    private void refreshdata() {    
        
        //below is for orderTable refresh
        String customerTableSelected= String.valueOf(customerTableNoComboBox.getSelectedItem());
        String customerTableSelectedNumber = String.valueOf(customerTableNoComboBox.getSelectedIndex()+1);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
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

            for(int i=0; i<7; i++){
                orderTable.getColumnModel().getColumn(i).setMinWidth(100);
            }
                orderTable.getColumnModel().getColumn(1).setMinWidth(140); //food_name
                orderTable.getColumnModel().getColumn(3).setMinWidth(180); //summed price
                orderTable.getColumnModel().getColumn(4).setMinWidth(110); //ismaking

            //set discount and service charge label
            service_charge_percent = 0.0;
            discount_percent = 0.0;
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM charge_discount WHERE id=0;");
            while(rs2.next()){
                service_charge_percent = rs2.getDouble(2);
                discount_percent = rs2.getDouble(3);
            }

            serviceChargeAmountLabel.setText(Double.toString(service_charge_percent) + "%");
            discountAmountLabel.setText(Double.toString(discount_percent) + "% Off");
            

            int refreshorderTableRowCount = orderTable.getRowCount();

            if(refreshorderTableRowCount == 0){
                originalMoneyAmountLabel.setText("$0");
                originalMoneyAmountLabel.repaint();
                MoneyAmountAfterChargeLabel.setText("$0");
                finalMoneyAmountAfterChargeLabel.setText("$0");
                return;
            }
            else{
                priceToShow = 0.0;
                
                for(int i = 0; i < refreshorderTableRowCount; i++){
                    if(Integer.valueOf(String.valueOf(orderTable.getValueAt(i, 6))) == 1){ //pass cancelled item
                        continue;
                    }
                    priceToShow += Double.parseDouble(String.valueOf(orderTable.getValueAt(i, 3)));
                }

                //value show in label round to 1 decimal place, but in caculation, no rounding.
                //except for receive - total, this use rounded value to caculate
                originalMoneyAmountLabel.setText("$" + String.valueOf(Math.round(priceToShow*10.0)/10.0));
                //originalMoneyAmountLabel.repaint();

                originalPrice = priceToShow*(1.0+(service_charge_percent/100));
                MoneyAmountAfterChargeLabel.setText(String.valueOf(Math.round(originalPrice*10.0)/10.0));

                finaltotalPrice = originalPrice*(1.0-(discount_percent/100));
                finalMoneyAmountAfterChargeLabel.setText(String.valueOf(Math.round(finaltotalPrice*10.0)/10.0));
            }

            //get ispaid and set ispaidLabel to PAID(green 130,255,184) or NOT PAID(red 255,130,130) with respectively color
            ResultSet rs3 = stmt.executeQuery("SELECT ispaid FROM customer_status WHERE customer_table =" + customerTableSelectedNumber + ";");
            while(rs3.next()){
                ispaid = rs3.getInt(1);
            }
            if(ispaid == 0){
                ispaidLabel.setText("NOT PAID");
                ispaidLabel.setBackground(new Color(255,130,130));
                receivedMoneyAmountLabel.setText("0.0");
            }
            if(ispaid == 1){
                ispaidLabel.setText("PAID");
                ispaidLabel.setBackground(new Color(130,255,184));
                changeMoneyAmountLabel.setText("n/a");
            }
            
            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientCashier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}