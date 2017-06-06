/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;

/**
 *
 * @author cpu10869-local
 */
public class TestNode {

    public static void main(String[] args) {
        Node node = nodeBuilder().node();
        Client client = node.client();
        // on shutdown
        node.close();
    }

}
