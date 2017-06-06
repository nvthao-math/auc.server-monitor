/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gen.stats;

import java.io.Serializable;

/**
 *
 * @author thaonv
 */
public class NodeConnection implements Serializable {
    
    private String command;
    private String tecoCaller;
    private String tecoCallee;
    private StringBuilder track;

    // constructor
    public NodeConnection() {
        super();
    }
    
    public NodeConnection(String command, String teco) {
        this.command = command;
        if ("406".equals(command)) {
            this.tecoCaller = teco;
        } else {
            this.tecoCallee = teco;
        }
        this.track = new StringBuilder();
        this.track.append(teco).append("-");
    }
    
    public NodeConnection setConnection(NodeConnection node) {
        if ("406".equalsIgnoreCase(node.getCommand())) {
            this.setTecoCaller(node.getTecoCaller());
        } else {
            this.setTecoCallee(node.getTecoCallee());
        }
        return this;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return the tecoCaller
     */
    public String getTecoCaller() {
        return tecoCaller;
    }

    /**
     * @param tecoCaller the tecoCaller to set
     */
    public void setTecoCaller(String tecoCaller) {
        this.tecoCaller = tecoCaller;
        this.track.append(tecoCaller).append("-");
    }

    /**
     * @return the tecoCallee
     */
    public String getTecoCallee() {
        return tecoCallee;
    }

    /**
     * @param tecoCallee the tecoCallee to set
     */
    public void setTecoCallee(String tecoCallee) {
        this.tecoCallee = tecoCallee;
        this.track.append(tecoCallee).append("-");
    }

    /**
     * @return the track
     */
    public StringBuilder getTrack() {
        return track;
    }

    /**
     * @param track the track to set
     */
    public void setTrack(StringBuilder track) {
        this.track = track;
    }
    
}
