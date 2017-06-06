/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.data.generate.adlogs.dao;

/**
 *
 * @author thaonv
 */
public class Click {

    private long logTime;
    private String time;
    private String type;
    private String url;
    private String domain;
    private String os;
    private String browser;
    private String device;
    private String ip;
    private String province;
    private String country;
    private String gender;
    private String product;
    private int timeToClick;
    private long userId;
    private long zoneId;
    private long creativeId;
    private long campaignId;

    // constructor
    public Click() {
        super();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the zoneId
     */
    public long getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * @return the creativeId
     */
    public long getCreativeId() {
        return creativeId;
    }

    /**
     * @param creativeId the creativeId to set
     */
    public void setCreativeId(long creativeId) {
        this.creativeId = creativeId;
    }

    /**
     * @return the campaignId
     */
    public long getCampaignId() {
        return campaignId;
    }

    /**
     * @param campaignId the campaignId to set
     */
    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * @return the os
     */
    public String getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * @return the browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * @param browser the browser to set
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the timeToClick
     */
    public int getTimeToClick() {
        return timeToClick;
    }

    /**
     * @param timeToClick the timeToClick to set
     */
    public void setTimeToClick(int timeToClick) {
        this.timeToClick = timeToClick;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the logTime
     */
    public long getLogTime() {
        return logTime;
    }

    /**
     * @param logTime the logTime to set
     */
    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

}
