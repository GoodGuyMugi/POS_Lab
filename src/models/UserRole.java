/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author D A I S U K E
 */
public class UserRole extends Action{
    public int user_id;
    public int role_id;
    public String sql;
    public DefaultTableModel mode;
    public int selectedRow;
    public String name;
    
    
   public void setRole(JComboBox cboRole) {
        try {
            this.sql = "Select * from role";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            while (Database.rs.next()) {
                cboRole.addItem(Database.rs.getString("Role"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    public int getRoleId(JComboBox cboRole) {
        int ide = 0;
        try {
            this.sql = "Select * from role where Role =?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, cboRole.getSelectedItem().toString());
            Database.rs = Database.s.executeQuery();
            if (Database.rs.next()) {
                ide = Database.rs.getInt("RoleID");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
        return ide;
    }
    
     public void setUser(JComboBox cboUser) {
        try {
            this.sql = "Select * from tblUser";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            while (Database.rs.next()) {
                cboUser.addItem(Database.rs.getString("UserName"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    public int getUserId(JComboBox cboUser) {
        int ide = 0;
        try {
            this.sql = "Select * from tbluser where UserName =?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, cboUser.getSelectedItem().toString());
            Database.rs = Database.s.executeQuery();
            if (Database.rs.next()) {
                ide = Database.rs.getInt("UserID");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
        return ide;
    }
    
    
    
    public void tranferData(JTable tblRole, JComboBox cboUser,JComboBox cboRole) {
        try{
            if (tblRole.getRowCount() == 0) {
                return;
            }
          this.selectedRow=tblRole.getSelectedRow();
          cboRole.setSelectedItem(tblRole.getValueAt(selectedRow, 0));
          cboUser.setSelectedItem(tblRole.getValueAt(selectedRow, 1));
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }
        
        

    @Override
    public void save() {
          try{
            Database.con.setAutoCommit(false);
            this.sql="insert into user_role values (?,?)";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.role_id);
            Database.s.setObject(2, this.user_id);
            Database.con.setSavepoint();
            Database.s.executeUpdate();
            
            Database.con.commit();
            JOptionPane.showMessageDialog(null, "Add Role Success");
        }catch(Exception ex){
            try {
                Database.con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddStock.class.getName()).log(Level.SEVERE, null, ex1);
            }
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void loadData(JTable table) {
        try {
            this.sql = "SELECT * from user_role INNER JOIN role ON role.RoleID=user_role.role_id INNER JOIN tbluser ON tbluser.UserID=user_role.user_id LIMIT 10";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);
            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("Role"),
                    Database.rs.getObject("UserName"),
                };
                mode.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void delete(JTable table) {
        if(MyFunction.TableItemEmpty(table)== true){
                return;
            }
            selectedRow = table.getSelectedRow();
            this.name = table.getValueAt(selectedRow, 1).toString();
            this.sql = "DELETE user_role FROM user_role INNER JOIN role ON role.RoleID=user_role.role_id INNER JOIN tbluser ON tbluser.UserID=user_role.user_id WHERE UserName=?";
         try{
        try {
            Database.s = Database.con.prepareStatement(this.sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserRole.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.s.setObject(1, this.name);
            mode = (DefaultTableModel) table.getModel();

            int Click = JOptionPane.showConfirmDialog(null, "Do you want to delete this Role?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (Click == JOptionPane.YES_OPTION) {
                int effectedRow = Database.s.executeUpdate();
                if (effectedRow == 1) {
                    mode.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Role Deleted");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
       
    
}
