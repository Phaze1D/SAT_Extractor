package com.steadypathapp;

import com.steadypathapp.models.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;


public class UploadAlgorithm {


    private XMLDataExtractor extractor;


    public void startAlgo(String path) throws IOException, SAXException, ParserConfigurationException, SQLException {
        extractor = new XMLDataExtractor(path);
        extractor.extractData();
        uploadAlgo();
    }

    public XMLDataExtractor getExtractor() {
        return extractor;
    }

    private void uploadAlgo() throws SQLException {

        int emid = Emisor.getDatabaseID(extractor.getComprobante().getEmisor().getRfc());
        extractor.getComprobante().getEmisor().setId(emid);
        boolean exist = Comprobante.find(extractor.getComprobante().getFolio(), emid, extractor.getComprobante().getSerie());

        if (!exist) {
            uploadEmisor();
            uploadReceptor();
            uploadComprobante();
            uploadDomicilioE();
            uploadDomicilioR();
            uploadConceptos();
            uploadImpuestos();
            uploadTraslados();
            uploadTimbreFiscal();
        }
    }

    private void uploadEmisor() {
        if (Emisor.findByRFC(extractor.getComprobante().getEmisor().getRfc())) {
            extractor.getComprobante().getEmisor().setId(Emisor.getDatabaseID(extractor.getComprobante().getEmisor().getRfc()));
        } else {
            //Generate Emisor id
            // By saving then retriving
            extractor.getComprobante().getEmisor().save();
            extractor.getComprobante().getEmisor().setId(Emisor.getDatabaseID(extractor.getComprobante().getEmisor().getRfc()));
        }
    }

    private void uploadReceptor() {
        if (Receptor.findByRFC(extractor.getComprobante().getReceptor().getRfc())) {
            extractor.getComprobante().getReceptor().setId(Receptor.getDatabaseID(extractor.getComprobante().getReceptor().getRfc()));
        } else {
            //Generate Receptor id
            // By saving then retriving
            extractor.getComprobante().getReceptor().save();
            extractor.getComprobante().getReceptor().setId(Receptor.getDatabaseID(extractor.getComprobante().getReceptor().getRfc()));
        }
    }

    private void uploadComprobante() {
        extractor.getComprobante().setEmisors_id(extractor.getComprobante().getEmisor().getId());
        extractor.getComprobante().setReceptors_id(extractor.getComprobante().getReceptor().getId());
        extractor.getComprobante().save();
        int id = Comprobante.getDatabaseID(extractor.getComprobante().getFolio(), extractor.getComprobante().getEmisors_id(), extractor.getComprobante().getSerie());
        extractor.getComprobante().setId(id);
    }

    private void uploadDomicilioE() {
        extractor.getComprobante().getEmisor().getDomicilio().setEmisors_id(extractor.getComprobante().getEmisor().getId());
        //Look up Domicilio
        if (Domicilio.findByEmisor(extractor.getComprobante().getEmisor().getId())) {
            //Look throught each one and find differences
            if (!Domicilio.isequalEmisorDom(extractor.getComprobante().getEmisor().getId(),
                    extractor.getComprobante().getEmisor().getDomicilio())) {
                //Generate Domicilio id
                // By saving then retriving
                extractor.getComprobante().getEmisor().getDomicilio().save();
            }

        } else {
            //Generate Domicilio id
            // By saving then retriving
            extractor.getComprobante().getEmisor().getDomicilio().save();
        }
    }

    private void uploadDomicilioR() {
        extractor.getComprobante().getReceptor().getDomicilio().setReceptors_id(extractor.getComprobante().getReceptor().getId());
        if (Domicilio.findByReceptor(extractor.getComprobante().getReceptor().getId())) {
            //Look throught each one and find differences
            if (!Domicilio.isequalRecDom(extractor.getComprobante().getReceptor().getId(),
                    extractor.getComprobante().getReceptor().getDomicilio())) {
                //Generate Domicilio id
                // By saving then retriving
                extractor.getComprobante().getReceptor().getDomicilio().save();
            }
        } else {
            //Generate Domicilio id
            // By saving then retriving
            extractor.getComprobante().getReceptor().getDomicilio().save();
        }
    }

    private void uploadConceptos() {
        //Look Up the Products if exist set ID else genearte ID
        for (Concepto con : extractor.getComprobante().getConceptos()) {
            if (Producto.findByPLU(con.getProducto().getPLU())) {
                con.getProducto().setId(Producto.getDatabaseID(con.getProducto().getPLU()));
                con.setProductos_id(con.getProducto().getId());
            } else {
                // By saving then retriving
                con.getProducto().save();
                con.getProducto().setId(Producto.getDatabaseID(con.getProducto().getPLU()));
                con.setProductos_id(con.getProducto().getId());
            }

            con.setComprobantes_id(extractor.getComprobante().getId());
            con.save();
            con.setId(Concepto.getDatabaseID(con.getProductos_id(), con.getComprobantes_id()));

            for (InformancionAduanera info : con.getInfoAduaneras()) {
                info.setConceptos_id(con.getId());
                info.save();
            }
        }
    }

    private void uploadImpuestos() {
        extractor.getComprobante().getImpuesto().setComprobantes_id(extractor.getComprobante().getId());
        extractor.getComprobante().getImpuesto().save();
        extractor.getComprobante().getImpuesto().setId(Impuesto.getDatabaseID(extractor.getComprobante().getId()));
    }

    private void uploadTraslados() {
        for (Traslado tra : extractor.getComprobante().getImpuesto().getTraslados()) {
            tra.setImpuestos_id(extractor.getComprobante().getImpuesto().getId());
            tra.save();
        }
    }

    private void uploadTimbreFiscal() {
        extractor.getComprobante().getTimbreFiscalDigital().setComprobantes_id(extractor.getComprobante().getId());
        extractor.getComprobante().getTimbreFiscalDigital().save();
    }

    public void cancelAlgo(String path) throws IOException, SAXException, ParserConfigurationException {
        extractor = new XMLDataExtractor(path);
        extractor.extractData();

        int emid = Emisor.getDatabaseID(extractor.getComprobante().getEmisor().getRfc());
        extractor.getComprobante().getEmisor().setId(emid);
        boolean exist = Comprobante.find(extractor.getComprobante().getFolio(), emid, extractor.getComprobante().getSerie());

        if (exist){
            int id = Comprobante.getDatabaseID(extractor.getComprobante().getFolio(), emid, extractor.getComprobante().getSerie());
            Comprobante.cancelCom(id);
        }


    }

}


