/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.ad.log.job;

import org.auc.ad.log.dao.Click;
import org.auc.ad.log.schema.ClickSchema;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author bigdata
 */
public class StorageRawLogBatch extends StorageRawLog {
    
    private static final String TAG = StorageRawLogBatch.class.getSimpleName();
    
    public static void main(String[] args) {
        
    }
    
    @Override
    protected void parse(String line) {
        try {
            Click clickData = new Click();
            String[] fields = line.split(TAB);
            clickData.setTime(fields[ClickSchema.TIME]);
            clickData.setUserId(fields[ClickSchema.USER_ID]);
            clickData.setTimeSpan(fields[ClickSchema.TIME_SPAN]);
            clickData.setIsView(Boolean.parseBoolean(fields[ClickSchema.IS_VIEW]));
            clickData.setUrl(fields[ClickSchema.URL]);
            clickData.setOs(fields[ClickSchema.OS]);
            clickData.setBrowser(fields[ClickSchema.BROWSER]);
            clickData.setDeviceType(fields[ClickSchema.DEVICE_TYPE]);
            clickData.setDeviceModel(fields[ClickSchema.DEVICE_MODEL]);
            clickData.setIp(fields[ClickSchema.IP]);
            clickData.setCountry(fields[ClickSchema.COUNTRY]);
            clickData.setGender(fields[ClickSchema.GENDER]);
            clickData.setProduct(fields[ClickSchema.PRODUCT]);
            clickData.setAge(Integer.parseInt(fields[ClickSchema.AGE]));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }
    
}
