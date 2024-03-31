/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author D A I S U K E
 */
public class AddStock extends Product{
    public String expDate;
    public int newQty;
    public String sql1;
    
//     public void setUser(JComboBox cboUser) {
//        try {
//            this.sql = "Select * from tbluser";
//            Database.s = Database.con.prepareStatement(this.sql);
//            Database.rs = Database.s.executeQuery();
//            while (Database.rs.next()) {
//                cboUser.addItem(Database.rs.getString("UserName"));
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
//        }
//    }
//
//    public int getUserId(JComboBox cboUser) {
//        int id = 0;
//        try {
//            this.sql = "Select * from tbluser where UserName =?";
//            Database.s = Database.con.prepareStatement(this.sql);
//            Database.s.setString(1, cboUser.getSelectedItem().toString());
//            Database.rs = Database.s.executeQuery();
//            if (Database.rs.next()) {
//                id = Database.rs.getInt("UserID");
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
//        }
//        return id;
//    }
    
    public void tranferData(JTable tblProduct,JTextField txtPid,JTextField txtPname,JTextField txtQtyInStock,JLabel LabelImage){
        try{
            int selectedRows=tblProduct.getSelectedRow();
            this.id=Integer.valueOf(tblProduct.getValueAt(selectedRows, 0).toString());
            this.sql="select * from tblproduct where ProductID=?";
            Database.s=Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
            Database.rs=Database.s.executeQuery();
            if(Database.rs.next()){
                txtPid.setText(Database.rs.getObject("ProductID").toString());
                txtPname.setText(Database.rs.getObject("ProductName").toString());
                txtQtyInStock.setText(Database.rs.getObject("QtyInStock").toString());
                MyFunction.ReadImage1( tblProduct,LabelImage,6);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
      }

    @Override
    public void save() {
        try{
            Database.con.setAutoCommit(false);
            this.sql="insert into tbladdstock values (?,CURRENT_TIMESTAMP(),?,?,?)";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
            Database.s.setObject(2, this.expDate);
            Database.s.setObject(3, this.newQty);
            Database.s.setObject(4, User.id);
            Database.con.setSavepoint();
            Database.s.executeUpdate();
            
            this.sql1="update tblproduct set QtyInStock=QtyInStock+? where ProductID=?";
            Database.s=Database.con.prepareStatement(this.sql1);
            Database.s.setObject(1,this.newQty);
            Database.s.setObject(2,this.id);
            Database.con.setSavepoint();
            Database.s.executeUpdate();
            
            Database.con.commit();
            JOptionPane.showMessageDialog(null, "Add Success");
        }catch(Exception ex){
            try {
                Database.con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddStock.class.getName()).log(Level.SEVERE, null, ex1);
            }
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

}
