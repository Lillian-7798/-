package com.example.demo.entity;

import java.util.*;

public class Node {
    public String name;
    public int inDegree;
    public boolean isResult;
    public ArrayList<Node> nextList;
    public ArrayList<Node> resultList;

    public Node(String name){
        this.name = name;
        this.isResult = false;
        this.inDegree = 0;
        this.nextList = new ArrayList<>();
        this.resultList= new ArrayList<>();
    }
    public Node (String name, boolean isResult, int inDegree){
        this.name = name;
        this.isResult = isResult;
        this.inDegree = inDegree;
        this.nextList = new ArrayList<>();
        this.resultList = new ArrayList<>();
    }




    public List<String> topoSort(List<Node> nodes){
        if (nodes == null){
            return null;
        }

        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInDegree = new LinkedList<>();
        List<Node> sortResult = new ArrayList<>();
        for (Node node: nodes){
            inMap.put(node, node.inDegree);
            if (node.inDegree == 0){
                zeroInDegree.add(node);
                nodes.remove(node);
            }
        }

        while (!zeroInDegree.isEmpty()){
            Node current = zeroInDegree.poll();
            sortResult.add(current);
            for (Node next : current.nextList){
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0){
                    zeroInDegree.add(next);
                    nodes.remove(next);
                }
            }
        }

        List<String> result = new ArrayList<>();
        if (!nodes.isEmpty()) {
            result.add("ring");
            return result;
        }

        for (Node node : sortResult){
            if (node.isResult){
                result.add(node.name);
            }
        }
        return result;
    }
}


























