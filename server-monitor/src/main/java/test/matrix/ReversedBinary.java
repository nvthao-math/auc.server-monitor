/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.matrix;

import com.esotericsoftware.kryo.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.auc.core.matrix.Matrix;
import org.auc.core.utils.CommonUtils;

/**
 *
 * @author thaonv
 */
public class ReversedBinary {

    public static final String ALL = "all";
    public static final String TAB_DELIMITER = "\t";

    public static void main(String[] args) {
        String tab = TAB_DELIMITER + ALL;
        String network = "wifi" + tab;
//        String dataCenter = "vnso-pt";
//        String errorCode = "-1";
        //
        String isp = "viettel" + tab;
        String location = "hcm" + tab;
        //
        List<String> list = Arrays.asList(network, isp, location);
        //
        List<List<String>> result = Matrix.featuresMask(list, TAB_DELIMITER);
        System.out.println(CommonUtils.toJson(result));
    }

}
