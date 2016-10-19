package com.steadypathapp.models;
import com.steadypathapp.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:53 PM
 */
public class Traslado {

    private int id;
    private String impuesto;
    private double tasa;
    private double importe;

    private int Impuestos_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void save(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("INSERT INTO Traslados(impuesto, tasa, importe, Impuestos_id)" +
                    "VALUES (?,?,?,?)");
            ps.setString(1,this.getImpuesto());
            ps.setDouble(2,this.getTasa());
            ps.setDouble(3,this.getImporte());
            ps.setInt(4,this.getImpuestos_id());
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


    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getImpuestos_id() {
        return Impuestos_id;
    }

    public void setImpuestos_id(int impuestos_id) {
        Impuestos_id = impuestos_id;
    }

    public static final String ID = "id";
    public static final String IMPUESTO = "impuesto";
    public static final String TASA = "tasa";
    public static final String IMPORTE ="importe";

    public static final String FK_IMPUESTOS ="Impuestos_id";
}

