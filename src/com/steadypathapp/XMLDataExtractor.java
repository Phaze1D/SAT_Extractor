package com.steadypathapp;


import com.steadypathapp.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 2:05 PM
 */
public class XMLDataExtractor {



    private Document document;
    String filePath;
    private Comprobante comprobante;


    public XMLDataExtractor(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(filePath);
        this.filePath = filePath;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(fXmlFile);
        document.getDocumentElement().normalize();
    }

    /**
     * Extract the data from the given xml file
     */
    public void extractData() {
        comprobante = extractComprobante();
        comprobante.setEmisor( extractEmisor() );
        comprobante.setReceptor( extractReceptor() );
        comprobante.setTimbreFiscalDigital(extractTimber());
        comprobante.setImpuesto(extractImpuesto());
        comprobante.setConceptos(extractConceptos());
    }

    /**
     * Extracts the cfdi:Comprobante tag info
     * @return A Comprobante object
     */
    private Comprobante extractComprobante(){

        Comprobante retr = new Comprobante();

        NodeList nodeL = document.getElementsByTagName("cfdi:Comprobante");
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            retr.setFolio( Integer.parseInt( ele.getAttribute(Comprobante.FOLIO) )  );
            retr.setFecha( stringToDate(ele.getAttribute(Comprobante.FECHA), "yyyy-MM-dd HH:mm:ss")  );
            retr.setSello(ele.getAttribute(Comprobante.SELLO));
            retr.setFormaDePago(ele.getAttribute(Comprobante.FORMA_DE_PAGO));
            retr.setNoCertificado(ele.getAttribute(Comprobante.NO_CERTIFICADO));
            retr.setCertificado(ele.getAttribute(Comprobante.CERTIFICADO));
            retr.setSubTotal( parseDouble(ele.getAttribute(Comprobante.SUB_TOTAL)) );
            retr.setDescuento(parseDouble(ele.getAttribute(Comprobante.DESCUENTO)) );
            retr.setTipoCambio((int) parseDouble(ele.getAttribute(Comprobante.TIPO_CAMBIO)));
            retr.setMoneda(ele.getAttribute(Comprobante.MONEDA));
            retr.setTotal(parseDouble(ele.getAttribute(Comprobante.TOTAL)));
            retr.setTipoDeComprobante(ele.getAttribute(Comprobante.TIPO_DE_COMPROBANTE));
            retr.setMetodoDePago(ele.getAttribute(Comprobante.METODO_DE_PAGO));
            retr.setLugarExpedicion(ele.getAttribute(Comprobante.LUGAR_EXPEDICION));
            retr.setSerie(ele.getAttribute(Comprobante.SERIE));
        }
        return retr;
    }

    /**
     * Extracts the cfdi:Emisor tag info
     * @return A Emisor object
     */
    private Emisor extractEmisor(){
        Emisor temp = new Emisor();
        NodeList nodeL = document.getElementsByTagName("cfdi:Emisor");
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            temp.setRfc(ele.getAttribute(Emisor.RFC));
            temp.setNombre(ele.getAttribute(Emisor.NOMBRE));
            temp.setDomicilio(extractDomicilio("cfdi:DomicilioFiscal"));
        }
        return temp;
    }

    /**
     * Extracts the cfdi:Receptor tag info
     * @return A Receptor object
     */
    private Receptor extractReceptor(){
        Receptor temp = new Receptor();
        NodeList nodeL = document.getElementsByTagName("cfdi:Receptor");
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            temp.setRfc(ele.getAttribute(Emisor.RFC));
            temp.setNombre(ele.getAttribute(Emisor.NOMBRE));
            temp.setDomicilio(extractDomicilio("cfdi:Domicilio"));
        }
        return temp;
    }

    /**
     * Extracts the Receptor and Emisor Domicilio tag info
     * @return A Domicilio object
     */
    private Domicilio extractDomicilio(String tag){

        Domicilio temp = new Domicilio();
        NodeList nodeL = document.getElementsByTagName(tag);
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            temp.setCodigoPostal(Integer.parseInt(ele.getAttribute(Domicilio.CODIGO_POSTAL)));
            temp.setPais(ele.getAttribute(Domicilio.PAIS));
            temp.setNoInterior(ele.getAttribute(Domicilio.NO_INTERIOR));
            temp.setNoExterior(ele.getAttribute(Domicilio.NO_EXTERIOR));
            temp.setMunicipio(ele.getAttribute(Domicilio.MUNICIPIO));
            temp.setLocalidad(ele.getAttribute(Domicilio.LOCALIDAD));
            temp.setCalle(ele.getAttribute(Domicilio.CALLE));
            temp.setColonia(ele.getAttribute(Domicilio.COLONIA));
            temp.setEstado(ele.getAttribute(Domicilio.ESTADO));

        }
        return temp;
    }

    /**
     * Extracts the tfd:TimbreFiscalDigital tag info
     * @return A TimbreFiscalDigital object
     */
    private TimbreFiscalDigital extractTimber(){
        TimbreFiscalDigital temp = new TimbreFiscalDigital();
        NodeList nodeL = document.getElementsByTagName("tfd:TimbreFiscalDigital");
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            temp.setUuid(ele.getAttribute(TimbreFiscalDigital.UUID));
            temp.setSelloSAT(ele.getAttribute(TimbreFiscalDigital.SELLO_SAT));
            temp.setSelloCFD(ele.getAttribute(TimbreFiscalDigital.SELLO_CFD));
            temp.setNoCertificadoSAT(ele.getAttribute(TimbreFiscalDigital.NO_CERTIFICADO_SAT));
            temp.setFechaTimbrado(stringToDate(ele.getAttribute(TimbreFiscalDigital.FECHA_TIMBRADO), "yyyy-MM-dd HH:mm:ss"));
        }
        return temp;
    }

    /**
     * Extracts the cfdi:Impuestos tag info
     * @return A Impuesto object
     */
    private Impuesto extractImpuesto(){
        Impuesto temp = new Impuesto();
        NodeList nodeL = document.getElementsByTagName("cfdi:Impuestos");
        Node node = nodeL.item(0);

        if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
            Element ele = (Element)node;
            temp.setTotalImpuestosTrasladados(parseDouble(ele.getAttribute(Impuesto.TOTAL_IMPUESTOS_TRASLADADOS)));
            temp.setTraslados(extractTraslado());
        }
        return temp;
    }

    /**
     * Extracts all the cfdi:Traslado tags info
     * @return An ArrayList of Traslado
     */
    private ArrayList<Traslado> extractTraslado(){

        ArrayList<Traslado> atemp = new ArrayList<Traslado>();

        NodeList nlist = document.getElementsByTagName("cfdi:Traslado");

        for (int i = 0; i < nlist.getLength(); i++){
            Node node = nlist.item(i);
            if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
                Element ele = (Element)node;
                Traslado ttemp = new Traslado();
                ttemp.setImporte(parseDouble(ele.getAttribute(Traslado.IMPORTE)));
                ttemp.setImpuesto(ele.getAttribute(Traslado.IMPUESTO));
                ttemp.setTasa(parseDouble(ele.getAttribute(Traslado.TASA)));

                atemp.add(ttemp);
            }
        }
        return atemp;

    }

    /**
     * Extracts all the cfdi:Concepto tags info
     * @return An ArrayList of Conceptos
     */
    private ArrayList<Concepto> extractConceptos(){

        ArrayList<Concepto> temp = new ArrayList<Concepto>();

        NodeList nlist = document.getElementsByTagName("cfdi:Concepto");

        for (int i = 0; i < nlist.getLength(); i++){
            Node node = nlist.item(i);
            if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
                Element ele = (Element)node;
                Concepto ctemp = new Concepto();
                ctemp.setCantidad((int)parseDouble(ele.getAttribute(Concepto.CANTIDAD)));
                ctemp.setImporte(parseDouble(ele.getAttribute(Concepto.IMPORTE)));
                ctemp.setUnidad(ele.getAttribute(Concepto.UNIDAD));
                ctemp.setValorUnitario(parseDouble(ele.getAttribute(Concepto.VALOR_UNITARIO)));

                ctemp.setInfoAduaneras(extractInfoAduan(node.getChildNodes()));

                Producto ptemp = new Producto();
                ptemp.setDescripcion(ele.getAttribute(Producto.DESCRIPTION));
                ptemp.setPLU(ele.getAttribute(Producto.PLU_VAR));

                ctemp.setProducto(ptemp);
                temp.add(ctemp);
            }
        }
        return temp;
    }

    /**
     * Extracts all the InformancionAduanera tag for a given Concepto node
     * @param nlist The parent cfdi:Concepto node
     * @return An ArrayList of InformancionAduanera
     */
    private ArrayList<InformancionAduanera> extractInfoAduan(NodeList nlist){

        ArrayList<InformancionAduanera> temp = new ArrayList<InformancionAduanera>();


        for (int i = 0; i < nlist.getLength(); i++){
            Node node = nlist.item(i);
            if(node != null &&  node.getNodeType() == Node.ELEMENT_NODE){
                Element ele = (Element)node;
                InformancionAduanera itemp = new InformancionAduanera();
                itemp.setAduana(ele.getAttribute(InformancionAduanera.ADUANA));
                itemp.setFecha(stringToDate(ele.getAttribute(InformancionAduanera.FECHA), "yyyy-MM-dd"));
                itemp.setNumero(ele.getAttribute(InformancionAduanera.NUMERO));

                temp.add(itemp);
            }
        }
        return temp;
    }

    /**
     * Converts a string into a date
     * @param dateString The string to be converted
     * @param format The format the string is in
     * @return LocalDate Object
     */
    private LocalDate stringToDate(String dateString, String format){
        dateString = dateString.replace('T',' ');
        DateTimeFormatter dateF = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
        return LocalDate.parse(dateString, dateF);
    }

    /**
     * Converts a string into a double. Checks if the string is null
     * @param string The string to be converted
     * @return The converted double or 0.00 if the string was null
     */
    private double parseDouble(String string){
        if (string == null || string.equalsIgnoreCase("") || string.isEmpty()){
            return 0;
        }else{
            return Double.parseDouble(string);
        }
    }

    /**
     * Getter for the Comprobante object that holds all the xml info
     * @return
     */
    public Comprobante getComprobante() {
        return comprobante;
    }



    /** Create randoms IDs for the models(execpt Comprobantes) not AI **/


}
