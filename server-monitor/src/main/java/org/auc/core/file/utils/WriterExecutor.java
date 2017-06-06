/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.file.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author thaonguyen
 */
public abstract class WriterExecutor {

    protected BufferedWriter bufferedWriter;
    protected String fileName;
    protected boolean append;

    // default constructor
    public WriterExecutor() {
        super();
    }

    // constructor
    public WriterExecutor(String fileName, boolean append) {
        this.fileName = fileName;
        this.append = append;
    }

    public void run() {
        FileWriter fileWriter = null;
        try {
            if (null == this.fileName) {
                builds();
            } else {
                File file = new File(this.fileName);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // true = append file
                fileWriter = new FileWriter(file.getAbsoluteFile(), this.append);
                this.bufferedWriter = new BufferedWriter(fileWriter);
                builds();
            }
        } catch (Exception e) {
            StringWriter error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            System.out.println(error.toString());
        } finally {
            try {
                if (this.bufferedWriter != null) {
                    this.bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public abstract void builds() throws Exception;

}
