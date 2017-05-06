package es.ledg.ghost.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import es.ledg.BaseObj;

public class HashNodeList extends BaseObj {

    String prefix;
    ArrayList<HashNode> list = new ArrayList<HashNode>();

    public HashNodeList(String pfx, HashNode node) {
        prefix = pfx;
        list.add(node);
    }

    public void add(HashNode node) {
        list.add(node);
    }

    public String toString() {
        return "sz: " + list.size();
    }

    public int size() {
        return list.size();
    }

    public void sortByLen() {
        Collections.sort(list, new Comparator<HashNode>() {
            public int compare(HashNode n1, HashNode n2) {
                return n1.lenght() - n2.lenght();
            }
        });
    }
};
