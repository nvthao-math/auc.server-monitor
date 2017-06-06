/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gen.stats;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thaonv
 */
public class TecoNetModel implements Comparator<TecoNetModel>, Serializable {

    private Map<String, Integer> tecoMap;

    public TecoNetModel() {
        this.tecoMap = new HashMap<>();
    }

    public TecoNetModel(String str, Integer val) {
        this.tecoMap = new HashMap<>();
        this.tecoMap.put(str, val);
    }

    public void add(String str, Integer count) {
        Integer oldCount = this.tecoMap.get(str);
        this.tecoMap.put(str, oldCount == null ? count : count + oldCount);
    }

    public TecoNetModel add(TecoNetModel model) {
        Map<String, Integer> modelMap = model.getTecoMap();
        for (String key : modelMap.keySet()) {
            Integer count = modelMap.get(key);
            this.add(key, count);
        }
        return this;
    }

    /**
     * @return the tecoMap
     */
    public Map<String, Integer> getTecoMap() {
        return tecoMap;
    }

    /**
     * @param tecoMap the tecoMap to set
     */
    public void setTecoMap(Map<String, Integer> tecoMap) {
        this.tecoMap = tecoMap;
    }

    @Override
    public int compare(TecoNetModel o1, TecoNetModel o2) {
        return o1.getTecoMap().size() - o2.getTecoMap().size();
    }

}
