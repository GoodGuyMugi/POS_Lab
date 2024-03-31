/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author D A I S U K E
 */
public class Product extends Action {
    public DefaultTableModel mode;
    public int id;
    public String pname;
    public int barcode;
    public float uprice;
    public float sprice;
    public int category;
    public int qty;
    public String sql;
    public String pic;
    public int selectedRow;
    
    
    
  public void setPosition(JComboBox cboPosition) {
        try {
            this.sql = "Select * from tblcategory ";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            while (Database.rs.next()) {
                cboPosition.addItem(Database.rs.getString("CategoryName"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    public int getPositionId(JComboBox cboPosition) {
        int id = 0;
        try {
            this.sql = "Select * from tblcategory where CategoryName =?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, cboPosition.getSelectedItem().toString());
            Database.rs = Database.s.executeQuery();
            if (Database.rs.next()) {
                id = Database.rs.getInt("CategoryId");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
        return id;
    }

    @Override
    public void save() {
 try {
            this.sql = "Insert into tblproduct values(null,?,?,?,?,?,?,?)";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setString(1, this.pname);
            Database.s.setInt(2, this.barcode);
            Database.s.setFloat(3, this.uprice);
            Database.s.setFloat(4, this.sprice);
            Database.s.setInt(5, this.category);
            Database.s.setString(6,this.pic);
            Database.s.setInt(7, this.qty);

            int effectedRow = Database.s.executeUpdate();
            if (effectedRow == 1) {
                JOptionPane.showMessageDialog(null, "Product Added");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
        }
    }

    @Override
    public void loadData(JTable table) {
try {
            this.sql = "SELECT * FROM tblproduct as p INNER JOIN tblcategory as c on c.CategoryID=p.CategoryID LIMIT 10";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.rs = Database.s.executeQuery();
            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);
            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("ProductID"),
                    Database.rs.getObject("ProductName"),
                    Database.rs.getObject("Barcode"),
                    Database.rs.getObject("UnitPrice"),
                    Database.rs.getObject("SellPrice"),
                    Database.rs.getObject("CategoryName"),
                    Database.rs.getObject("Photo"),
                    Database.rs.getObject("QtyInStock"),
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
            this.sql = "DELETE FROM tblproduct WHERE ProductID=?";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.id);
            mode = (DefaultTableModel) table.getModel();

            int Click = JOptionPane.showConfirmDialog(null, "Do you want to delete this Product?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (Click == JOptionPane.YES_OPTION) {
                int effectedRow = Database.s.executeUpdate();
                if (effectedRow == 1) {
                    mode.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Product Deleted");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void search(JTable table) {
try {
            this.sql = "SELECT * FROM tblproduct as p INNER JOIN tblcategory as c on c.CategoryID=p.CategoryID  WHERE ProductName like CONCAT('%',?,'%')";
            Database.s = Database.con.prepareStatement(this.sql);
            Database.s.setObject(1, this.pname);
            Database.rs = Database.s.executeQuery();

            mode = (DefaultTableModel) table.getModel();
            mode.setRowCount(0);

            while (Database.rs.next()) {
                Object[] row = {Database.rs.getObject("ProductID"),
                    Database.rs.getObject("ProductName"),
                    Database.rs.getObject("Barcode"),
                    Database.rs.getObject("UnitPrice"),
                    Database.rs.getObject("SellPrice"),
                    Database.rs.getObject("CategoryID"),
                    Database.rs.getObject("Photo"),
                    Database.rs.getObject("QtyInStock"),
                };
                mode.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public void tranferData(JTable tblProduct, JTextField txtPname, JTextField txtBarcode, JTextField txtQty, JTextField txtUprice, JTextField txtSprice, JComboBox cboCategory, JLabel labelImage, JTextField txtDirectory) {
     if (tblProduct.getRowCount() == 0) {
                return;
          }
        this.selectedRow=tblProduct.getSelectedRow();
        txtPname.setText(tblProduct.getValueAt(selectedRow, 1).toString());
        txtBarcode.setText(tblProduct.getValueAt(selectedRow, 2).toString());
        txtUprice.setText(tblProduct.getValueAt(selectedRow, 3).toString());
        txtSprice.setText(tblProduct.getValueAt(selectedRow, 4).toString());
        txtQty.setText(tblProduct.getValueAt(selectedRow, 7).toString());
        cboCategory.setSelectedItem(tblProduct.getValueAt(selectedRow, 5));
        
        if(tblProduct.getValueAt(selectedRow, 6)==null){
            labelImage.setIcon(null);
            txtDirectory.setText("");
        }else if(tblProduct.getValueAt(selectedRow, 6).toString().equals("")){
            labelImage.setIcon(null);
            txtDirectory.setText("");
        }else
            MyFunction.ReadImage1(tblProduct, labelImage, 6);
            txtDirectory.setText(tblProduct.getValueAt(selectedRow, 6).toString());
    }    

    @Override
    public void update(JTable table) {
        try{
                    if(MyFunction.TableItemEmpty(table)== true){
                        return;
                    }

                    selectedRow=table.getSelectedRow();
                    this.id=Integer.valueOf(table.getValueAt(selectedRow, 0).toString());

                    this.sql="UPDATE tblproduct SET ProductName=?,Barcode=?,UnitPrice=?,SellPrice=?,CategoryID=?,Photo=?,QtyInStock=? WHERE ProductID=?";
                    Database.s = Database.con.prepareStatement(this.sql);
                    Database.s.setString(1, this.pname);
                    Database.s.setInt(2, this.barcode);
                    Database.s.setFloat(3, this.uprice);
                    Database.s.setFloat(4, this.sprice);
                    Database.s.setInt(5, this.category);
                    Database.s.setInt(7, this.qty);
                    Database.s.setString(6, this.pic);
                    Database.s.setObject(8, this.id);

                    int effectedRow = Database.s.executeUpdate();
                    if (effectedRow == 1) {
                        JOptionPane.showMessageDialog(null, "User Updated");
                    }
                }catch(HeadlessException | SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error"+ex);
                };
            }
    
    
    
    
}
