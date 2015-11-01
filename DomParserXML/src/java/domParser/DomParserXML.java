/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domParser;

import com.oracle.jrockit.jfr.ContentType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author Sergey
 */
@ManagedBean
@RequestScoped
public class DomParserXML {

    private String rootElement;
    private Part fileXml;
    private File file;
    String path = "/Users/Sergey/Desktop/temt.tmp";

    public DomParserXML() {
    }

    public void myStart() {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(path));
            document.getDocumentElement().normalize();
            rootElement = document.getDocumentElement().getNodeName();
            System.out.println("" + rootElement);
        } catch (Exception ex) {
            System.out.println("");
        } finally {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException ex) {
                Logger.getLogger(DomParserXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void upload() {
        try (InputStream is = fileXml.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                PrintWriter outputStream = new PrintWriter(path);) {
            String line;
            while ((line = br.readLine()) != null) {
                outputStream.write(line);
                System.out.println("" + line);
            }
            setFile(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(DomParserXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Part getFileXml() {
        return fileXml;
    }

    public void setFileXml(Part fileXml) {
        this.fileXml = fileXml;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getRootElement() {
        return rootElement;
    }

    public void setRootElement(String rootElement) {
        this.rootElement = rootElement;
    }

}
