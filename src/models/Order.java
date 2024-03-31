/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author D A I S U K E
 */
public class Order  extends Product{
    public double price;
    public int orderID;
    public double cashReceive;
    public double cashReturn;
    public String sqlOrderDetail,updatestockSQL;
    public double amount(){
        return this.qty*this.price;
    }
    
    double totalAmount(JTable tblOrder){
        double total=0;
        for(int i=0;i<tblOrder.getRowCount();i++){
            total+=Double.valueOf(tblOrder.getValueAt(i, 5).toString());
        }
        return total;
    }
    
 
   
    
    public void scanBarcode(JTable tblOrder,JTextField txtScanBarcode, JLabel lblTotalAmount){
        try{
            this.sql="SELECT * FROM tblproduct where Barcode=?";
            Database.s=Database.con.prepareStatement(this.sql);
            this.barcode=Integer.valueOf(txtScanBarcode.getText().trim());
            Database.s.setObject(1, this.barcode);
            Database.rs=Database.s.executeQuery();
            this.mode=(DefaultTableModel)tblOrder.getModel();
            DecimalFormat df=new DecimalFormat("#,##0.00");
            if(Database.rs.next()){
                this.id=Database.rs.getInt("ProductID");
                this.barcode=Database.rs.getInt("Barcode");
                this.pname=Database.rs.getString("ProductName");
                this.qty=1;
                this.price= Database.rs.getDouble("UnitPrice");
                //check dupe
                for(int i=0;i<tblOrder.getRowCount();i++){
                    long checkBarcode = Long.valueOf(mode.getValueAt(i, 1).toString());
                    if(checkBarcode==this.barcode){
                        this.qty=Integer.valueOf(mode.getValueAt(i, 3).toString())+1;
                        mode.setValueAt(this.qty, i, 3);
                        mode.setValueAt(df.format(this.amount()), i, 5);
                        lblTotalAmount.setText(""+df.format(this.totalAmount(tblOrder)));
                        txtScanBarcode.setText("");
                        txtScanBarcode.grabFocus();
                        return;
                    }
                }
                     
                Object[]row= {this.id, this.barcode,this.pname,this.qty,df.format(this.price), df.format(this.amount())};
                mode.addRow(row);
                
                 lblTotalAmount.setText(""+df.format(this.totalAmount(tblOrder)));
                txtScanBarcode.setText("");
                txtScanBarcode.grabFocus();
            }else{
                JOptionPane.showMessageDialog(null, "No Barcode Found");
                txtScanBarcode.grabFocus();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error"+ex.getMessage());
        }
    }
    
        public int OrderAutoID(){
            try{
                this.id=0;
                this.sql="select OrderID from tblorder ORDER BY OrderID DESC";
                Database.s=Database.con.prepareStatement(this.sql);
                Database.rs=Database.s.executeQuery();
                if(Database.rs.next()){
                    this.id=Integer.valueOf(Database.rs.getObject("OrderID").toString());
                }
            }catch (Exception e){
                 JOptionPane.showMessageDialog(null, "error"+e.getMessage());
            }
            return this.id+1;
        }
        
        public void commitData(JTable tblOrder, JLabel lblTotalAmount) throws Exception{
            try {
                mode=(DefaultTableModel)tblOrder.getModel();
                this.orderID=this.OrderAutoID();
                Database.con.setAutoCommit(false);
                this.sql="insert into tblorder values (?,CURRENT_TIMESTAMP(),?,?)";
                Database.s=Database.con.prepareStatement(this.sql);
                Database.s.setObject(1, this.orderID);
                Database.s.setObject(2, User.id);
                Database.s.setObject(3, Double.parseDouble(lblTotalAmount.getText()));
                Database.con.setSavepoint();
                Database.s.executeUpdate();
                
                //insert data to orderdetail
                for(int i=0;i<tblOrder.getRowCount();i++){
                    this.id=Integer.valueOf(tblOrder.getValueAt(i, 0).toString());
                    this.qty=Integer.valueOf(tblOrder.getValueAt(i, 3).toString());
                    this.price=Double.valueOf(tblOrder.getValueAt(i, 4).toString());
                    this.sqlOrderDetail="insert into tblorderdetail values (?,?,?,?,?)";
                    Database.s=Database.con.prepareStatement(this.sqlOrderDetail);
                    Database.s.setObject(1, this.orderID);
                    Database.s.setObject(2, this.id);
                    Database.s.setObject(3, this.qty);
                    Database.s.setDouble(4, this.price);
                    Database.s.setObject(5, this.amount());
                    Database.con.setSavepoint();
                    Database.s.executeUpdate();
                    
                    //update stock
                    this.updatestockSQL="UPDATE tblproduct SET QtyInStock= QtyInStock - ? WHERE ProductID= ?";
                    Database.s=Database.con.prepareStatement(this.updatestockSQL);
                    Database.s.setObject(1, this.qty);
                    Database.s.setObject(2, this.id);
                    Database.con.setSavepoint();
                    Database.s.executeUpdate();
                }
                

                
                
                Database.con.commit();
                JOptionPane.showMessageDialog(null, "Data Commited");
                this.PrintReport(this.orderID);
                mode.setRowCount(0);
                lblTotalAmount.setText("00.00");
            } catch (Exception e) {
                Database.con.rollback();
                JOptionPane.showMessageDialog(null, "error"+e.getMessage());
            }
        }
        
        
        public void PrintReport(int saleId) {
        try {
             String filepath ="/reports/OrderReport.jrxml";
            InputStream file = getClass().getResourceAsStream(filepath);
            JasperDesign jdesigner = JRXmlLoader.load(file);
            this.sql = "SELECT *FROM view_order_to_customer where OrderID='"+ saleId +"'";
            Map parameters = new HashMap();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            parameters.put("CashReceive", df.format(Double.valueOf(this.cashReceive)));
            parameters.put("CashReturn", df.format(Double.valueOf(this.cashReturn)));
            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText(this.sql);
            jdesigner.setQuery(designQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jdesigner);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,Database.con);
//
////            if(false){
////                JasperPrintManager.printReport(jasperPrint, true);
////            }else{
////                  JasperPrintManager.printReport(jasperPrint, false);
////            }
            JasperViewer.viewReport(jasperPrint, false);
//            String filepath ="/reports/report1.jrxml";
//            InputStream file = getClass().getResourceAsStream(filepath);
//            JasperDesign jdesigner = JRXmlLoader.load(file);
//            JasperReport jr = JasperCompileManager.compileReport(jdesigner);
//            JasperPrint jp = JasperFillManager.fillReport(jr, null, Database.con);
//            JasperViewer.viewReport(jp);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }

    }
        
        
}
