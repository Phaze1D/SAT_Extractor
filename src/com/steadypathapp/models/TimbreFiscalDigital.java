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
 * Time: 1:52 PM
 */
public class TimbreFiscalDigital {


    private int id;
    private String selloCFD;
    private LocalDate fechaTimbrado;
    private String uuid;
    private String noCertificadoSAT;
    private String selloSAT;

    private int Comprobantes_id;


    public void save(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO `Timbre Fiscal Digital`(selloCFD, fechaTimbrado, UUID, noCertificadoSAT, selloSAT, Comprobantes_id)"
                            + "VALUES (?,?,?,?,?,?)");
            ps.setString(1,this.getSelloCFD());
            ps.setDate(2, Date.valueOf(this.getFechaTimbrado()));
            ps.setString(3,this.getUuid());
            ps.setString(4,this.getNoCertificadoSAT());
            ps.setString(5,this.getSelloSAT());
            ps.setInt(6,this.getComprobantes_id());
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

    public String getSelloCFD() {
        return selloCFD;
    }

    public void setSelloCFD(String selloCFD) {
        this.selloCFD = selloCFD;
    }

    public LocalDate getFechaTimbrado() {
        return fechaTimbrado;
    }

    public void setFechaTimbrado(LocalDate fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNoCertificadoSAT() {
        return noCertificadoSAT;
    }

    public void setNoCertificadoSAT(String noCertificadoSAT) {
        this.noCertificadoSAT = noCertificadoSAT;
    }

    public String getSelloSAT() {
        return selloSAT;
    }

    public void setSelloSAT(String selloSAT) {
        this.selloSAT = selloSAT;
    }

    public int getComprobantes_id() {
        return Comprobantes_id;
    }

    public void setComprobantes_id(int comprobantes_id) {
        Comprobantes_id = comprobantes_id;
    }

    public static final String ID = "id";
    public static final String SELLO_CFD = "selloCFD";
    public static final String FECHA_TIMBRADO = "FechaTimbrado";
    public static final String UUID = "UUID";
    public static final String NO_CERTIFICADO_SAT = "noCertificadoSAT";
    public static final String SELLO_SAT = "selloSAT";
    public static final String FK_COMPROBANTE = "Comprobantes_id";
}
