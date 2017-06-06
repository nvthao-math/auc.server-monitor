/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.cluster.configs;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.auc.core.file.utils.Logger;
import org.auc.core.model.RedisInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thaonguyen
 */
public class RedisConfigs {

    private static final String TAG = RedisConfigs.class.getSimpleName();
    public static RedisInfo INFO = null;
    public static RedisInfo DATA = null;

    static {
        getInfo();
    }

    public static void getInfo() {
        try {
            File clusterXml = new File(ConfigInfo.REDIS.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(clusterXml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("info");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("id").equals("INFO")) {
                        String host = eElement.getElementsByTagName("host").item(0).getTextContent();
                        String port = eElement.getElementsByTagName("port").item(0).getTextContent();
                        INFO = new RedisInfo(host, port);
                    } else if (eElement.getAttribute("id").equals("DATA")) {
                        String host = eElement.getElementsByTagName("host").item(0).getTextContent();
                        String port = eElement.getElementsByTagName("port").item(0).getTextContent();
                        DATA = new RedisInfo(host, port);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

}
