/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author thaonv
 */
public class NetWorkUtils {
    
    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
    
    public static void main(String[] args) throws URISyntaxException {
        
        String url = "https://www.sendo.vn/thoi-trang-nu/dam/?q=đamhọatiết";
        System.out.println(url.charAt(45));
        String domain = getDomainName(url);
        System.out.println(domain);
        
    }
    
}
