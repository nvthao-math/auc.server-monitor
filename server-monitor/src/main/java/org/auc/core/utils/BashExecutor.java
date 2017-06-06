/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import org.auc.core.file.utils.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thaonguyen
 * @param <T>
 */
public abstract class BashExecutor<T> {

    private static final String TAG = BashExecutor.class.getSimpleName();
    protected String TAB_DELIMITER = "\t";
    private String command;
    protected BufferedReader stdError;
    protected BufferedReader stdInput;
    protected StringBuilder resultSet;

    // constructor
    public BashExecutor() {
        super();
    }

    public BashExecutor(String command) {
        this.command = command;
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
     * @return the stdError
     */
    public BufferedReader getStdError() {
        return stdError;
    }

    /**
     * @return the stdInput
     */
    public BufferedReader getStdInput() {
        return stdInput;
    }

    public T run() {
        T result = null;
        try {
            List<String> commands = new ArrayList<>();
            commands.add("bash");
            commands.add("-c");
            commands.add(this.command);
            //Run macro on target
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.directory(new File("/tmp"));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            this.stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.stdError = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.resultSet = new StringBuilder();
            String line = null;
            while ((line = stdInput.readLine()) != null) {
                this.resultSet.append(line);
                this.resultSet.append("\t");
            }
            result = build();
            process.destroy();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    protected abstract T build();

}
