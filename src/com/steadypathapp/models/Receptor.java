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
 * Time: 1:50 PM
 */
public class Receptor {


    private int id;
    private String rfc;
    private String nombre;

    private Domicilio domicilio;


    /**
     * *********************************** Database Operations *************************************
     */
    public static boolean findByRFC(String value){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT rfc FROM Receptors WHERE rfc = ?");
            ps.setString(1, value);
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



    public static int getDatabaseID(String rfc){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT id FROM Receptors WHERE rfc = ?");
            ps.setString(1, rfc);
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
            ps = con.prepareStatement("INSERT INTO Receptors (nombre, rfc)" + "VALUES (?, ?)");
            ps.setString(1, this.getNombre());
            ps.setString(2, this.getRfc());
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

    /**
     * ********************************** Getter And Setters *************************************
     */

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * *********************************** Constants ***************************************
     */
    public static final String ID = "id";
    public static final String RFC = "rfc";
    public static final String NOMBRE = "nombre";
}
