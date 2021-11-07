//customizable-restaurant-operating-system-with-Java-MySQL v1.0.0
//by lrex93497 @github
//Client_Kitchen
//release under GPLv2

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.logging.*;

import java.sql.*;
import java.text.SimpleDateFormat;

class ClientKitchen implements ActionListener{

    Connection con;
    JMenuItem itemFilMenuRefreshNowRefreshNow;
    JMenuItem itemFilMenuRefreshIntervalSet;
    JMenuItem itemFilMenuOthersExit;
    String timeStamp = "";
    JPanel[] orderPanels;
    JLabel[] foodNameLabels;
    JPanel[] foodNameLabelsPanels;
    JButton[] makeButtons;
    JPanel[] makeButtonsPanels;
    JButton[] serveButtons;
    JPanel[] serveButtonsPanels;
    JButton[] cancelButtons;
    JPanel[] cancelButtonsPanels;
    JLabel[] id_orderLabels;
    JPanel[] id_orderLabelsPanels;
    JLabel[] amountLabels;
    JPanel[] amountLabelsPanels;
    JLabel[] tableNumberLabels;
    JPanel[] tableNumberLabelsPanels;
    Font DefaultFontSetting = new Font("Arial", Font.BOLD, 22);
    GridBagConstraints gbc = new GridBagConstraints();
    int numberOfPanel = 20;
    JPanel makeFoodPanel;
    JFrame kitchenFrame;
    String conInf; 
    String account; 
    String password;
    Integer[] id_orderIntArray;
    Integer[] customer_tableInteArray;
    String[] food_nameStringArray;
    Integer[] amountIntArray;
    Integer[] ismakingIntArray;                   
    Integer[] isservedIntArray;
    Integer[] iscancelIntArray;
    int valueArrayCounter;
    int selectedorder_id;
    int selectedTableNumber;
    int checkiscancel;
    int checkIsmaking;
    int checkismaking;
    int refreshCounter;
    int autoRefreshInterval = 15;
    int tempautoRefreshInterval = 0;
   
    ClientKitchen(String tempConInf, String tempAccount, String tempPassword) throws IOException {
        conInf = tempConInf;
        account = tempAccount;
        password = tempPassword;

        //get autoRefreshInterval from config.inf, if not exist creat it
        File file = new File("kconfig.inf");
        if(file.exists()) {
            try{
                String content = new String(Files.readAllBytes(Paths.get("kconfig.inf")), StandardCharsets.UTF_8);
                autoRefreshInterval = Integer.valueOf(content.substring(26));
            }
            catch(FileNotFoundException exception){
                exception.printStackTrace();
            }
        }
        else {
            try{
                FileWriter writer = new FileWriter("kconfig.inf");
                writer.write("int autoRefreshInterval = 15");
                writer.flush();
                writer.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


        kitchenFrame = new JFrame(); 
        kitchenFrame.setSize(700,700);
        kitchenFrame.setMinimumSize(new Dimension(1200,800));
        kitchenFrame.setLayout(new GridBagLayout());  
        kitchenFrame.setTitle("Kitchen Client");
        kitchenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        kitchenFrame.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        kitchenFrame.setJMenuBar(menuBar);

        JMenu filMenuRefreshNow = new JMenu("Refresh now");
        menuBar.add(filMenuRefreshNow);

        JMenu filMenuRefreshInterval = new JMenu("Refresh interval");
        menuBar.add(filMenuRefreshInterval);

        itemFilMenuRefreshNowRefreshNow = new JMenuItem("Refresh now");
        itemFilMenuRefreshNowRefreshNow.addActionListener(this);
        itemFilMenuRefreshNowRefreshNow.setActionCommand("refresh");
        filMenuRefreshNow.add(itemFilMenuRefreshNowRefreshNow);

        itemFilMenuRefreshIntervalSet = new JMenuItem("Set refresh interval");
        itemFilMenuRefreshIntervalSet.addActionListener(this);
        itemFilMenuRefreshIntervalSet.setActionCommand("setRefreshInterval");
        filMenuRefreshInterval.add(itemFilMenuRefreshIntervalSet);

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
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        kitchenFrame.add(timePanel, gbc);


        String timeStamp = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss   EEE").format(Calendar.getInstance().getTime());
        JLabel timeLabel = new JLabel(timeStamp);
        //System.out.println(Calendar.getInstance().getTime());

        //runnable for time refresh
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

        //runnable2 for refresh time 10 second each
        Runnable runnable2 = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    refreshCounter = autoRefreshInterval;
                    kitchenFrame.setTitle("Kitchen Client: Auto refresh in " + refreshCounter + "s");
                    while( refreshCounter > 0){
                        try {
                            Thread.sleep(1000);
                            refreshCounter -= 1;
                            kitchenFrame.setTitle("Kitchen Client: Auto refresh in " + refreshCounter + "s");
                        }
                          catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    refresh();
                }
            }
        };

        Thread t2 = new Thread(runnable2);
        t2.start();

        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timePanel.add(timeLabel);


        makeFoodPanel = new JPanel();
        makeFoodPanel.setLayout(new GridBagLayout());
        makeFoodPanel.setBackground(new Color(0,149,255));

        JScrollPane jps = new JScrollPane(makeFoodPanel);
        //jps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        //jps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 4.0;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        kitchenFrame.add(jps, gbc);

        refresh();
    
        kitchenFrame.setVisible(true);

        //need add update thread pre 7s(planned for now)

    }

    void buildOrders(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            //System.out.println(conInf+account+password); 
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Kitchen;");
            while(rs.next()){
                numberOfPanel = rs.getInt(1);
            }

            orderPanels = new JPanel[numberOfPanel];
            foodNameLabels = new JLabel[numberOfPanel];
            makeButtons = new JButton[numberOfPanel];
            serveButtons = new JButton[numberOfPanel];
            cancelButtons = new JButton[numberOfPanel];
            id_orderLabels = new JLabel[numberOfPanel];
            amountLabels = new JLabel[numberOfPanel];
            tableNumberLabels = new JLabel[numberOfPanel];

            foodNameLabelsPanels = new JPanel[numberOfPanel];
            makeButtonsPanels = new JPanel[numberOfPanel];
            serveButtonsPanels = new JPanel[numberOfPanel];
            cancelButtonsPanels = new JPanel[numberOfPanel];
            id_orderLabelsPanels = new JPanel[numberOfPanel];
            amountLabelsPanels = new JPanel[numberOfPanel];
            tableNumberLabelsPanels = new JPanel[numberOfPanel];

            id_orderIntArray = new Integer[numberOfPanel];
            customer_tableInteArray = new Integer[numberOfPanel];
            food_nameStringArray = new String[numberOfPanel];
            amountIntArray = new Integer[numberOfPanel];
            ismakingIntArray = new Integer[numberOfPanel];                 
            isservedIntArray = new Integer[numberOfPanel];
            iscancelIntArray = new Integer[numberOfPanel];
            //iscancelIntArray is to detect if item cancelled, if 1 , kitchen will not above to push make or serve(error pop up and delete this order in kitchen)
            

            //get each value array from kitchen
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM Kitchen;");
            valueArrayCounter = 0;
            while(rs2.next()){
                id_orderIntArray[valueArrayCounter] = rs2.getInt(1);
                customer_tableInteArray[valueArrayCounter] = rs2.getInt(2);
                food_nameStringArray[valueArrayCounter] = rs2.getString(3);
                amountIntArray[valueArrayCounter] = rs2.getInt(4);
                ismakingIntArray[valueArrayCounter] = rs2.getInt(5);
                isservedIntArray[valueArrayCounter] = rs2.getInt(6);
                iscancelIntArray[valueArrayCounter] = rs2.getInt(7);
                valueArrayCounter +=1;
            }

            //build order panel
            for(int n = 0; n < numberOfPanel; n++){

                orderPanels[n] = new JPanel();
                //orderPanels[n].setBackground(new Color(255,231,166));
                orderPanels[n].setLayout(new GridBagLayout());
                gbc.insets = new Insets(3,3,3,3);
                gbc.gridx = 0;
                gbc.gridy = n;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = gbc.BOTH;
                makeFoodPanel.add(orderPanels[n], gbc);
    
                id_orderLabels[n] = new JLabel("id: " + id_orderIntArray[n]);
                id_orderLabels[n].setFont(DefaultFontSetting);
                id_orderLabels[n].setOpaque(true);
                //id_orderLabels[n].setBackground(new Color(255,192,110));
                id_orderLabelsPanels[n] = new JPanel();
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 2.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(id_orderLabelsPanels[n], gbc);
                id_orderLabelsPanels[n].add(id_orderLabels[n]);
    
                tableNumberLabels[n] = new JLabel("Table: " + customer_tableInteArray[n]);
                tableNumberLabels[n].setFont(DefaultFontSetting);
                tableNumberLabels[n].setOpaque(true);
                //tableNumberLabels[n].setBackground(new Color(255,192,110));
                tableNumberLabelsPanels[n] = new JPanel();
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 2.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(tableNumberLabelsPanels[n], gbc);
                tableNumberLabelsPanels[n].add(tableNumberLabels[n]);
    
                foodNameLabels[n] = new JLabel(food_nameStringArray[n]);
                foodNameLabels[n].setFont(DefaultFontSetting);
                foodNameLabels[n].setOpaque(true);
                //foodNameLabels[n].setBackground(new Color(255,192,110));
                foodNameLabelsPanels[n] = new JPanel(); 
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.weightx = 15.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(foodNameLabelsPanels[n], gbc);
                foodNameLabelsPanels[n].add(foodNameLabels[n]);
    
                amountLabels[n] = new JLabel("Amount: " + amountIntArray[n]);
                amountLabels[n].setFont(DefaultFontSetting);
                amountLabels[n].setOpaque(true);
                //amountLabels[n].setBackground(new Color(255,192,110));
                amountLabelsPanels[n] = new JPanel();
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 3;
                gbc.gridy = 0;
                gbc.weightx = 2.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(amountLabelsPanels[n], gbc);
                amountLabelsPanels[n].add(amountLabels[n]);
    
                makeButtons[n] = new JButton("Make");
                makeButtons[n].setFont(DefaultFontSetting);
                makeButtons[n].addActionListener(this);
                makeButtonsPanels[n] = new JPanel();
                if(ismakingIntArray[n] == 1){
                    makeButtons[n].setEnabled(false);
                }
                else{
                    makeButtons[n].setEnabled(true);
                }
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 4;
                gbc.gridy = 0;
                gbc.weightx = 0.5;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(makeButtonsPanels[n], gbc);
                makeButtonsPanels[n].add(makeButtons[n]);
    
                serveButtons[n] = new JButton("Serve");
                serveButtons[n].setFont(DefaultFontSetting);
                serveButtons[n].addActionListener(this);
                serveButtonsPanels[n]  = new JPanel();
                if(isservedIntArray[n] == 1){
                    serveButtons[n].setEnabled(false);
                }
                else{
                    serveButtons[n].setEnabled(true);
                }
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 5;
                gbc.gridy = 0;
                gbc.weightx = 0.5;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(serveButtonsPanels[n], gbc);
                serveButtonsPanels[n].add(serveButtons[n]);
    
                cancelButtons[n] = new JButton("Cancel");
                cancelButtons[n].setFont(DefaultFontSetting);
                cancelButtons[n].addActionListener(this);
                cancelButtonsPanels[n] = new JPanel();
                gbc.insets = new Insets(7,7,7,7);
                gbc.gridx = 6;
                gbc.gridy = 0;
                gbc.weightx = 0.5;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.fill = gbc.BOTH;
                orderPanels[n].add(cancelButtonsPanels[n], gbc);
                cancelButtonsPanels[n].add(cancelButtons[n]);
            }

            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void refresh(){
        //check SQL first, then get numberOfPanel
        makeFoodPanel.removeAll();

        if(numberOfPanel < 10){
            buildOrders();

            //int emptyPanelNumber = 10 - numberOfPanel;
            orderPanels = new JPanel[10];

            for(int n = numberOfPanel; n < 10; n++){

                orderPanels[n] = new JPanel();
                //orderPanels[n].setBackground(Color.blue);
                orderPanels[n].setLayout(new GridBagLayout());
                gbc.insets = new Insets(3,3,3,3);
                gbc.gridx = 0;
                gbc.gridy = n;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = gbc.BOTH;
                makeFoodPanel.add(orderPanels[n], gbc);
                orderPanels[n].setOpaque(false);

            }
        }
        if(numberOfPanel >= 10){
            buildOrders();
        }

        refreshCounter = autoRefreshInterval;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        for(int n = 0; n<numberOfPanel ; n++){
            if(e.getSource() == makeButtons[n]){
                selectedorder_id = Integer.valueOf(id_orderLabels[n].getText().substring(4));
                selectedTableNumber = Integer.valueOf(tableNumberLabels[n].getText().substring(7));

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    //System.out.println(conInf+account+password); 
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();

                    //Firstly,  check iscancel is 1 or not, 1 = cancelled by other client
                    //if 1, give error popup and delete order in kitchen, then refresh 
                    //otherwise update tables
                    checkiscancel = 0;
                    ResultSet rs = stmt.executeQuery("SELECT iscancel FROM kitchen WHERE id_order ="+ selectedorder_id +";");
                    while(rs.next()){
                        checkiscancel = rs.getInt(1);
                    }

                    if(checkiscancel == 1){
                        stmt.executeUpdate("DELETE FROM kitchen WHERE id_order =" + selectedorder_id + ";");
                        JOptionPane.showMessageDialog(null, "Order canceled", "Order canceled", JOptionPane.WARNING_MESSAGE);
                    }

                    else{
                        //set ismaking in ingredient_temporary, kitchen, order_record, table_n to 1.
                        stmt.executeUpdate("UPDATE ingredient_temporary SET ismaking=1 WHERE id_order =" + selectedorder_id + ";");
                        stmt.executeUpdate("UPDATE order_record SET ismaking=1 WHERE id_order =" + selectedorder_id + ";");
                        stmt.executeUpdate("UPDATE table_" + selectedTableNumber + " SET ismaking=1 WHERE id_order =" + selectedorder_id + ";");
                        stmt.executeUpdate("UPDATE kitchen SET ismaking=1 WHERE id_order =" + selectedorder_id + ";");

                        //edit time_last_modified in order_record
                        String tempTime = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss").format(Calendar.getInstance().getTime());
                        stmt.executeUpdate("UPDATE order_record SET time_last_modified = '" + tempTime + "' WHERE id_order = " + selectedorder_id + ";");
                    }

                    stmt.close();
        
                } catch (SQLException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                }

                refresh();
            }
            if(e.getSource() == serveButtons[n]){
                selectedorder_id = Integer.valueOf(id_orderLabels[n].getText().substring(4));
                selectedTableNumber = Integer.valueOf(tableNumberLabels[n].getText().substring(7));

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    //System.out.println(conInf+account+password); 
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();

                    //Firstly,  check iscancel is 1 or not, 1 = cancelled by other client
                    //if 1, give error popup and delete order in kitchen, then refresh 
                    //then check is making , if 0 ,give error popup then refresh, 
                    //food must be made to serve
                    //otherwise still remove order as served
                    checkiscancel = 0;
                    checkismaking = 0;
                    ResultSet rs = stmt.executeQuery("SELECT iscancel FROM kitchen WHERE id_order ="+ selectedorder_id +";");
                    while(rs.next()){
                        checkiscancel = rs.getInt(1);
                    }

                    if(checkiscancel == 1){
                        stmt.executeUpdate("DELETE FROM kitchen WHERE id_order =" + selectedorder_id + ";");
                        JOptionPane.showMessageDialog(null, "Order cancelled", "Order cancelled", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        ResultSet rs2 = stmt.executeQuery("SELECT ismaking FROM kitchen WHERE id_order ="+ selectedorder_id +";");
                        while(rs2.next()){
                            checkismaking = rs2.getInt(1);
                        }
                        if(checkismaking == 0){
                            JOptionPane.showMessageDialog(null, "Food must be made to serve.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            //set isserved in order_record, table_n to 1.
                            //the one in kitchen will be deleted
                            stmt.executeUpdate("UPDATE order_record SET isserved=1 WHERE id_order =" + selectedorder_id + ";");
                            stmt.executeUpdate("UPDATE table_" + selectedTableNumber + " SET isserved=1 WHERE id_order =" + selectedorder_id + ";");
                            stmt.executeUpdate("DELETE FROM kitchen WHERE id_order =" + selectedorder_id + ";");
                        
                            //edit time_last_modified in order_record
                            String tempTime = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss").format(Calendar.getInstance().getTime());
                            stmt.executeUpdate("UPDATE order_record SET time_last_modified = '" + tempTime + "' WHERE id_order = " + selectedorder_id + ";");
                        }
                    }

                    stmt.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }
            if(e.getSource() == cancelButtons[n]){
                selectedorder_id = Integer.valueOf(id_orderLabels[n].getText().substring(4));
                selectedTableNumber = Integer.valueOf(tableNumberLabels[n].getText().substring(7));
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    //System.out.println(conInf+account+password); 
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();

                    //first edit iscancel to 1 in order_record, table_n
                    //then remove it from ingredient_temporary if ismaking is 0
                    //then remove it from kitchen
                    stmt.executeUpdate("UPDATE order_record SET iscancel = 1 WHERE id_order= " + selectedorder_id + ";");
                    
                    //edit time_last_modified in order_record
                    String tempTime = new SimpleDateFormat("dd/MM/yyyy   HH.mm.ss").format(Calendar.getInstance().getTime());
                    stmt.executeUpdate("UPDATE order_record SET time_last_modified = '" + tempTime + "' WHERE id_order = " + selectedorder_id + ";");
                    stmt.executeUpdate("UPDATE table_" + selectedTableNumber + " SET iscancel = 1 WHERE id_order= " + selectedorder_id + ";");
                    
                    ResultSet rs = stmt.executeQuery("SELECT ismaking FROM ingredient_temporary where id_order =" + selectedorder_id + ";");
                    while(rs.next()){
                        checkIsmaking = rs.getInt(1);
                    }

                    if(checkIsmaking == 1){
                        stmt.executeUpdate("DELETE FROM kitchen WHERE id_order =" + selectedorder_id + ";");  
                    }
                    else{
                        stmt.executeUpdate("DELETE FROM ingredient_temporary WHERE id_order =" + selectedorder_id + ";");
                        stmt.executeUpdate("DELETE FROM kitchen WHERE id_order =" + selectedorder_id + ";");  
                    }
                    stmt.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientKitchen.class.getName()).log(Level.SEVERE, null, ex);
                }

                refresh();
            }
        }

        if(s == "setRefreshInterval"){
            try{
                try{
                    String tempautoRefreshIntervalString = JOptionPane.showInputDialog("Enter refresh interval in integer second", autoRefreshInterval);
                    
                    if(tempautoRefreshIntervalString == null){
                        return;
                    }
                    
                    tempautoRefreshInterval = Integer.valueOf(tempautoRefreshIntervalString);

                    if(tempautoRefreshInterval == 0){
                        JOptionPane.showMessageDialog(null, "Interval cannot be 0s", "Error", JOptionPane.WARNING_MESSAGE);
                        itemFilMenuRefreshIntervalSet.doClick();
                        return;
                    }
                    
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.WARNING_MESSAGE);
                    itemFilMenuRefreshIntervalSet.doClick();
                    return;
                }

                autoRefreshInterval = tempautoRefreshInterval;
                
                //write to config.inf after obtained value
                File file = new File("kconfig.inf");
                if(file.exists()) {
                    try{
                        FileWriter writer = new FileWriter("kconfig.inf");
                        writer.write("int autoRefreshInterval = " + autoRefreshInterval);
                        writer.flush();
                        writer.close();
                    }
                    catch(FileNotFoundException exception){
                        exception.printStackTrace();
                    }
                }
                else {
                    try{
                        FileWriter writer = new FileWriter("kconfig.inf");
                        writer.write("int autoRefreshInterval = " + autoRefreshInterval);
                        writer.flush();
                        writer.close();
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        if(s == "exit"){
            System.exit(0);
        }
        if(s == "refresh"){
            refresh();
        }

    }

}

    