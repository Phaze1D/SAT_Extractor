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
 * Time: 1:52 PM
 */
public class Impuesto {

    private int id;
    private double totalImpuestosTrasladados;

    private int Comprobantes_id;

    private ArrayList<Traslado> traslados;

    public Impuesto() {
        traslados = new ArrayList<Traslado>();
    }


    public static int getDatabaseID(int compID){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT id FROM Impuestos WHERE Comprobantes_id = ?");
            ps.setInt(1,compID);
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
            ps = con.prepareStatement("INSERT INTO Impuestos(totalImpuestosTrasladados, Comprobantes_id)" + "VALUES (?,?)");
            ps.setDouble(1, this.getTotalImpuestosTrasladados());
            ps.setInt(2, this.getComprobantes_id());
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


    public void setTraslados(ArrayList<Traslado> traslados) {
        this.traslados = traslados;
    }

    public ArrayList<Traslado> getTraslados() {
        return traslados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(double totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }

    public int getComprobantes_id() {
        return Comprobantes_id;
    }

    public void setComprobantes_id(int comprobantes_id) {
        Comprobantes_id = comprobantes_id;
    }

    public static final String ID = "id";
    public static final String TOTAL_IMPUESTOS_TRASLADADOS = "totalImpuestosTrasladados";
    public static final String FK_COMPROBANTE = "Comprobantes_id";
}
