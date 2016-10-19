package com.steadypathapp.models;

import com.steadypathapp.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:47 PM
 */
public class Concepto {

    private int id;
    private int cantidad;
    private String unidad;
    private double valorUnitario;
    private double importe;

    private int Comprobantes_id;
    private int Productos_id;


    public static int getDatabaseID(int prod, int comp){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT id FROM Conceptos WHERE Productos_id = ? AND Comprobante_id = ?");
            ps.setInt(1,prod);
            ps.setInt(2,comp);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            assert con != null && ps != null && rs != null;
            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("INSERT INTO Conceptos(cantidad, unidad, valorUnitario, importe, Productos_id, Comprobante_id)"
                    + "VALUES (?,?,?,?,?,?)");
            ps.setInt(1,this.getCantidad());
            ps.setString(2, this.getUnidad());
            ps.setDouble(3, this.getValorUnitario());
            ps.setDouble(4, this.getImporte());
            ps.setInt(5, this.getProductos_id());
            ps.setInt(6, this.getComprobantes_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert con != null && ps != null;
            try {
                con.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<InformancionAduanera> infoAduaneras;
    private Producto producto;

    public Concepto() {
        infoAduaneras = new ArrayList<InformancionAduanera>();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ArrayList<InformancionAduanera> getInfoAduaneras() {
        return infoAduaneras;
    }

    public void setInfoAduaneras(ArrayList<InformancionAduanera> infoAduaneras) {
        this.infoAduaneras = infoAduaneras;
    }

    public int getProductos_id() {
        return Productos_id;
    }

    public void setProductos_id(int productos_id) {
        Productos_id = productos_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getComprobantes_id() {
        return Comprobantes_id;
    }

    public void setComprobantes_id(int comprobantes_id) {
        Comprobantes_id = comprobantes_id;
    }

    public static final String ID = "id";
    public static final String CANTIDAD = "cantidad";
    public static final String UNIDAD = "unidad";
    public static final String VALOR_UNITARIO = "valorUnitario";
    public static final String IMPORTE = "importe";

    public static final String FK_PRODUCTOS = "Productos_id";
    public static final String FK_COMPROBANTE = "Comprobantes_folio";
}
