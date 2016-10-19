package com.steadypathapp.models;


import com.steadypathapp.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:48 PM
 */
public class Producto {


    private int id;
    private String PLU;
    private String descripcion;


    public static boolean findByPLU(String plu){
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT PLU FROM Productos WHERE PLU = ?");
            ps.setString(1, plu);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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


    public static int getDatabaseID(String plu){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT id FROM Productos WHERE PLU = ?");
            ps.setString(1, plu);
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
            ps = con.prepareStatement("INSERT INTO Productos (PLU, descripcion)" + "VALUES (?, ?)");
            ps.setString(1, this.getPLU());
            ps.setString(2, this.getDescripcion());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPLU() {
        return PLU;
    }

    public void setPLU(String PLU) {
        this.PLU = PLU;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static final String ID = "id";
    public static final String PLU_VAR = "noIdentificacion";
    public static final String DESCRIPTION = "descripcion";
}
