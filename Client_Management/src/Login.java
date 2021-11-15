//customizable-restaurant-operating-system-with-Java-MySQL v1.0.1
//by lrex93497 @github
//Client_Management login v1.0.0
//release under GPLv2

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.logging.*;

import java.sql.*;

/*   number is emptyLabel
        0           1           2
        3       loginPanel    loginButtonPanel
        6           7           8    
*/

public class Login implements ActionListener {

    JFrame loginFrame;
    JTextField accountTextField;
    JPasswordField passwordTextField;
    String conInf = "localhost:3306/restaurant";
    String account ="";
    String password ="";
    Connection con;

    Login() throws IOException {

        //get conInf from config.inf, if not exist creat it
        File file = new File("config.inf");
        if(file.exists()) {
            try{
                String content = new String(Files.readAllBytes(Paths.get("config.inf")), StandardCharsets.UTF_8);
                conInf = content.substring(9);
            }
            catch(FileNotFoundException exception){
                exception.printStackTrace();
            }
        }
        else {
            try{
                FileWriter writer = new FileWriter("config.inf");
                writer.write("conInf = localhost:3306/restaurant");
                writer.flush();
                writer.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


        Font DefaultFontSetting = new Font("Arial", Font.BOLD, 20);


        loginFrame = new JFrame(); 
        //loginFrame.setSize(500,500);
        loginFrame.setMinimumSize(new Dimension(1050,500));
        loginFrame.setLayout( new GridLayout(3,3) );  
        loginFrame.setTitle("Management Client : Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        loginFrame.setResizable(true);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel accountLoginLabel = new JLabel("Account: ");
        accountLoginLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 3;
        gbc.fill = gbc.BOTH;
        loginPanel.add(accountLoginLabel, gbc);


        JLabel passwordLoginLabel = new JLabel("Password: ");
        passwordLoginLabel.setFont(DefaultFontSetting);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 3;
        gbc.fill = gbc.BOTH;
        loginPanel.add(passwordLoginLabel, gbc);

        
        accountTextField = new JTextField("");
        accountTextField.setFont(DefaultFontSetting);
        accountTextField.setEditable(true);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 5.0;
        gbc.weighty = 1;
        gbc.fill = gbc.BOTH;
        loginPanel.add(accountTextField, gbc);

        passwordTextField = new JPasswordField("");
        passwordTextField.setFont(DefaultFontSetting);
        passwordTextField.setEditable(true);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 5.0;
        gbc.weighty = 1;
        gbc.fill = gbc.BOTH;
        loginPanel.add(passwordTextField, gbc);
        

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new GridLayout(3,3));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");

        loginButton.setFont(DefaultFontSetting);

        JLabel[] emptyLabelLoginButtonPanel = new JLabel[9];
        for(int i = 0; i<4; i++){
            emptyLabelLoginButtonPanel[i] = new JLabel();
            loginButtonPanel.add(emptyLabelLoginButtonPanel[i]);
        }
        loginButtonPanel.add(loginButton);
        for(int i = 5; i<9; i++){
            emptyLabelLoginButtonPanel[i] = new JLabel();
            loginButtonPanel.add(emptyLabelLoginButtonPanel[i]);
        }


        JLabel[] emptyLabel = new JLabel[9];
        for(int i = 0; i<4; i++){
            if(i == 1){
                JLabel welcomeLabel = new JLabel("Please login");
                welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
                welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
                loginFrame.add(welcomeLabel);
                continue;
            }
            emptyLabel[i] = new JLabel();
            loginFrame.add(emptyLabel[i]);
        }
        
        loginFrame.add(loginPanel);
        loginFrame.add(loginButtonPanel);

        for(int i = 6; i<9; i++){
            emptyLabel[i] = new JLabel();
            loginFrame.add(emptyLabel[i]);
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu Setting = new JMenu("Setting");
        menuBar.add(Setting);
        JMenuItem itemmenuBarSettingSetServer = new JMenuItem("Set connection");
        itemmenuBarSettingSetServer.addActionListener(this);
        itemmenuBarSettingSetServer.setActionCommand("setcon");
        Setting.add(itemmenuBarSettingSetServer);

        loginFrame.setJMenuBar(menuBar);
        loginFrame.pack();
        loginFrame.setVisible(true);

    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == "setcon"){
            String tempconInf = JOptionPane.showInputDialog(null, "Enter server location \n Example:localhost:3306/restaurant", conInf);
            if(tempconInf == null){  // for cancel button pressed, which resulting input null
                return;
            }
            else{
                conInf = tempconInf;
                try{
                    FileWriter writer = new FileWriter("config.inf");
                    writer.write("conInf = " + conInf);
                    writer.flush();
                    writer.close();
                }
                catch(IOException ex){
                    ex.printStackTrace();
                } 
            }
            
            
            //System.out.println(conInf);
        }
        if(command == "login"){
            account = accountTextField.getText();
            password = String.valueOf(passwordTextField.getPassword());
            //System.out.println(accountTextField.getText());
            //System.out.println(passwordTextField.getPassword());
            
            connectToServer(conInf, account, password);
             
        }
    }

    void connectToServer(String conInf, String account, String password){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   //loading the driver
            //connect to MySQL       
            //localhost:3306/testmysql
            con = DriverManager.getConnection("jdbc:mysql://" + conInf, account, password);
            //^  localhost, port 3306, testmysql 

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            //if wrong connection, password or user name, give error window and return
            JOptionPane.showMessageDialog(null, "Wrong connection setting, user name or password", "Access denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginFrame.dispose();
        new ClientManagement(conInf, account, password);
    }
}
