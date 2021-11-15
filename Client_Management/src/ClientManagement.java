//customizable-restaurant-operating-system-with-Java-MySQL v1.0.1
//by lrex93497 @github
//Client_Management v1.0.1
//release under GPLv2

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.logging.*;


import java.sql.*;
import java.text.SimpleDateFormat;


class ClientManagement implements ActionListener{

    Font DefaultFontSetting = new Font("Arial", Font.BOLD, 22);
    DefaultTableModel tableModel;
    int tempCheckrs;
    JMenuItem itemfilMenuDatabaseChooseTable;
    JMenuItem itemfilMenuDatabaseSummizeUsed;
    JMenuItem itemfilMenuCheckCurrentIngredients;
    JMenuItem itemfilMenuSetCharge;
    JMenuItem itemFilMenuOthersExit;
    JMenuItem itemfilMenuCheckSetTableAmount;
    JMenuItem itemfilMenuCheckSetIngredientAccordingly;
    JMenuItem itemfilMenuEditEdit;
    JButton editButton;
    String timeStamp = "";
    String currentTable = "ingredients_management";
    Connection con;
    String conInf;
    String account;
    String password;
    JFrame managementFrame;
    JFrame editIngreidentFrame;
    JTable dataTable;
    JFrame addRecordFrame;
    JButton addRecordFrameAddButton;
    JComboBox ingreidentsNamecomboBox;
    JPanel addRecordPanel;
    JButton ingreidentAddButton;
    JButton addButton;
    JButton delButton;
    JButton refreshButton;
    String[] ingredientToAddArray = new String[150];
    Double[] ingredientToAddArrayAmounts = new Double[150];
    JLabel[] ingredientToAddArrayLabel = new JLabel[150];
    JTextField[] ingredientToAddArrayTextfield = new JTextField[150];
    int ingredientToAddArrayCounter = 0;
    int id_eventInt = 0;
    int columnCount = 0;
    JTextField eventTextField;
    JFrame chooseTableFrame;
    int id_foodInt;
    JTextField food_typeTextField;
    JTextField food_nameTextField;
    JTextField priceTextField;
    String originfood_type;
    String originfood_name;
    String origintime_added;
    String originprice;
    String originAmount;
    String setTableAmount;
    int biggestTableNumber;
    String[] allIngredientNameArray;
    int allIngredientNumber;
    Double[] allIngredientAmountArray;
    Double[] ingredientNegAmountArray;
    String[] ingredientsNameArray;
    int rowCount;
    JFrame setChargeFrame;
    JTextField discountTextField;
    JTextField serviceChargeTextField;
    JButton setChargeButton;
    int gettedServiceCharge;
    int gettedDiscount;
    JPanel editRecordPanel;
    JButton ingreidentEditButton;
    JTextField ingreidentEditNameTextfield;
    //addIngredientColumnAccordingTofood_list()
    
    //Boolean isFinish = false;

    ClientManagement(String tmepConInf, String tempAccount, String tempPassword){
        conInf = tmepConInf;
        account = tempAccount;
        password = tempPassword;

        
        managementFrame = new JFrame(); 
        managementFrame.setSize(700,700);
        managementFrame.setMinimumSize(new Dimension(900,800));
        managementFrame.setLayout(new GridBagLayout());  
        managementFrame.setTitle("Management Client: ingredients_management");
        managementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        managementFrame.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        managementFrame.setJMenuBar(menuBar);

        JMenu filMenuDatabase = new JMenu("Database");
        menuBar.add(filMenuDatabase);
        JMenu filMenuCheck = new JMenu("Check and set");
        menuBar.add(filMenuCheck);
        JMenu filMenuEdit = new JMenu("edit");
        menuBar.add(filMenuEdit);
        JMenu filMenuOthers = new JMenu("Others");
        menuBar.add(filMenuOthers);

        itemfilMenuDatabaseChooseTable = new JMenuItem("Choose table");
        itemfilMenuDatabaseChooseTable.addActionListener(this);
        itemfilMenuDatabaseChooseTable.setActionCommand("chooseTable");
        filMenuDatabase.add(itemfilMenuDatabaseChooseTable);

        itemfilMenuDatabaseSummizeUsed = new JMenuItem("Summize used ingredient into ingredients_management and update ingredient_temporary");
        itemfilMenuDatabaseSummizeUsed.addActionListener(this);
        itemfilMenuDatabaseSummizeUsed.setActionCommand("sumStock");
        filMenuDatabase.add(itemfilMenuDatabaseSummizeUsed);

        itemfilMenuCheckSetTableAmount = new JMenuItem("Set Table Numbers");
        itemfilMenuCheckSetTableAmount.addActionListener(this);
        itemfilMenuCheckSetTableAmount.setActionCommand("setTable");
        filMenuCheck.add(itemfilMenuCheckSetTableAmount);

        itemfilMenuCheckSetIngredientAccordingly = new JMenuItem("Add missed ingredient in ingredient_temporary and ingredients_management according to food_list");
        itemfilMenuCheckSetIngredientAccordingly.addActionListener(this);
        itemfilMenuCheckSetIngredientAccordingly.setActionCommand("addMissedColumn");
        filMenuCheck.add(itemfilMenuCheckSetIngredientAccordingly);

        itemfilMenuCheckCurrentIngredients = new JMenuItem("Current ingredient stock in ingredients_management (add at last row without involving database)");
        itemfilMenuCheckCurrentIngredients.addActionListener(this);
        itemfilMenuCheckCurrentIngredients.setActionCommand("currentStock");
        filMenuCheck.add(itemfilMenuCheckCurrentIngredients);

        itemfilMenuSetCharge = new JMenuItem("Set service charge and discount");
        itemfilMenuSetCharge.addActionListener(this);
        itemfilMenuSetCharge.setActionCommand("setCharge");
        filMenuCheck.add(itemfilMenuSetCharge);

        itemfilMenuEditEdit = new JMenuItem("Edit ingredient name base on food_list (One ingredient pre-use)");
        itemfilMenuEditEdit.addActionListener(this);
        itemfilMenuEditEdit.setActionCommand("editIngredientName");
        filMenuEdit.add(itemfilMenuEditEdit);

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
        gbc.gridwidth = 2;
        gbc.fill = gbc.BOTH;
        managementFrame.add(timePanel, gbc);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        //tablePanel.setBackground(Color.black);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 9.0;
        gbc.weighty = 2.0;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;
        managementFrame.add(tablePanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1,1,55));
        //buttonPanel.setBackground(Color.red);
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 2.0;
        gbc.fill = gbc.BOTH;
        gbc.gridwidth = 1;
        managementFrame.add(buttonPanel, gbc);

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

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        dataTable.setFont(DefaultFontSetting);
        dataTable.setRowHeight(30);
        JTableHeader dataTableHeader = dataTable.getTableHeader();
        dataTableHeader.setFont(DefaultFontSetting);

        JScrollPane jps = new JScrollPane(dataTable);
        //jps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        //jps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        tablePanel.add(jps, gbc);

        
        refreshTable(currentTable);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        addButton.setFont(DefaultFontSetting);

        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        editButton.setActionCommand("edit");
        editButton.setFont(DefaultFontSetting);
        editButton.setEnabled(false);

        delButton = new JButton("Delete");
        delButton.addActionListener(this);
        delButton.setActionCommand("delete");
        delButton.setFont(DefaultFontSetting);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        refreshButton.setActionCommand("refresh");
        refreshButton.setFont(DefaultFontSetting);

        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(delButton);
    
        managementFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        //below is chooseTable itembar item related
        if(s == "chooseTable"){
            managementFrame.setEnabled(false);
            chooseCurrentTable();
        }
        if(s == "choosefood_list"){
            currentTable = "food_list";
            managementFrame.setTitle("Management Client: food_list");
            chooseTableFrame.dispose();
            editButton.setEnabled(true);
            addButton.setEnabled(true);
            delButton.setEnabled(true);
            managementFrame.setEnabled(true);
            refreshTable(currentTable);
        }
        if(s == "chooseingredients_management"){
            currentTable = "ingredients_management";
            managementFrame.setTitle("Management Client: ingredients_management");
            chooseTableFrame.dispose();
            managementFrame.setEnabled(true);
            editButton.setEnabled(false);
            addButton.setEnabled(true);
            delButton.setEnabled(true);
            refreshTable(currentTable);
        }
        if(s == "chooseorder_record"){
            currentTable = "order_record";
            managementFrame.setTitle("Management Client: customer_status");
            chooseTableFrame.dispose();
            managementFrame.setEnabled(true);
            editButton.setEnabled(false);
            addButton.setEnabled(false);
            delButton.setEnabled(false);
            refreshTable(currentTable);
        }

        //below is addMissedColumn itembar item related
        if(s == "addMissedColumn"){
            addIngredientColumnAccordingTofood_list();
        }

        //below is sumStock itembar item related
        if(s == "sumStock"){
            //refreshTable(currentTable);
            sumTemporaryIngredientsToManagementTable();      
        
        }

        //below is itemfilMenuEditEdit related
        if(s == "editIngredientName"){
            managementFrame.setEnabled(false);
            editIngredientName(); 
        }
        if(s == "ingreidentEditButton"){
            applyEditIngredientName();
        }

        //below is itembar item itemfilMenuSetCharge related
        if(s == "setCharge"){
            managementFrame.setEnabled(false);
            setCharge();
        }
        if(s == "setChargeButtonSet"){
            setChargeButtonSet();
        }
        

        //below is sumTable itembar item related
        if(s == "setTable"){
            setTableAmount = JOptionPane.showInputDialog(null, "Set Table number, enter int only, existed table will not be deleted.");

            if(setTableAmount == null){
                return;
            }

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");  
                con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                Statement stmt = con.createStatement();

                int setTableAmountint = Integer.parseInt(setTableAmount);

                for(int i = 1; i<setTableAmountint+1 ;i++){
                    stmt.execute(
                        "CREATE TABLE IF NOT EXISTS `restaurant`.`table_" +
                        i +
                        "`"+ 
                        " (" +
                        "  `id_order` INT NOT NULL DEFAULT -9,"+
                        "  `food_name` VARCHAR(200) NULL DEFAULT 'nofood',"+
                        "  `amount` INT NULL DEFAULT 0,"+
                        "  `summed_price` DOUBLE NULL DEFAULT 0,"+
                        "  `ismaking` INT NULL DEFAULT 0,"+
                        "  `isserved` INT NULL DEFAULT 0,"+
                        "  `iscancel` INT NULL DEFAULT 0,"+
                        "  PRIMARY KEY (`id_order`));"
                    );
                }

                
                ResultSet rs = stmt.executeQuery("SELECT * FROM customer_status ORDER BY customer_table DESC LIMIT 1;");
                if(rs.next()){
                    biggestTableNumber = rs.getInt(1);
                    
                }
                
                for(int i = biggestTableNumber+1; i < setTableAmountint+1;i++){
                    stmt.executeUpdate("INSERT INTO customer_status VALUES();");
                }

                stmt.close();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //below is currentStock itembar item related
        if(s == "currentStock"){
            if(currentTable == "ingredients_management"){
            allIngredientNumber = 0;
            refreshTable(currentTable);
            sumIngredients();
            }
            else{
                JOptionPane.showMessageDialog(null, "This function can only be used when ingredients_management is selected.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        //below is exit itembar item related
        if(s == "exit"){
            System.exit(0);
        }
        //below is add button related
        if(s == "add"){
            if(currentTable == "ingredients_management"){
                managementFrame.setEnabled(false);
                ingredientToAddArrayCounter = 0;
                ingredientToAddArray = new String[150];
                ingredientToAddArrayAmounts = new Double[150];
                ingredientToAddArrayLabel = new JLabel[150];
                ingredientToAddArrayTextfield = new JTextField[150];
                id_eventInt = 0;
                addNewRecordIngreident();
            }
            if(currentTable == "food_list"){
                managementFrame.setEnabled(false);
                ingredientToAddArrayCounter = 0;
                ingredientToAddArray = new String[150];
                ingredientToAddArrayAmounts = new Double[150];
                ingredientToAddArrayLabel = new JLabel[150];
                ingredientToAddArrayTextfield = new JTextField[150];
                id_foodInt = 0;
                addNewRecordFood();
            }  
        }
        if(s == "addConfirm"){
            if(currentTable == "ingredients_management"){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();
                    
                    
                    for(int i =0; i<ingredientToAddArrayCounter; i++){
                        ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM " + currentTable + " LIKE " + "'" + ingredientToAddArray[i] + "'" + ";");
                        if(rs.next()){
                            continue;
                        }
                        else{
                            stmt.executeUpdate("ALTER TABLE "+ currentTable + " ADD COLUMN `"+ ingredientToAddArray[i] +"` DOUBLE NULL DEFAULT '0';");
                        }
                    }

                    stmt.executeUpdate("INSERT INTO "+ currentTable +" VALUES();");  //this add new row and auto fill event_id 

                    //event_id, eventToAdd, timeNow, ingredientToAddArrayAmounts[i]<-- last can be a lot
                    //below check invalid input, if invalid, give warning anf point out 1st invalid value, 
                    //then get valid value to ingredientToAddArrayAmounts[i]
                    for(int i =0; i<ingredientToAddArrayCounter; i++){

                        try {
                            Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                        } catch (NumberFormatException ex) {
                            String warningDialog ="First invalid input detected at: " + ingredientToAddArray[i];
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        ingredientToAddArrayAmounts[i] = Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                    }   
                    if(eventTextField.getText().isEmpty()){
                        String warningDialog ="First invalid input detected at: " + "event";
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    
                    //below update event value for new record
                    String eventToAdd =  eventTextField.getText();
                    stmt.executeUpdate("UPDATE " + currentTable + " SET event= '" + eventToAdd + 
                    "' WHERE id_event = '" + id_eventInt +"';");

                    //below update time_added for new record
                    String timeNow = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
                    stmt.executeUpdate("UPDATE " + currentTable + " SET time_added= '" + timeNow + 
                    "' WHERE id_event = '" + id_eventInt +"';");

                    for(int i =0; i<ingredientToAddArrayCounter; i++){
                        stmt.executeUpdate("UPDATE " + currentTable + " SET " + ingredientToAddArray[i] + "= '" + ingredientToAddArrayAmounts[i] + 
                        "' WHERE id_event = '" + id_eventInt +"';");
                    }
        
                    stmt.close();
        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(currentTable == "food_list"){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();
                    
                    
                    for(int i =0; i<ingredientToAddArrayCounter; i++){
                        ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM " + currentTable + " LIKE " + "'" + ingredientToAddArray[i] + "'" + ";");
                        if(rs.next()){
                            continue;
                        }
                        else{
                            stmt.executeUpdate("ALTER TABLE "+ currentTable + " ADD COLUMN "+ ingredientToAddArray[i] +" DOUBLE NULL DEFAULT '0';");
                        }
                    }

                    stmt.executeUpdate("INSERT INTO "+ currentTable +" VALUES();");  //this add new row and auto fill id_food 

                    //id_food, food_type, food_name, time_added, time_last_modified, price, ingredientToAddArrayAmounts[i]<-- last can be a lot
                    //below check invalid input, if invalid, give warning and point out 1st invalid value, 
                    //then get valid value to ingredientToAddArrayAmounts[i]
                    for(int i =0; i<ingredientToAddArrayCounter; i++){

                        try {
                            Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                        } catch (NumberFormatException ex) {
                            String warningDialog ="First invalid input detected at: " + ingredientToAddArray[i];
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        ingredientToAddArrayAmounts[i] = Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                    }   
                    if(food_typeTextField.getText().isEmpty()){
                        String warningDialog ="First invalid input detected at: " + "food_type";
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    if(food_nameTextField.getText().isEmpty()){
                        String warningDialog ="First invalid input detected at: " + "food_name";
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    if(priceTextField.getText().isEmpty()){
                        String warningDialog ="First invalid input detected at: " + "price";
                            JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    try{
                        Double.valueOf(priceTextField.getText());
                    }
                    catch(Exception ex){
                        String warningDialog ="Invalid input detected at: " + "price";
                        JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    //below update food_type value for new record
                    String food_typeToAdd =  food_typeTextField.getText();
                    stmt.executeUpdate("UPDATE " + currentTable + " SET food_type= '" + food_typeToAdd + 
                    "' WHERE id_food = '" + id_foodInt +"';");

                    //below update food_name value for new record
                    String food_nameToAdd =  food_nameTextField.getText();
                    stmt.executeUpdate("UPDATE " + currentTable + " SET food_name= '" + food_nameToAdd + 
                    "' WHERE id_food = '" + id_foodInt +"';");

                    //below update time_added for new record
                    String timeNow = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
                    stmt.executeUpdate("UPDATE " + currentTable + " SET time_added= '" + timeNow + 
                    "' WHERE id_food = '" + id_foodInt +"';");

                    //below update time_last_modified for new record
                    stmt.executeUpdate("UPDATE " + currentTable + " SET time_last_modified= '" + timeNow + 
                    "' WHERE id_food = '" + id_foodInt +"';");

                    //below update price value for new record
                    String priceToAdd = priceTextField.getText();
                    stmt.executeUpdate("UPDATE " + currentTable + " SET price= '" + priceToAdd + 
                    "' WHERE id_food = '" + id_foodInt +"';");

                    //below is rest of ingredient
                    for(int i =0; i<ingredientToAddArrayCounter; i++){
                        stmt.executeUpdate("UPDATE " + currentTable + " SET " + ingredientToAddArray[i] + "= '" + ingredientToAddArrayAmounts[i] + 
                        "' WHERE id_food = '" + id_foodInt +"';");
                    }
        
                    stmt.close();
        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            managementFrame.setEnabled(true);
            addRecordFrame.dispose();
            refreshTable(currentTable);

        }
        if(s == "ingreidentAddButton"){   //"+" button
            //max amount is 150 pair, if exist, first if block it
            if(ingredientToAddArrayCounter >= 150){
                JOptionPane.showMessageDialog(null, "Cannot add more than 150 ingreidents record at once, add new record to do so.", 
                "Cannot add more than 150 ingreidents record at once", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            addRecordPanel.remove(ingreidentsNamecomboBox);
            addRecordPanel.remove(ingreidentAddButton);
            addRecordPanel.repaint();

            ingredientToAddArrayCounter += 1;

            if(String.valueOf(ingreidentsNamecomboBox.getSelectedItem()) == ">new<"){
                // adding type of ingreident in pair of label and textfield 
                String newIngredientName = JOptionPane.showInputDialog("Please enter new ingredient name");
                //if cancel or empty or start with number or have space
                if(newIngredientName == null){
                    addRecordPanel.add(ingreidentsNamecomboBox);
                    addRecordPanel.add(ingreidentAddButton);
                    ingredientToAddArrayCounter -= 1;
                    addRecordPanel.repaint();
                    addRecordPanel.doLayout();
                    JOptionPane.showMessageDialog(null, "Null input", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(newIngredientName.isEmpty()){
                    addRecordPanel.add(ingreidentsNamecomboBox);
                    addRecordPanel.add(ingreidentAddButton);
                    ingredientToAddArrayCounter -= 1;
                    addRecordPanel.repaint();
                    addRecordPanel.doLayout();
                    JOptionPane.showMessageDialog(null, "Empty input", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(newIngredientName.contains(" ")){
                    addRecordPanel.add(ingreidentsNamecomboBox);
                    addRecordPanel.add(ingreidentAddButton);
                    ingredientToAddArrayCounter -= 1;
                    addRecordPanel.repaint();
                    addRecordPanel.doLayout();
                    JOptionPane.showMessageDialog(null, "New ingredient name cannot contain space", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(Character.isLetter(newIngredientName.charAt(0)) == false){
                    addRecordPanel.add(ingreidentsNamecomboBox);
                    addRecordPanel.add(ingreidentAddButton);
                    ingredientToAddArrayCounter -= 1;
                    newIngredientName = "";
                    addRecordPanel.repaint();
                    JOptionPane.showMessageDialog(null, "New ingredient name cannot start with number", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                
                else{
                    ingredientToAddArray[ingredientToAddArrayCounter-1] = newIngredientName;
                
                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1] = 
                    new JLabel(newIngredientName + ": ");

                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setOpaque(true);;
                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setBackground(new Color(255,231,166));
                    addRecordPanel.add(ingredientToAddArrayLabel[ingredientToAddArrayCounter-1]);

                    ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1] = new JTextField("       ");
                    ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                    addRecordPanel.add(ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1]);

                    addRecordPanel.add(ingreidentsNamecomboBox);
                    addRecordPanel.add(ingreidentAddButton);
                }
            }
            else{
                // adding existing ingreident in pair of label and textfield 
                
                ingredientToAddArray[ingredientToAddArrayCounter-1] = String.valueOf(ingreidentsNamecomboBox.getSelectedItem());
                
                ingredientToAddArrayLabel[ingredientToAddArrayCounter-1] = 
                new JLabel(String.valueOf(ingreidentsNamecomboBox.getSelectedItem()) + ": ");

                ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setOpaque(true);;
                ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setBackground(new Color(255,231,166));
                addRecordPanel.add(ingredientToAddArrayLabel[ingredientToAddArrayCounter-1]);

                ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1] = new JTextField("       ");
                ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                addRecordPanel.add(ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1]);

                addRecordPanel.add(ingreidentsNamecomboBox);
                ingreidentsNamecomboBox.removeItem(String.valueOf(ingreidentsNamecomboBox.getSelectedItem()));
                addRecordPanel.add(ingreidentAddButton);
            }
            
            addRecordPanel.doLayout();
        }

        //below is edit button related
        if(s == "edit"){
            if(currentTable == "ingredients_management"){
                JOptionPane.showMessageDialog(null, "Edit not allow in ingredients_management for better management, please use delete or add only", 
                "Edit not allow in ingredients_management", JOptionPane.WARNING_MESSAGE);
            }
            if(currentTable == "food_list"){
                int row = dataTable.getSelectedRow();
                
                if(row == -1){
                    JOptionPane.showMessageDialog(null, "Please select row to edit",
                    "No row selected", JOptionPane.WARNING_MESSAGE); 
                    return;
                }
                managementFrame.setEnabled(false);
                ingredientToAddArrayCounter = 0;
                ingredientToAddArray = new String[150];
                ingredientToAddArrayAmounts = new Double[150];
                ingredientToAddArrayLabel = new JLabel[150];
                ingredientToAddArrayTextfield = new JTextField[150];
                id_foodInt = 0;
                editRecordFood();
            }
        }
        if(s == "editConfirm"){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");  
                con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                Statement stmt = con.createStatement();

                //id_food, food_type, food_name, time_added, time_last_modified, price, ingredientToAddArrayAmounts[i]<-- last can be a lot
                //below check invalid input, if invalid, give warning and point out 1st invalid value, 
                //then get valid value to ingredientToAddArrayAmounts[i]
                for(int i =0; i<ingredientToAddArrayCounter; i++){

                    try {
                        Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                    } catch (NumberFormatException ex) {
                        String warningDialog ="First invalid input detected at: " + ingredientToAddArray[i];
                        JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    ingredientToAddArrayAmounts[i] = Double.parseDouble(ingredientToAddArrayTextfield[i].getText());
                }   
                if(food_typeTextField.getText().isEmpty()){
                    String warningDialog ="First invalid input detected at: " + "event";
                        JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                }
                if(food_nameTextField.getText().isEmpty()){
                    String warningDialog ="First invalid input detected at: " + "event";
                        JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                }
                if(priceTextField.getText().isEmpty()){
                    String warningDialog ="First invalid input detected at: " + "price";
                        JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                }
                try{
                    Double.valueOf(priceTextField.getText());
                }
                catch(Exception ex){
                    String warningDialog ="Invalid input detected at: " + "price";
                    JOptionPane.showMessageDialog(null, warningDialog, "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                //below update food_type value for new record
                String food_typeToAdd =  food_typeTextField.getText();
                stmt.executeUpdate("UPDATE " + currentTable + " SET food_type= '" + food_typeToAdd + 
                "' WHERE id_food = '" + id_foodInt +"';");

                //below update food_name value for new record
                String food_nameToAdd =  food_nameTextField.getText();
                stmt.executeUpdate("UPDATE " + currentTable + " SET food_name= '" + food_nameToAdd + 
                "' WHERE id_food = '" + id_foodInt +"';");

                String timeNow = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
                //below update time_last_modified for new record
                stmt.executeUpdate("UPDATE " + currentTable + " SET time_last_modified= '" + timeNow + 
                "' WHERE id_food = '" + id_foodInt +"';");

                //below update price value for new record
                String priceToAdd = priceTextField.getText();
                stmt.executeUpdate("UPDATE " + currentTable + " SET price= '" + priceToAdd + 
                "' WHERE id_food = '" + id_foodInt +"';");

                //below is rest of ingredient
                for(int i =0; i<ingredientToAddArrayCounter; i++){
                    stmt.executeUpdate("UPDATE " + currentTable + " SET " + ingredientToAddArray[i] + "= '" + ingredientToAddArrayAmounts[i] + 
                    "' WHERE id_food = '" + id_foodInt +"';");
                }
    
                stmt.close();
    
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            }

            managementFrame.setEnabled(true);
            addRecordFrame.dispose();
            refreshTable(currentTable);
        }
        if(s == "editIngreidentAddButton"){   //"+" button on edit
            //max amount is 150 pair, if exist, first if block it
            if(ingredientToAddArrayCounter >= 150){
                JOptionPane.showMessageDialog(null, "Cannot edit more than 150 ingreidents record at once, use another edit to do so.", 
                "Cannot edit more than 150 ingreidents record at once", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            addRecordPanel.remove(ingreidentsNamecomboBox);
            addRecordPanel.remove(ingreidentAddButton);
            addRecordPanel.repaint();

            ingredientToAddArrayCounter += 1;

                // adding existing ingreident in pair of label and textfield 
                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                    Statement stmt = con.createStatement();
                    //Statement stmt2 = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentTable + ";");
                    ResultSetMetaData rsmd = rs.getMetaData();
                    columnCount = rsmd.getColumnCount();
                    
                    ingredientToAddArray[ingredientToAddArrayCounter-1] = String.valueOf(ingreidentsNamecomboBox.getSelectedItem());
                    String tamp_columString =ingredientToAddArray[ingredientToAddArrayCounter-1];
                    rs = stmt.executeQuery("SELECT "+ tamp_columString +" FROM " + currentTable + " WHERE id_food = " + id_foodInt + ";");
                    if(rs.next()){
                        originAmount = rs.getString(1);
                    }
                    

                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1] = 
                    new JLabel(String.valueOf(ingreidentsNamecomboBox.getSelectedItem()) + ": ");

                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setOpaque(true);;
                    ingredientToAddArrayLabel[ingredientToAddArrayCounter-1].setBackground(new Color(255,231,166));
                    addRecordPanel.add(ingredientToAddArrayLabel[ingredientToAddArrayCounter-1]);

                    ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1] = new JTextField(originAmount);
                    ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1].setFont(DefaultFontSetting);
                    addRecordPanel.add(ingredientToAddArrayTextfield[ingredientToAddArrayCounter-1]);

                    addRecordPanel.add(ingreidentsNamecomboBox);
                    ingreidentsNamecomboBox.removeItem(String.valueOf(ingreidentsNamecomboBox.getSelectedItem()));
                    addRecordPanel.add(ingreidentAddButton);
        
                    stmt.close();
        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
            addRecordPanel.doLayout();
        }

        //below is delete button related
        if(s == "delete"){
            int row = dataTable.getSelectedRow();
            String rowToDeleteId = (String) dataTable.getModel().getValueAt(row, 0);
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");  
                con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
                Statement stmt = con.createStatement();
                //Statement stmt2 = con.createStatement();

                if(currentTable == "ingredients_management"){
                    stmt.executeUpdate("DELETE FROM " + currentTable + " WHERE id_event =" +  Integer.valueOf(rowToDeleteId) + ";");
                    ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + currentTable + " ORDER BY id_event DESC LIMIT 1;");
                    //for adjusting the auto increment value according to last record
                    if(rs2.next()){
                        id_eventInt = rs2.getInt(1);
                        stmt.executeUpdate("ALTER TABLE "+ currentTable +" AUTO_INCREMENT = "+ id_eventInt +";");
                    }
                }
                if(currentTable == "food_list"){
                    stmt.executeUpdate("DELETE FROM " + currentTable + " WHERE id_food =" +  Integer.valueOf(rowToDeleteId) + ";");
                    ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + currentTable + " ORDER BY id_food DESC LIMIT 1;");
                    //for adjusting the auto increment value according to last record
                    if(rs2.next()){
                        id_foodInt = rs2.getInt(1);
                        stmt.executeUpdate("ALTER TABLE "+ currentTable +" AUTO_INCREMENT = "+ id_foodInt +";");
                    }
                }
                stmt.close();
    
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            refreshTable(currentTable);
        }

        //below is refresh button related
        if(s == "refresh"){
            refreshTable(currentTable);
        }

    }

    private void applyEditIngredientName() {
        String NameEdited = ingreidentEditNameTextfield.getText();
        String selectedToEdit = String.valueOf(ingreidentsNamecomboBox.getSelectedItem());

        //detect invalid input, return if detected
        if(NameEdited == null){
            JOptionPane.showMessageDialog(null, "Null input", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(NameEdited.isEmpty()){
            JOptionPane.showMessageDialog(null, "Empty input", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(NameEdited.contains(" ")){
            JOptionPane.showMessageDialog(null, "New ingredient name cannot contain space", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(Character.isLetter(NameEdited.charAt(0)) == false){
            JOptionPane.showMessageDialog(null, "New ingredient name cannot start with number", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            
            //update food_list, ingredient_temporary, ingredients_management, order_record for the edited ingredient name
            stmt.executeUpdate("ALTER TABLE `food_list` CHANGE COLUMN `" + selectedToEdit + "` `" + NameEdited + "` DOUBLE NULL DEFAULT '0' ;");
            stmt.executeUpdate("ALTER TABLE `ingredient_temporary` CHANGE COLUMN `" + selectedToEdit + "` `" + NameEdited + "` DOUBLE NULL DEFAULT '0' ;");
            stmt.executeUpdate("ALTER TABLE `ingredients_management` CHANGE COLUMN `" + selectedToEdit + "` `" + NameEdited + "` DOUBLE NULL DEFAULT '0' ;");
            
            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        editIngreidentFrame.dispose();
        managementFrame.setEnabled(true);
        refreshTable(currentTable);
    }

    private void editIngredientName() {

        editIngreidentFrame = new JFrame("Edit ingreident");
        editIngreidentFrame.setSize(400,250);
        editIngreidentFrame.setLayout(new GridLayout(3,1));
        editIngreidentFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            //Statement stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM food_list;");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();

            String[] ingreidentsName = new String[columnCount-6]; 

            //get column name which should be ingreidents, add them into ingreidentsName
            for(int i = 7; i < columnCount + 1; i++){
                ingreidentsName[i-7] = rsmd.getColumnName(i);
            }

            ingreidentsNamecomboBox = new JComboBox(ingreidentsName);
            ingreidentsNamecomboBox.setFont(DefaultFontSetting);
            editIngreidentFrame.add(ingreidentsNamecomboBox);

            ingreidentEditNameTextfield = new JTextField();
            ingreidentEditNameTextfield.setFont(DefaultFontSetting);
            ((AbstractDocument)ingreidentEditNameTextfield.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
            editIngreidentFrame.add(ingreidentEditNameTextfield);

            ingreidentEditButton = new JButton("Edit");
            ingreidentEditButton.setFont(DefaultFontSetting);
            ingreidentEditButton.addActionListener(this);
            ingreidentEditButton.setActionCommand("ingreidentEditButton");
            editIngreidentFrame.add(ingreidentEditButton);

            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        editIngreidentFrame.setVisible(true);
    }

    private void setChargeButtonSet() {
        String newServiceCharge = serviceChargeTextField.getText();
        String newDiscount = discountTextField.getText();

        //make change to charge_discount
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE charge_discount SET service_charge_percentage = " + newServiceCharge + " WHERE id=0;");
            stmt.executeUpdate("UPDATE charge_discount SET discount_off_percentage = " + newDiscount + " WHERE id=0;");

            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        setChargeFrame.dispose();
        managementFrame.setEnabled(true);
    }

    private void setCharge() {
        setChargeFrame = new JFrame();
        setChargeFrame = new JFrame("Set charge and discount");
        setChargeFrame.setSize(500,130);
        setChargeFrame.setLayout(new FlowLayout());
        setChargeFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });


        //get discount and service charge percentage in charge_discount, which have one row only id=0
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  

            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM charge_discount WHERE id=0");
            while(rs.next()){
                gettedServiceCharge = rs.getInt(2);
                gettedDiscount = rs.getInt(3);
            }

            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }


        JLabel discountLabel = new JLabel("Discount %: ");
        discountLabel.setFont(DefaultFontSetting);
        setChargeFrame.add(discountLabel);

        String StringServiceCharge = String.valueOf(gettedServiceCharge);
        int StringServiceChargeLenght = StringServiceCharge.length();
        String StringDiscount = String.valueOf(gettedDiscount);
        int StringDiscountLenght = StringDiscount.length();

        //StringServiceCharge StringDiscount must be 3 in lenght to fill the space;
        if(StringServiceChargeLenght == 1){
            StringServiceCharge = StringServiceCharge + "  ";
        }
        if(StringServiceChargeLenght == 2){
            StringServiceCharge = StringServiceCharge + " ";
        }

        if(StringDiscountLenght == 1){
            StringDiscount = StringDiscount + "  ";
        }
        if(StringDiscountLenght == 2){
            StringDiscount = StringDiscount + " ";
        }

        //limit 3 character
        discountTextField = new JTextField(StringDiscount);
        ((AbstractDocument)discountTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(3));
        discountTextField.setFont(DefaultFontSetting);
        setChargeFrame.add(discountTextField);


        JLabel serviceChargeLabel = new JLabel("Service charge %: ");
        serviceChargeLabel.setFont(DefaultFontSetting);
        setChargeFrame.add(serviceChargeLabel);

        serviceChargeTextField = new JTextField(StringServiceCharge);
        ((AbstractDocument)serviceChargeTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(3));
        serviceChargeTextField.setFont(DefaultFontSetting);
        setChargeFrame.add(serviceChargeTextField);

        setChargeButton = new JButton("SET");
        setChargeButton.addActionListener(this);
        setChargeButton.setActionCommand("setChargeButtonSet");
        setChargeButton.setFont(DefaultFontSetting);
        setChargeFrame.add(setChargeButton);

        setChargeFrame.setVisible(true);
    }

    private void sumTemporaryIngredientsToManagementTable() {
        // id_order -1 to storage all ingredient value, no need delete
        //There is no AUTO_INCREMENT in ingredient_temporary, id_order is added according to order_record
        //Used ingreident value will be added after kitchen click make button

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            //System.out.println(conInf+account+password); 
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ingredient_temporary;");
            ResultSetMetaData rsmd = rs.getMetaData();

            int ingredientnumber = rsmd.getColumnCount() - 2;

            ingredientsNameArray = new String[ingredientnumber];
            ingredientNegAmountArray = new Double[ingredientnumber];
            
            for(int i = 3; i < ingredientnumber+3; i++){   //get ingredient name in ingredient_temporary
                ingredientsNameArray[i-3] = rsmd.getColumnName(i);
            }

            for(int i = 0; i < ingredientnumber; i++){
                //No need id_order -1, if ismaking is 0, not need to get value, as no ingreident used
                ResultSet rs2 = stmt.executeQuery("SELECT " + ingredientsNameArray[i] + " FROM ingredient_temporary WHERE id_order != '-1' AND ismaking!=0;");
                //initialize ingredientsAmountsArray[] to 0.0 each
                ingredientNegAmountArray[i] = 0.0;
                while(rs2.next()){
                    ingredientNegAmountArray[i] += rs2.getDouble(1);
                }
            }

            //below is to clean getted used amounts of ingredient row(S) in ingredient_temporary
            //id_order -1 to storage all ingredient value, no need delete
            stmt.executeUpdate("DELETE FROM ingredient_temporary WHERE id_order !=-1 AND ismaking!=0;");


            stmt.executeUpdate("INSERT INTO ingredients_management VALUES();");  //this add new row to ingredients_management
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM ingredients_management ORDER BY id_event DESC LIMIT 1;");
            if(rs3.next()){
                id_eventInt = rs3.getInt(1);
            }


            //add event
            stmt.executeUpdate("UPDATE ingredients_management SET event='" + "sum Up ingredient used in ingredient_temporary and upgrade it" + 
                    "' WHERE id_event = " + id_eventInt + ";");   

            //add time_added
            String timeNow = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
            stmt.executeUpdate("UPDATE ingredients_management SET time_added = '" + timeNow + "' WHERE id_event = '" + id_eventInt + "';");

            //add -ve value into ingredients_management
            for(int i = 0; i < ingredientnumber; i++){
                stmt.executeUpdate("UPDATE ingredients_management SET " + ingredientsNameArray[i] + " = '" + ingredientNegAmountArray[i] + "' WHERE id_event = '" + id_eventInt + "';");
            }

            id_eventInt=0; // reset id_eventInt just incase 


            //Below is caculate and put total ingredient amount pre ingredient column into id_order =-1 in ingredient_temporary 
            //for waiter_client to use, just sumIngredients()
            //first refreshtable ingredients_management
            currentTable = "ingredients_management";
            refreshTable(currentTable);

            int columnCount = dataTable.getColumnCount();
            allIngredientNameArray = new String[columnCount-3];
            allIngredientAmountArray = new Double[columnCount-3];

            for(int i=0; i < columnCount-3;i++ ){
                allIngredientNameArray[i] = dataTable.getColumnName(i+3);  //3 is first ingredient column
                allIngredientNumber +=1;

                for(int n=0; n < dataTable.getRowCount();n++){
                    if(n==0){
                        allIngredientAmountArray[i] = 0.0;
                    }
                    Double temp = Double.valueOf(String.valueOf(dataTable.getModel().getValueAt(n, i+3)));
                    allIngredientAmountArray[i] = allIngredientAmountArray[i] + temp;
                }
            }

            for(int i = 0; i < columnCount-3; i++){
                stmt.executeUpdate("UPDATE ingredient_temporary SET " + allIngredientNameArray[i] + "=" + allIngredientAmountArray[i] + " WHERE id_order =-1");
            }

            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addIngredientColumnAccordingTofood_list(){
        /*addIngredientColumnAccordingTofood_list() add missed columnin in gredients_management and ingredient_temporary according to food_list
        //.getModel().getValueAt(row_index, col_index); .getRowCount().getColumnCount().getColumnName(columnIndex) --*/

        currentTable = "food_list";
        refreshTable(currentTable);
        allIngredientNumber = 0;

        int columnCount = dataTable.getColumnCount();
        allIngredientNameArray = new String[columnCount-6];

        for(int i=0; i < columnCount-6;i++ ){
            allIngredientNameArray[i] = dataTable.getColumnName(i+6);  //3 is first ingredient column
            allIngredientNumber +=1;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            //System.out.println(conInf+account+password); 
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();

            DatabaseMetaData md = con.getMetaData();
            //add missed column in ingredients_management and ingredient_temporary according to food_list
            for(String x: allIngredientNameArray) {
                ResultSet rs2 = md.getColumns(null, null, "ingredients_management", x);
                if (rs2.next() == false) {
                    stmt.executeUpdate("ALTER TABLE ingredients_management ADD COLUMN " + x + " Double NULL DEFAULT 0;");
                }
                
                ResultSet rs3 = md.getColumns(null, null, "ingredient_temporary", x);
                if (rs3.next() == false) {
                    stmt.executeUpdate("ALTER TABLE ingredient_temporary ADD COLUMN " + x + " Double NULL DEFAULT 0;");
                }  
            }

            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        currentTable = "ingredients_management";
        managementFrame.setTitle("Management Client: ingredients_management");
        refreshTable(currentTable);
    }

    private void sumIngredients() {
        //.getModel().getValueAt(row_index, col_index); .getRowCount().getColumnCount().getColumnName(columnIndex)

        int columnCount = dataTable.getColumnCount();
        allIngredientNameArray = new String[columnCount-3];
        allIngredientAmountArray = new Double[columnCount-3];

        for(int i=0; i < columnCount-3;i++ ){
            allIngredientNameArray[i] = dataTable.getColumnName(i+3);  //3 is first ingredient column
            allIngredientNumber +=1;

            for(int n=0; n < dataTable.getRowCount();n++){
                if(n==0){
                    allIngredientAmountArray[i] = 0.0;
                }
                Double temp = Double.valueOf(String.valueOf(dataTable.getModel().getValueAt(n, i+3)));
                allIngredientAmountArray[i] = allIngredientAmountArray[i] + temp;
            }
        }

        Double[] newRowAllIngredientAmountArray = new Double[columnCount];
        //first 3 column just 0
        newRowAllIngredientAmountArray[0] = 0.0;    
        newRowAllIngredientAmountArray[1] = 0.0;
        newRowAllIngredientAmountArray[2] = 0.0;

        for(int i = 3; i < columnCount; i++){
            newRowAllIngredientAmountArray[i] = allIngredientAmountArray[i-3];
        }

        tableModel.addRow(newRowAllIngredientAmountArray);

    }

    private void editRecordFood() {
        addRecordFrame = new JFrame("Edit value(s)");
        addRecordFrame.setSize(1000,350);
        addRecordFrame.setLayout(new GridBagLayout());
        addRecordFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });

        addRecordPanel = new JPanel();
        //addRecordPanel.setLayout(new FlowLayout());

        JPanel ingreidentAddButtonPanel = new JPanel();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            //Statement stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentTable + ";");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
            
            String[] firstSixColumnName = new String[6];
            JLabel[] firstSixColumnLabels = new JLabel[6];
            String[] ingreidentsName = new String[columnCount-6]; //no >new< so -5
            id_foodInt = 0;
            
            
            //get first 6 column name id_food, food_type, food_name, time_added, time_last_modified and price add them into firstFiveColumnName
            for(int i = 1; i < 7; i++){
                firstSixColumnName[i-1] =  rsmd.getColumnName(i);
            }

            //get rest of column name, which should be ingreidents, add them into ingreidents
            for(int i = 7; i < columnCount + 1; i++){
                ingreidentsName[i-7] = rsmd.getColumnName(i);
            }

            int row = dataTable.getSelectedRow();
            id_foodInt = Integer.valueOf((String) dataTable.getModel().getValueAt(row, 0));

            rs = stmt.executeQuery("SELECT * FROM " + currentTable + " WHERE id_food = " + id_foodInt + ";");
            if(rs.next()){
                originfood_type = rs.getString(2);
                originfood_name = rs.getString(3);
                origintime_added = rs.getString(4);
                originprice = String.valueOf(rs.getDouble(6));

            }
            //loop to add first 6 pairs of label and textfield/label according to first five column
            for(int i = 0; i < 6; i++){
                firstSixColumnLabels[i] = new JLabel(firstSixColumnName[i] + ": ");
                firstSixColumnLabels[i].setFont(DefaultFontSetting);
                firstSixColumnLabels[i].setOpaque(true);
                firstSixColumnLabels[i].setBackground(new Color(255,231,166));
                addRecordPanel.add(firstSixColumnLabels[i]);
                if(i==0){
                    JLabel id_foodIntLabel = new JLabel(String.valueOf(id_foodInt));
                    id_foodIntLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(id_foodIntLabel);
                }
                if(i==1){
                    food_typeTextField = new JTextField(originfood_type);
                    ((AbstractDocument)food_typeTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    food_typeTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(food_typeTextField);
                }
                if(i==2){
                    food_nameTextField = new JTextField(originfood_name);
                    ((AbstractDocument)food_nameTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    food_nameTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(food_nameTextField);
                }
                if(i==3){
                    JLabel time_addedLabel = new JLabel(origintime_added);
                    time_addedLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(time_addedLabel);
                }
                if(i==4){
                    JLabel time_editedLabel = new JLabel("System time ");
                    time_editedLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(time_editedLabel);
                }
                if(i==5){
                    priceTextField = new JTextField(originprice);
                    ((AbstractDocument)priceTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    priceTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(priceTextField);
                }
            }
            
            ingreidentsNamecomboBox = new JComboBox(ingreidentsName);
            ingreidentsNamecomboBox.setFont(DefaultFontSetting);
            addRecordPanel.add(ingreidentsNamecomboBox);

            ingreidentAddButton = new JButton("+");
            ingreidentAddButton.setFont(DefaultFontSetting);
            ingreidentAddButton.addActionListener(this);
            ingreidentAddButton.setActionCommand("editIngreidentAddButton");
            addRecordPanel.add(ingreidentAddButton);


            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addRecordFrameAddButton = new JButton("Edit");
        addRecordFrameAddButton.setFont(DefaultFontSetting);
        addRecordFrameAddButton.addActionListener(this);
        addRecordFrameAddButton.setActionCommand("editConfirm");
        ingreidentAddButtonPanel.add(addRecordFrameAddButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 10.0;
        gbc.fill = gbc.BOTH;

        addRecordFrame.add(addRecordPanel, gbc);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        addRecordFrame.add(ingreidentAddButtonPanel, gbc);

        addRecordFrame.setVisible(true);
    }

    private void addNewRecordFood() {
        addRecordFrame = new JFrame("Select value(s) to add");
        addRecordFrame.setSize(1000,350);
        addRecordFrame.setLayout(new GridBagLayout());
        addRecordFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });

        addRecordPanel = new JPanel();
        //addRecordPanel.setLayout(new FlowLayout());

        JPanel ingreidentAddButtonPanel = new JPanel();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            //Statement stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentTable + ";");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
            
            String[] firstSixColumnName = new String[6];
            JLabel[] firstSixColumnLabels = new JLabel[6];
            String[] ingreidentsName = new String[columnCount-5]; 
            id_foodInt = 0;
            
            
            //get first 5 column name id_food, food_type, food_name, time_added and time_last_modified, add them into firstFiveColumnName
            for(int i = 1; i < 7; i++){
                firstSixColumnName[i-1] =  rsmd.getColumnName(i);
            }

            //get rest of column name, which should be ingreidents, add them into ingreidents
            for(int i = 7; i < columnCount + 1; i++){
                ingreidentsName[i-7] = rsmd.getColumnName(i);
            }
            ingreidentsName[columnCount-6] = ">new<";

            tempCheckrs = 0;
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + currentTable + " ORDER BY id_food DESC LIMIT 1;");
            if(rs2.next()){
                id_foodInt = rs2.getInt(1);
                id_foodInt += 1;
                tempCheckrs += 1;
            }
            if(tempCheckrs == 0){  // if first row to add
                stmt.executeUpdate("ALTER TABLE " + currentTable + " AUTO_INCREMENT = 0;");
                id_foodInt = 1;
            }

            //loop to add first 6 pairs of label and textfield/label according to first six column
            for(int i = 0; i < 6; i++){
                firstSixColumnLabels[i] = new JLabel(firstSixColumnName[i] + ": ");
                firstSixColumnLabels[i].setFont(DefaultFontSetting);
                firstSixColumnLabels[i].setOpaque(true);
                firstSixColumnLabels[i].setBackground(new Color(255,231,166));
                addRecordPanel.add(firstSixColumnLabels[i]);
                if(i==0){
                    JLabel id_foodIntLabel = new JLabel(String.valueOf(id_foodInt));
                    id_foodIntLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(id_foodIntLabel);
                }
                if(i==1){
                    food_typeTextField = new JTextField("max 200 character(s)/symbol(s)   ");
                    ((AbstractDocument)food_typeTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    food_typeTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(food_typeTextField);
                }
                if(i==2){
                    food_nameTextField = new JTextField("max 200 character(s)/symbol(s)   ");
                    ((AbstractDocument)food_nameTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    food_nameTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(food_nameTextField);
                }
                if(i==3){
                    JLabel time_addedLabel = new JLabel("System time ");
                    time_addedLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(time_addedLabel);
                }
                if(i==4){
                    JLabel time_editedLabel = new JLabel("System time ");
                    time_editedLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(time_editedLabel);
                }
                if(i==5){
                    priceTextField = new JTextField("999.9");
                    ((AbstractDocument)priceTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    priceTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(priceTextField);
                }
            }
            
            ingreidentsNamecomboBox = new JComboBox(ingreidentsName);
            ingreidentsNamecomboBox.setFont(DefaultFontSetting);
            addRecordPanel.add(ingreidentsNamecomboBox);

            ingreidentAddButton = new JButton("+");
            ingreidentAddButton.setFont(DefaultFontSetting);
            ingreidentAddButton.addActionListener(this);
            ingreidentAddButton.setActionCommand("ingreidentAddButton");
            addRecordPanel.add(ingreidentAddButton);


            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addRecordFrameAddButton = new JButton("Add");
        addRecordFrameAddButton.setFont(DefaultFontSetting);
        addRecordFrameAddButton.addActionListener(this);
        addRecordFrameAddButton.setActionCommand("addConfirm");
        ingreidentAddButtonPanel.add(addRecordFrameAddButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 10.0;
        gbc.fill = gbc.BOTH;

        addRecordFrame.add(addRecordPanel, gbc);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        addRecordFrame.add(ingreidentAddButtonPanel, gbc);

        addRecordFrame.setVisible(true);
    }

    private void chooseCurrentTable() {
        chooseTableFrame = new JFrame("Choose table:");
        chooseTableFrame.setSize(300,170);
        chooseTableFrame.setLayout(new FlowLayout());
        chooseTableFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });

        JButton chooseTablefood_listButton = new JButton("food_list");
        chooseTablefood_listButton.setFont(DefaultFontSetting);
        chooseTablefood_listButton.addActionListener(this);
        chooseTablefood_listButton.setActionCommand("choosefood_list");
        JButton chooseTableingredients_managementButton = new JButton("ingredients_management");
        chooseTableingredients_managementButton.setFont(DefaultFontSetting);
        chooseTableingredients_managementButton.addActionListener(this);
        chooseTableingredients_managementButton.setActionCommand("chooseingredients_management");
        JButton chooseTableorder_recordButton = new JButton("order_record");
        chooseTableorder_recordButton.setFont(DefaultFontSetting);
        chooseTableorder_recordButton.addActionListener(this);
        chooseTableorder_recordButton.setActionCommand("chooseorder_record");

        chooseTableFrame.add(chooseTablefood_listButton);
        chooseTableFrame.add(chooseTableingredients_managementButton);
        chooseTableFrame.add(chooseTableorder_recordButton);
        chooseTableFrame.setVisible(true);
    }

    void addNewRecordIngreident() {

        //isFinish = false;

        addRecordFrame = new JFrame("Select value(s) to add");
        addRecordFrame.setSize(1000,350);
        addRecordFrame.setLayout(new GridBagLayout());
        addRecordFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                managementFrame.setEnabled(true);
            }
        });

        addRecordPanel = new JPanel();
        //addRecordPanel.setLayout(new FlowLayout());

        JPanel ingreidentAddButtonPanel = new JPanel();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            //Statement stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentTable + ";");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
            
            String[] firstThreeColumnName = new String[3];
            JLabel[] firstThreeColumnLabels = new JLabel[3];
            String[] ingreidentsName = new String[columnCount-2]; //last value is >new<, new ingredient, so -2, not -3
            id_eventInt = 0;
            
            
            //get first 3 column name id_event, event, and time_added, add them into firstThreeColumnName
            for(int i = 1; i < 4; i++){
                firstThreeColumnName[i-1] =  rsmd.getColumnName(i);
            }

            //get rest of column name, which should be ingreidents, add them into ingreidents
            for(int i = 4; i < columnCount + 1; i++){
                ingreidentsName[i-4] = rsmd.getColumnName(i);
            }
            ingreidentsName[columnCount-3] = ">new<";

            tempCheckrs = 0;
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + currentTable + " ORDER BY id_event DESC LIMIT 1;");
            if(rs2.next()){
                id_eventInt = rs2.getInt(1);
                id_eventInt += 1;
                tempCheckrs += 1; // check if rs2 have value
            }
            if(tempCheckrs == 0){
                stmt.executeUpdate("ALTER TABLE " + currentTable + " AUTO_INCREMENT = 0;");
                id_eventInt = 1;
            }

            //loop to add first 3 pairs of label and textfield/label according to first three column
            for(int i = 0; i < 3; i++){
                firstThreeColumnLabels[i] = new JLabel(firstThreeColumnName[i] + ": ");
                firstThreeColumnLabels[i].setFont(DefaultFontSetting);
                firstThreeColumnLabels[i].setOpaque(true);
                firstThreeColumnLabels[i].setBackground(new Color(255,231,166));
                addRecordPanel.add(firstThreeColumnLabels[i]);
                if(i==0){
                    JLabel id_eventLabel = new JLabel(String.valueOf(id_eventInt));
                    id_eventLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(id_eventLabel);
                }
                if(i==1){
                    eventTextField = new JTextField("max 200 character(s)/symbol(s)                      ");
                    ((AbstractDocument)eventTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
                    
                    //eventTextField.setDocument(new JTextFieldLimit(10));
                    eventTextField.setFont(DefaultFontSetting);
                    addRecordPanel.add(eventTextField);
                }
                if(i==2){
                    JLabel time_addedLabel = new JLabel("System time ");
                    time_addedLabel.setFont(DefaultFontSetting);
                    addRecordPanel.add(time_addedLabel);
                }
            }
            
            ingreidentsNamecomboBox = new JComboBox(ingreidentsName);
            ingreidentsNamecomboBox.setFont(DefaultFontSetting);
            addRecordPanel.add(ingreidentsNamecomboBox);

            ingreidentAddButton = new JButton("+");
            ingreidentAddButton.setFont(DefaultFontSetting);
            ingreidentAddButton.addActionListener(this);
            ingreidentAddButton.setActionCommand("ingreidentAddButton");
            addRecordPanel.add(ingreidentAddButton);


            stmt.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addRecordFrameAddButton = new JButton("Add");
        addRecordFrameAddButton.setFont(DefaultFontSetting);
        addRecordFrameAddButton.addActionListener(this);
        addRecordFrameAddButton.setActionCommand("addConfirm");
        ingreidentAddButtonPanel.add(addRecordFrameAddButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 10.0;
        gbc.fill = gbc.BOTH;

        addRecordFrame.add(addRecordPanel, gbc);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = gbc.BOTH;
        addRecordFrame.add(ingreidentAddButtonPanel, gbc);

        addRecordFrame.setVisible(true);
        
    }

    void refreshTable(String currentTable){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            //System.out.println(conInf+account+password); 
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentTable + ";");
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int columnCount = rsmd.getColumnCount();
            
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            for(int i=0; i<columnCount; i++){
                tableModel.addColumn(rsmd.getColumnName(i+1));
            }
            
            while(rs.next()){
                Object[] obj = new Object[columnCount];

                for(int i=0; i<columnCount; i++){
                    obj[i] = rs.getString(i+1);
                }
                tableModel.addRow(obj);
            }
            stmt.close();

            //set size for first 3 or 5 column only, as they will not change(3 for ingredients_management, 5 forfood_list)
            if(currentTable == "food_list"){
                for(int i=0; i<5; i++){
                    dataTable.getColumnModel().getColumn(i).setMinWidth(150);
                }
                dataTable.getColumnModel().getColumn(3).setMinWidth(220);
                dataTable.getColumnModel().getColumn(4).setMinWidth(220);
            }
            if(currentTable == "ingredients_management"){
                for(int i=0; i<3; i++){
                    dataTable.getColumnModel().getColumn(i).setMinWidth(150);
                }
            }
            if(currentTable == "order_record"){
                for(int i=0; i<13; i++){
                    dataTable.getColumnModel().getColumn(i).setMinWidth(150);
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}