/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.auc.gps.speed.model.SpeedModel;

/**
 *
 * @author inet
 */
public class UniqueUser {

    private static final String TAG = UniqueUser.class.getSimpleName();
    private static final Set<String> UNIQUE_USER = new HashSet<>();

    public UniqueUser() {
        super();
    }

    public static void addElement(String id) {
        UNIQUE_USER.add(id);
    }

    public static void addList(List<SpeedModel> models) {
        for (SpeedModel model : models) {
            UNIQUE_USER.add(Long.toString(model.getId()));
        }
    }

    public static long getUniqueUser() {
        return UNIQUE_USER.size();
    }

}
