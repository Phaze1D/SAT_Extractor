package com.steadypathapp.models;

import com.steadypathapp.DBConnector;

import java.sql.*;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 1:51 PM
 */
public class Domicilio {


    private int id;
    private String calle;
    private String noExterior;
    private String noInterior;
    private String colonia;
    private String localidad;
    private String municipio;
    private String estado;
    private String pais;
    private int codigoPostal;

    private int Receptors_id;
    private int Emisors_id;


    public static boolean findByEmisor(int eid) {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT Emisors_id FROM Domicilios WHERE Emisors_id = ?");
            ps.setInt(1, eid);
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

    public static boolean isequalEmisorDom(int eid, Domicilio domi) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT * FROM Domicilios WHERE Emisors_id = ?");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            return domEqual(rs, domi);
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

    public static boolean isequalRecDom(int eid, Domicilio domi) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT * FROM Domicilios WHERE Receptors_id = ?");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            return domEqual(rs, domi);
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

    private static boolean domEqual(ResultSet rs, Domicilio domi) throws SQLException {


        while (rs.next()) {
            String calle = rs.getString("calle");
            String noExterior = rs.getString("noExterior").replaceAll("\\s", "");
            String noInterior = rs.getString("noInterior").replaceAll("\\s", "");
            String colonia = rs.getString("colonia").replaceAll("\\s", "");
            String localidad = rs.getString("localidad").replaceAll("\\s", "");
            String municipio = rs.getString("municipio").replaceAll("\\s", "");
            String estado = rs.getString("estado").replaceAll("\\s", "");
            String pais = rs.getString("pais").replaceAll("\\s", "");
            int codigoPostal = rs.getInt("codigoPostal");

            if (calle.equalsIgnoreCase(domi.getCalle().replaceAll("\\s", "")) ||
                    codigoPostal == domi.getCodigoPostal()) {
                return true;
            }


        }
        return false;
    }

    public static boolean findByReceptor(int rid) {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("SELECT Receptors_id FROM Domicilios WHERE Receptors_id = ?");
            ps.setInt(1, rid);
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


    public void save() {

        if (this.getCalle() != null && this.getCodigoPostal() != 0 && this.getEstado() != null) {
           sureSave();
        }


    }

    private void sureSave() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getInstance().getConnection();
            ps = con.prepareStatement("INSERT INTO Domicilios (calle, noExterior, noInterior, colonia, localidad, municipio, estado, pais, codigoPostal, Receptors_id, Emisors_id)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, this.getCalle());
            ps.setString(2, this.getNoExterior());
            ps.setString(3, this.getNoInterior());
            ps.setString(4, this.getColonia());
            ps.setString(5, this.getLocalidad());
            ps.setString(6, this.getMunicipio());
            ps.setString(7, this.getEstado());
            ps.setString(8, this.getPais());
            if (this.getCodigoPostal() != 0) ps.setInt(9, this.getCodigoPostal());
            if (this.getReceptors_id() == 0) {
                ps.setNull(10, Types.INTEGER);
            } else {
                ps.setInt(10, this.getReceptors_id());
            }

            if (this.getEmisors_id() == 0) {
                ps.setNull(11, Types.INTEGER);
            } else {
                ps.setInt(11, this.getEmisors_id());
            }
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getReceptors_id() {
        return Receptors_id;
    }

    public void setReceptors_id(int receptors_id) {
        Receptors_id = receptors_id;
    }

    public int getEmisors_id() {
        return Emisors_id;
    }

    public void setEmisors_id(int emisors_id) {
        Emisors_id = emisors_id;
    }

    public static final String ID = "id";
    public static final String CALLE = "calle";
    public static final String NO_EXTERIOR = "noExterior";
    public static final String NO_INTERIOR = "noInterior";
    public static final String COLONIA = "colonia";
    public static final String LOCALIDAD = "localidad";
    public static final String MUNICIPIO = "municipio";
    public static final String ESTADO = "estado";
    public static final String PAIS = "pais";
    public static final String CODIGO_POSTAL = "codigoPostal";
    public static final String FK_EMISORS = "Emisors_id";
    public static final String FK_RECEPTORS = "Receptors_id";
}
