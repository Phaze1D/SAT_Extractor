package com.steadypathapp.models;

import com.steadypathapp.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:46 PM
 */
public class Comprobante {

    private int id;
    private int folio;
    private LocalDate fecha;
    private String sello;
    private String formaDePago;
    private String noCertificado;
    private String certificado;
    private double subTotal;
    private double descuento;
    private int tipoCambio;
    private String moneda;
    private double total;
    private String tipoDeComprobante;
    private String metodoDePago;
    private String lugarExpedicion;
    private String serie;

    private int Emisors_id;
    private int Receptors_id;

    private TimbreFiscalDigital timbreFiscalDigital;
    private Impuesto impuesto;
    private Emisor emisor;
    private Receptor receptor;
    private ArrayList<Concepto> conceptos;


    public Comprobante() {
        conceptos = new ArrayList<Concepto>();
    }


    /**
     * *********************************** Database Operations *************************************
     */

    public static boolean find(int folio, int emid, String serie) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            if(serie != null) {
                ps = con.prepareStatement("SELECT folio FROM Comprobantes WHERE folio = ? AND  Emisors_id = ? AND serie = ?");
                ps.setInt(1, folio);
                ps.setInt(2, emid);
                ps.setString(3, serie);
            }else{
                ps = con.prepareStatement("SELECT folio FROM Comprobantes WHERE folio = ? AND  Emisors_id = ?");
                ps.setInt(1, folio);
                ps.setInt(2, emid);
            }
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

    public static int getDatabaseID(int folio, int emid, String serie){

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            if(serie != null) {
                ps = con.prepareStatement("SELECT id FROM Comprobantes WHERE folio = ? AND  Emisors_id = ? AND serie = ?");
                ps.setInt(1, folio);
                ps.setInt(2, emid);
                ps.setString(3, serie);
            }else{
                ps = con.prepareStatement("SELECT id FROM Comprobantes WHERE folio = ? AND  Emisors_id = ?");
                ps.setInt(1, folio);
                ps.setInt(2, emid);
            }
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
            ps = con.prepareStatement(
                    "INSERT INTO Comprobantes(folio, fecha, sello, formaDePago, noCertificado, certificado, subTotal, descuento, " +
                            "tipoCambio, moneda, total, tipoDeComprobante, metodoDePago, lugarExpedicion, serie, Emisors_id, Receptors_id)" +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,this.getFolio());
            ps.setDate(2, Date.valueOf(this.getFecha()));
            ps.setString(3,this.getSello());
            ps.setString(4,this.getFormaDePago());
            ps.setString(5,this.getNoCertificado());
            ps.setString(6,this.getCertificado());
            ps.setDouble(7,this.getSubTotal());
            ps.setDouble(8,this.getDescuento());
            ps.setInt(9,this.getTipoCambio());
            ps.setString(10,this.getMoneda());
            ps.setDouble(11,this.getTotal());
            ps.setString(12,this.getTipoDeComprobante());
            ps.setString(13,this.getMetodoDePago());
            ps.setString(14,this.getLugarExpedicion());
            ps.setString(15,this.getSerie());
            ps.setInt(16,this.getEmisors_id());
            ps.setInt(17,this.getReceptors_id());
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


    public static void cancelCom(int id){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement(" UPDATE Comprobantes SET cancelado=1 WHERE Comprobantes.id = ?");
            ps.setInt(1,id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(ArrayList<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    public TimbreFiscalDigital getTimbreFiscalDigital() {
        return timbreFiscalDigital;
    }

    public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
        this.timbreFiscalDigital = timbreFiscalDigital;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public void setReceptor(Receptor receptor) {
        this.receptor = receptor;
    }

    public int getReceptors_id() {
        return Receptors_id;
    }

    public void setReceptors_id(int receptors_id) {
        Receptors_id = receptors_id;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getNoCertificado() {
        return noCertificado;
    }

    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(int tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoDeComprobante() {
        return tipoDeComprobante;
    }

    public void setTipoDeComprobante(String tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public int getEmisors_id() {
        return Emisors_id;
    }

    public void setEmisors_id(int emisors_id) {
        Emisors_id = emisors_id;
    }

    public void setSerie(String serie){
        this.serie = serie;
    }

    public String getSerie(){
        return serie;
    }


    /**
     * *********************************** Constants ***************************************
     */

    public static final String FOLIO = "folio";
    public static final String FECHA = "fecha";
    public static final String SELLO = "sello";
    public static final String SERIE= "serie";
    public static final String FORMA_DE_PAGO = "formaDePago";
    public static final String NO_CERTIFICADO = "noCertificado";
    public static final String CERTIFICADO = "certificado";
    public static final String SUB_TOTAL = "subTotal";
    public static final String DESCUENTO = "descuento";
    public static final String TIPO_CAMBIO = "TipoCambio";
    public static final String MONEDA = "Moneda";
    public static final String TOTAL = "total";
    public static final String TIPO_DE_COMPROBANTE = "tipoDeComprobante";
    public static final String METODO_DE_PAGO = "metodoDePago";
    public static final String LUGAR_EXPEDICION = "LugarExpedicion";
    public static final String FK_EMISORS = "Emisors_id";
    public static final String FK_RECEPTORS = "Receptors_id";
}
