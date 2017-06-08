/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.model;

import java.util.HashMap;
import java.util.Map;
import org.auc.core.utils.EUtils;

/**
 *
 * @author cpu10869-local
 */
public class Mapping {

    public static class Fields extends FieldMap {

        private boolean enabled;

        // constructor
        public Fields() {
            super();
        }

        public Fields(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public static Map<String, FieldMap> get(Field field, boolean enabled) {
            Map<String, FieldMap> map = new HashMap<>();
            map.put(field.toString(), new Fields(enabled));
            return map;
        }

    }

    public static void main(String[] args) {
        System.out.println(EUtils.toJson(Mapping.Fields.get(Field.ALL, false)));
    }

}
