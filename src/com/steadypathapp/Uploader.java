package com.steadypathapp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Uploader {

    public void excu() {

        DBConnector connector = DBConnector.getInstance();
        boolean didConnect = true;

        try {
            connector.connect("", "");
        } catch (SQLException e) {
            e.printStackTrace();
            didConnect = false;
        }

        if (didConnect) {
            startProcess();
            cancelaComp();
        }


    }

    private void startProcess() {

        File dir = new File("/Users/davidvillarreal/Dropbox/xmlOnly");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                UploadAlgorithm uploadAlgorithm = new UploadAlgorithm();
                try {
                    uploadAlgorithm.startAlgo(child.getAbsolutePath());
                    moveFile(child.getAbsolutePath(), "/Users/davidvillarreal/Dropbox/uploaded/",
                            uploadAlgorithm.getExtractor().getComprobante().getEmisor().getId());
                } catch (Exception e) {
                    e.printStackTrace();

                    ReportError.reportError(e, child.getName());
                    if(uploadAlgorithm.getExtractor() != null) {
                        moveFile(child.getAbsolutePath(), "/Users/davidvillarreal/Dropbox/xmlFileErrors/",
                                uploadAlgorithm.getExtractor().getComprobante().getEmisor().getId());
                    }else{
                        moveFile(child.getAbsolutePath(), "/Users/davidvillarreal/Dropbox/xmlFileErrors/", 1);
                    }
                }
            }
        }

//
//        File dir = new File("/Users/david/Dropbox/xmlOnly");
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            for (File child : directoryListing) {
//
//                if (child.getName().contains(".xml")) {
//
//                    UploadAlgorithm uploadAlgorithm = new UploadAlgorithm();
//                    try {
//                        uploadAlgorithm.startAlgo(child.getAbsolutePath());
//                        moveFile(child.getAbsolutePath(), "/Users/david/Dropbox/uploaded/",
//                                uploadAlgorithm.getExtractor().getComprobante().getEmisor().getId());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        ReportError.reportError(e, child.getName());
//                        if (uploadAlgorithm.getExtractor() != null) {
//                            moveFile(child.getAbsolutePath(), "/Users/david/Dropbox/xmlFileErrors/",
//                                    uploadAlgorithm.getExtractor().getComprobante().getEmisor().getId());
//                        } else {
//                            moveFile(child.getAbsolutePath(), "/Users/david/Dropbox/xmlFileErrors/", 1);
//                        }
//                    }
//                }
//            }
//        }

    }

    private void cancelaComp(){
        File dir = new File("/Users/davidvillarreal/Dropbox/xmlCancelados");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                UploadAlgorithm uploadAlgorithm = new UploadAlgorithm();
                try {
                    uploadAlgorithm.cancelAlgo(child.getAbsolutePath());

                } catch (Exception e) {
                    e.printStackTrace();

                    ReportError.reportError(e, child.getName());
                    if(uploadAlgorithm.getExtractor() != null) {
                        moveFile(child.getAbsolutePath(), "/Users/davidvillarreal/Dropbox/xmlFileErrors/",
                                uploadAlgorithm.getExtractor().getComprobante().getEmisor().getId());
                    }else{
                        moveFile(child.getAbsolutePath(), "/Users/davidvillarreal/Dropbox/xmlFileErrors/", 1);
                    }
                }
            }
        }
    }


    private void moveFile(String soucre, String to, int emisor) {
        System.out.println(emisor);
        File file = new File(soucre);
        if (file.exists()) {
            file.renameTo(new File(to + emisor + file.getName()));
        }
    }

}
