/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor;

import java.util.TimerTask;
import org.auc.monitor.bash.MemBash;
import org.auc.monitor.dao.RamInfo;
import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.EUtils;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class RamMonitor extends TimerTask {

    public static final String TAG = RamMonitor.class.getSimpleName();

    // constructor
    public RamMonitor() {
        super();
    }

    public static RamInfo getMemInfo() {
        RamInfo result = new RamInfo();
        try {
            result = (new BashExecutor<RamInfo>(MemBash.RAM_INFO) {
                @Override
                protected RamInfo build() {
                    RamInfo ramInfo = new RamInfo();
                    String[] elements = this.resultSet.toString().split("\t");
                    for (int i = 0; i < elements.length; i++) {
                        String[] parts = elements[i].split(":");
                        switch (parts[0]) {
                            case "MemTotal":
                                ramInfo.setMemTotal(parts[1]);
                                break;
                            case "MemFree":
                                ramInfo.setMemFree(parts[1]);
                                break;
                            case "MemAvailable":
                                ramInfo.setMemAvailable(parts[1]);
                                break;
                            case "Buffers":
                                ramInfo.setBuffers(parts[1]);
                                break;
                            case "Cached":
                                ramInfo.setCached(parts[1]);
                                break;
                            case "Active":
                                ramInfo.setActive(parts[1]);
                                break;
                            case "Inactive":
                                ramInfo.setInActive(parts[1]);
                                break;
                            case "Active(anon)":
                                ramInfo.setActiveAnon(parts[1]);
                                break;
                            case "Inactive(anon)":
                                ramInfo.setInActiveAnon(parts[1]);
                                break;
                            case "Active(file)":
                                ramInfo.setActiveFile(parts[1]);
                                break;
                            case "SwapCached":
                                ramInfo.setSwapCached(parts[1]);
                                break;
                            case "Inactive(file)":
                                ramInfo.setInActiveFile(parts[1]);
                                break;
                            case "SwapTotal":
                                ramInfo.setSwap(parts[1]);
                                break;
                            case "SwapFree":
                                ramInfo.setSwapFree(parts[1]);
                                break;
                            case "Dirty":
                                ramInfo.setDirty(parts[1]);
                                break;
                            default: // ignore                      
                        }
                    }
                    return ramInfo;
                }
            }).run();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    @Override
    public void run() {
        RamInfo ramInfo = getMemInfo();
        System.out.println(EUtils.toJson(ramInfo));
    }

}
