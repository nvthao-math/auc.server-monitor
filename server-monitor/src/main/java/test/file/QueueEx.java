/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.file;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.auc.core.utils.EUtils;

/**
 *
 * @author thaonguyen
 */
public class QueueEx {

    public static void main(String[] args) {

        List<String> myList = new ArrayList<String>();
        myList.add("A");
        myList.add("B");
        myList.add("C");
        myList.add("D");
        //
        Queue<String> myQueue = new LinkedList<>(myList);
        myQueue.poll();
        for (Object theFruit : myList) {
            System.out.println(myQueue.poll());
        }
        System.out.println(EUtils.toJson(myQueue));
        System.out.println("List: " + EUtils.toJson(myList));
    }

}
