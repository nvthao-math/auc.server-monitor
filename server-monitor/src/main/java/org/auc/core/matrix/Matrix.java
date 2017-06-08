/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.auc.core.utils.EUtils;

/**
 *
 * @author thaonv
 */
public class Matrix {

    public static List<List<String>> featuresMask(List<String> list, String delimiter) {
        List<List<String>> result = new ArrayList<>();
        int size = list.size();
        int[][] biMask = getBinaryMask(size);
        for (int[] val : biMask) {
            List<String> temp = new ArrayList<>();
            for (int i = 0; i < val.length; i++) {
                if (delimiter != null) {
                    String[] parts = list.get(i).split(delimiter, -1);
                    temp.add(val[i] == 0 ? parts[0] : parts[1]);
                } else {
                    temp.add(val[i] == 0 ? "null" : list.get(i));
                }
            }
            result.add(temp);
        }
        return result;
    }

    public static int[][] getBinaryMask(int size) {
        int rows = (int) Math.pow(2, size);
        System.out.println("rows: " + rows);
        int[][] matrix = new int[rows][size];
        for (int i = 0; i < rows; i++) {
            List<Integer> temp = toBinary(i, size);
            System.out.println(EUtils.toJson(temp));
            for (int j = 0; j < temp.size(); j++) {
                matrix[i][j] = temp.get(j);
            }
        }
        return matrix;
    }

    public static List<Integer> toBinary(int number, int length) {
        List<Integer> result = new LinkedList<>();
        while (number >= 1) {
            if (number == 1 || number == 0) {
                result.add(number);
                break;
            } else {
                int remainder = number % 2;
                result.add(remainder);
                number >>= 1;
            }
        }
        int size = result.size();
        if (size < length) {
            for (int i = 0; i < (length - size); i++) {
                result.add(0);
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static int[][] getIdentity(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
        }
        return matrix;
    }

}
