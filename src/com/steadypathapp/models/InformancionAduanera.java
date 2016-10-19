package com.steadypathapp.models;

import com.steadypathapp.DBConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:49 PM
 */
public class InformancionAduanera {


    private int id;
    private String numero;
    private LocalDate fecha;
    private String aduana;

    private int Conceptos_id;

    public void save(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("INSERT INTO `Informancion Aduanera`(numero, fecha, aduana, Conceptos_id)" +
                    "VALUES (?,?,?,?)");
            ps.setString(1,this.getNumero());
            ps.setDate(2, Date.valueOf(this.getFecha()));
            ps.setString(3,this.getAduana());
            ps.setInt(4,this.getConceptos_id());
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public int getConceptos_id() {
        return Conceptos_id;
    }

    public void setConceptos_id(int conceptos_id) {
        Conceptos_id = conceptos_id;
    }

    public static final String ID = "id";
    public static final String NUMERO = "numero";
    public static final String FECHA = "fecha";
    public static final String ADUANA = "aduana";
    public static final String FK_CONCEPTOS = "Conceptos_id";
}
