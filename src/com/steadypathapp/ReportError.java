package com.steadypathapp;

import java.io.*;


public class ReportError {

    public static void reportError(Exception e, String name) {
        try {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String content = errors.toString();
            int oc = name.indexOf('.');
            if (oc >= 0) {
                name = name.substring(0, oc);
            }
            File file = new File("/Users/davidvillarreal/Dropbox/xmlErrors/" + name + ".txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
