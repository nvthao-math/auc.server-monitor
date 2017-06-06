/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.config;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.auc.core.file.utils.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thaonguyen
 */
public class LogConfig {

    public static final String TAG = LogConfig.class.getSimpleName();
    public static String SPEED_LOG = null;
    public static String LOG_BASE = null;

    static {
        getInfo();
    }

    public static void getInfo() {
        try {
            File clusterXml = new File(ConfigInfo.SPEED.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(clusterXml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("inode");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    SPEED_LOG = eElement.getElementsByTagName("log-data").item(0).getTextContent();
                    LOG_BASE = eElement.getElementsByTagName("log-base").item(0).getTextContent();
                }
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

}
