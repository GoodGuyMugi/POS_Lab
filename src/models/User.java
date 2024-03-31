/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.toedter.calendar.JDateChooser;
import java.awt.HeadlessException;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import views.MainForm;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author D A I S U K E
 */
public class User extends Action {

    public DefaultTableModel mode;
    public static int id;
    public String dob;
    public String name;
    public String password;
    public String gender;
    public String desc;
    public int phonenumber;
    public float salary;
    public String active;
    public int posId;
    public static String rolename;
    public String sql;
    public String pic;
    public int selectedRow;

    public void LogIn(JFrame jFrame) {
        try {
            Database.connectionDB("localhost:3307", "pos_lab", "root", "qwertyuiop");
            this.sql = "SELECT * from user_role INNER JOIN role ON role.RoleID=user_role.role_id INNER JOIN tbluser ON tbluser.UserID=user_role.user_id Where UserName =? AND Password=?  AND Active='1'";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, this.name);
            Database.s.setString(2, this.password);
            Database.rs = Database.s.executeQuery();

            if (Database.rs.next()) {
                User.id = Integer.parseInt(Database.rs.getObject("UserID").toString());
                User.rolename=Database.rs.getObject("Role").toString();
                MainForm mainform = new MainForm();
                mainform.setVisible(true);
                jFrame.hide();
            } else {
                JOptionPane.showConfirmDialog(null, "Username or Password is invalid !", "Login", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    public void setPosition(JComboBox cboPosition) {
        try {
            this.sql = "SELECT * FROM tblposition ";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            while (Database.rs.next()) {
                cboPosition.addItem(Database.rs.getString("PositionName"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    public int getPositionId(JComboBox cboPosition) {
        int id = 0;
        try {
            this.sql = "SELECT * FROM tblposition WHERE PositionName=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, cboPosition.getSelectedItem().toString());
            Database.rs = Database.s.executeQuery();
            if (Database.rs.next()) {
                id = Database.rs.getInt("PositionID");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
        return id;
    }

    @Override
    public void save() {
        try {
            this.sql = "Insert into tbluser values(null,?,?,?,?,?,?,?,?,?,?)";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, this.name);
            Database.s.setString(2, this.gender);
            Database.s.setString(3, this.dob);
            Database.s.setString(4, this.password);
            Database.s.setString(5, this.desc);
            Database.s.setFloat(6, this.salary);
            Database.s.setInt(7, this.phonenumber);
            Database.s.setString(8, this.active);
            Database.s.setInt(9, this.posId);
            Database.s.setString(10, this.pic);

            int effectedRow = Database.s.executeUpdate();
            if (effectedRow == 1) {
                JOptionPane.showMessageDialog(null, "User Added");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void loadData(JTable table) {
        try {
            this.sql = "SELECT * FROM tbluser as u INNER JOIN tblposition as p on u.PositionID=p.PositionID LIMIT 10";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);
            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("UserID"),
                    Database.rs.getObject("UserName"),
                    Database.rs.getObject("Gender"),
                    Database.rs.getObject("DateOfBirth"),
                    Database.rs.getObject("Password"),
                    Database.rs.getObject("Description"),
                    Database.rs.getObject("Salary"),
                    Database.rs.getObject("Phone"),
                    Database.rs.getObject("Active"),
                    Database.rs.getObject("PositionName"),
                    Database.rs.getObject("Photo")
                };
                mode.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void delete(JTable table) {
        try {
           if(MyFunction.TableItemEmpty(table)== true){
                return;
            }

            selectedRow = table.getSelectedRow();
            this.id = Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
            this.sql = "DELETE FROM tbluser WHERE UserID=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
            mode = (DefaultTableModel) table.getModel();

            int Click = JOptionPane.showConfirmDialog(null, "Do you want to delete this User?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (Click == JOptionPane.YES_OPTION) {
                int effectedRow = Database.s.executeUpdate();
                if (effectedRow == 1) {
                    mode.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "User Deleted");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void search(JTable table) {
        try {
            this.sql = "SELECT * FROM tbluser as u INNER JOIN tblposition as p on u.PositionID=p.PositionID WHERE UserName like CONCAT('%',?,'%')";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.name);
            Database.rs = Database.s.executeQuery();

            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);

            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("UserID"),
                    Database.rs.getObject("UserName"),
                    Database.rs.getObject("Gender"),
                    Database.rs.getObject("DateOfBirth"),
                    Database.rs.getObject("Password"),
                    Database.rs.getObject("Description"),
                    Database.rs.getObject("Salary"),
                    Database.rs.getObject("Phone"),
                    Database.rs.getObject("Active"),
                    Database.rs.getObject("PositionName"),
                    Database.rs.getObject("Photo")
                };
                mode.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tranferData(JTable table,JTextField txtUsername,JDateChooser txtDate,JRadioButton rMale,JRadioButton rFemale,JTextField txtPassword,JTextField txtDesc,JTextField txtSalary,
                                         JTextField txtPhone,JRadioButton rT,JRadioButton rF,JComboBox cboPosition,JLabel labelImage,JTextField txtDirectory){
          if (table.getRowCount() == 0) {
                return;
          }
        selectedRow=table.getSelectedRow();
        txtUsername.setText(table.getValueAt(selectedRow, 1).toString());
        
        if(table.getValueAt(selectedRow,2).equals("M")){
            rMale.setSelected(true);
        }else{
            rFemale.setSelected(true);
        }
        
        txtDate.setDate((Date) table.getValueAt(selectedRow, 3));
        txtPassword.setText(table.getValueAt(selectedRow, 4).toString());
        txtDesc.setText(table.getValueAt(selectedRow, 5).toString());
        txtSalary.setText(table.getValueAt(selectedRow, 6).toString());
        txtPhone.setText(table.getValueAt(selectedRow, 7).toString());
        
        if(table.getValueAt(selectedRow,8).equals("1")){
            rT.setSelected(true);
        }else{
            rF.setSelected(true);
        }
        
        
        cboPosition.setSelectedItem(table.getValueAt(selectedRow, 9));
        
        if(table.getValueAt(selectedRow, 10)==null){
            labelImage.setIcon(null);
            txtDirectory.setText("");
        }else if(table.getValueAt(selectedRow, 10).toString().equals("")){
            labelImage.setIcon(null);
            txtDirectory.setText("");
        }else
            MyFunction.ReadImage1(table, labelImage, 10);
            txtDirectory.setText(table.getValueAt(selectedRow, 10).toString());
    }    

    @Override
    public void update(JTable table) {
        try{
            if(MyFunction.TableItemEmpty(table)== true){
                return;
            }
            
            selectedRow=table.getSelectedRow();
            this.id=Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
            
            this.sql="UPDATE tbluser SET UserName=?,Gender=?,DateOfBirth=?,Password=?,Description=?,Salary=?,Phone=?,Active=?,PositionID=?,Photo=? WHERE UserId=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, this.name);
            Database.s.setString(2, this.gender);
            Database.s.setString(3, this.dob);
            Database.s.setString(4, this.password);
            Database.s.setString(5, this.desc);
            Database.s.setFloat(6, this.salary);
            Database.s.setInt(7, this.phonenumber);
            Database.s.setString(8, this.active);
            Database.s.setInt(9, this.posId);
            Database.s.setString(10, this.pic);
            Database.s.setObject(11, this.id);
            
            int effectedRow = Database.s.executeUpdate();
            if (effectedRow == 1) {
                JOptionPane.showMessageDialog(null, "User Updated");
            }
        }catch(HeadlessException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error"+ex);
        }
    }
   
    }
