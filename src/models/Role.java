/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author D A I S U K E
 */
public class Role extends Action{
    public DefaultTableModel mode;
    public String role;
    public String sql;
    public int id;

    @Override
    public void loadData(JTable table) {
        try {
            this.sql = "SELECT * FROM role LIMIT 10";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);
            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("RoleID"),
                    Database.rs.getObject("Role"),
                };
                mode.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void save() {
       try {
            this.sql = "Insert into role values(null,?)";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, this.role);
            
            int effectedRow = Database.s.executeUpdate();
            if (effectedRow == 1) {
                JOptionPane.showMessageDialog(null, "Role Added");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void update(JTable table) {
 try{
                    int selectedRow=table.getSelectedRow();
                    this.id=Integer.valueOf(table.getValueAt(selectedRow, 0).toString());

                    this.sql="UPDATE role SET Role=? WHERE RoleID=?";
                    Database.s = Database.con.prepareStatement(this.sql);
                    Database.s.setString(1, this.role);
                    Database.s.setObject(2, this.id);

                    int effectedRow = Database.s.executeUpdate();
                    if (effectedRow == 1) {
                        JOptionPane.showMessageDialog(null, "User Updated");
                    }
                }catch(HeadlessException | SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error"+ex);
                }
    }
    
    
   public void tranferData(JTable table, JTextField txtRole){
        try {
            int selectedRow = table.getSelectedRow();
            this.id = Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
            this.sql = "SELECT * FROM role WHERE RoleID=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
            Database.rs = Database.s.executeQuery();
            if (Database.rs.next()) {
                txtRole.setText(Database.rs.getObject("Role").toString());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }
   }

    @Override
    public void delete(JTable table) {
        try {
            int selectedRow = table.getSelectedRow();
            this.id = Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
            this.sql = "DELETE FROM role WHERE RoleID=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
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
