/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.file.utils;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cpu10869-local
 */
public class FileInfo {

    public static String getFileName(String fileName) {
        return new File(fileName).getName();
    }

    public static <T> void toStorage(Collection<T> records, String path, boolean append) {
        StringBuilder sb = new StringBuilder();
        for (T record : records) {
            sb.append(record)
                    .append("\n");
        }
        // write to disk
        (new WriterExecutor(path, append) {
            @Override
            public void builds() throws Exception {
                this.bufferedWriter.write(sb.toString());
            }
        }).run();
    }

    public static void main(String[] args) {
        String test = "data/adlogs/test.txt";
        Set<String> sets = new HashSet<>();
        sets.add("1234");
        sets.add("abc");
        //
        toStorage(sets, test, true);
        toStorage(sets, test, true);
    }

}
