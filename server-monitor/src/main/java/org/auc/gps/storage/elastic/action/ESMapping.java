/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.action;

import java.util.HashMap;
import java.util.Map;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.EUtils;
import org.auc.gps.storage.elastic.model.Attributes;
import org.auc.gps.storage.elastic.model.Field;
import org.auc.gps.storage.elastic.model.Index;
import org.auc.gps.storage.elastic.model.IndexType;
import org.auc.gps.storage.elastic.model.Mapping;
import org.auc.gps.storage.elastic.model.Type;

/**
 *
 * @author cpu10869-local
 */
public class ESMapping {

    private static final String TAG = ESMapping.class.getSimpleName();

    public static String getMappingSpeedProfiles() {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> speedProfileMapping = new HashMap<>();
            speedProfileMapping.putAll(Mapping.Fields.get(Field.ALL, false));
            Map<String, Attributes> propertiesMap = new HashMap<>();
            // add properties
            propertiesMap.put("id", new Attributes(Type.LONG.toString()));
            propertiesMap.put("arcId", new Attributes(Type.LONG.toString()));
            propertiesMap.put("subClass", new Attributes(Type.STRING.toString(), Index.NOT_ANALYZED.toString()));
            propertiesMap.put("length", new Attributes(Type.DOUBLE.toString()));
            propertiesMap.put("speedMax", new Attributes(Type.DOUBLE.toString()));
            propertiesMap.put("speed", new Attributes(Type.DOUBLE.toString()));
            propertiesMap.put("time", new Attributes(Type.STRING.toString(), Index.NOT_ANALYZED.toString()));
            propertiesMap.put("dayOfWeek", new Attributes(Type.STRING.toString(), Index.NOT_ANALYZED.toString()));
            propertiesMap.put("dayNameOfWeek", new Attributes(Type.STRING.toString(), Index.NOT_ANALYZED.toString()));
            speedProfileMapping.put("properties", propertiesMap);
            // get final schema
            result.put(IndexType.SPEED.toString(), speedProfileMapping);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return EUtils.toJson(result);
    }

    public static void main(String[] args) {
        String jsonEs = getMappingSpeedProfiles();
        System.out.println(jsonEs);
    }

    public static String getMappingAdLogs() {
        String docType = "rawlog";
        String mappingDefine = "{\""
                + docType
                + "\":  {\"_all\": {\"enabled\": \"false\" }, "
                + "\"properties\": { "
                + "\"networkId\": {\"type\": \"long\"},"
                + "\"loggedTime\": {\"type\": \"date\"},"
                + "\"count\": {\"type\": \"integer\"}," // default = 1
                + "\"siteId\": {\"type\": \"long\"},"
                + "\"convId\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"convLabel\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"userId\": {\"type\": \"long\"},"
                + "\"session\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"beacon\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"conversion\": {\"type\": \"integer\"},"
                + "\"rmConvId\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"rmConvValue\": {\"type\": \"double\"},"
                + "\"products\" : {\"properties\" : {\"productId\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"name\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"pageType\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"brand\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate1\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate2\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate3\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"quantity\" : {\"type\" : \"integer\"},\"value\" : {\"type\" : \"float\"} }},"
                + "\"ecommerce\" : {\"type\": \"nested\", \"properties\" : {\"productId\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"name\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"}, \"normalName\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"normalBrand\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"}, \"normalCate\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"} , \"normalCate1\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"}, \"normalCate2\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"}, \"normalCate3\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"}, \"pageType\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"brand\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate1\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate2\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"cate3\" : {\"type\" : \"string\",\"index\": \"not_analyzed\"},\"quantity\" : {\"type\" : \"integer\"},\"value\" : {\"type\" : \"float\"} }},"
                + "\"urlId\": {\"type\": \"long\"},"
                + "\"refUrlId\": {\"type\": \"long\"},"
                + "\"refer\": {\"type\": \"integer\"},"
                + "\"internal\": {\"type\": \"integer\"},"
                + "\"websiteId\": {\"type\": \"long\"},"
                + "\"insightWebId\": {\"type\": \"long\"},"
                + "\"adId\": {\"type\": \"long\"},"
                + "\"campaignId\": {\"type\": \"long\"},"
                + "\"creativeId\": {\"type\": \"long\"},"
                + "\"sectionId\": {\"type\": \"long\"},"
                + "\"zoneId\": {\"type\": \"long\"},"
                + "\"templateId\": {\"type\": \"integer\"},"
                + "\"formatId\": {\"type\": \"integer\"},"
                + "\"payment\": {\"type\": \"integer\"},"
                + "\"utmChannel\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"utmSource\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"utmMedium\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"utmContent\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"utmCampaign\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"type\": {\"type\": \"integer\"},"
                + "\"res\": {\"type\": \"integer\"},"
                + "\"os\": {\"type\": \"integer\"},"
                + "\"browser\": {\"type\": \"integer\"},"
                + "\"browserVer\": {\"type\": \"integer\"},"
                + "\"deviceType\": {\"type\": \"integer\"},"
                + "\"deviceModel\": {\"type\": \"integer\"},"
                + "\"provinceId\": {\"type\": \"integer\"},"
                + "\"genderId\": {\"type\": \"integer\"},"
                + "\"osId\": {\"type\": \"integer\"},"
                + "\"osVerId\": {\"type\": \"integer\"},"
                + "\"browserId\": {\"type\": \"integer\"},"
                + "\"browserVerId\": {\"type\": \"integer\"},"
                + "\"devId\": {\"type\": \"integer\"},"
                + "\"devVerId\": {\"type\": \"integer\"},"
                + "\"antsPv\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"ageRange\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"convAgeRange\": {\"type\": \"string\",\"index\": \"not_analyzed\"},"
                + "\"countryCode\": {\"type\": \"string\",\"index\": \"not_analyzed\"}"
                + "}" + "}}\"";
        return mappingDefine;
    }

}
