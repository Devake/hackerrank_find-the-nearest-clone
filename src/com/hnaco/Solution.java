package com.hnaco;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    static private ArrayList<ArrayList<Integer>> buildAdjList(int graphNodes, int[]graphFrom, int[] graphTo){
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < graphNodes; ++i){
            ret.add(new ArrayList<Integer>());
        }
        for(int j =0; j <graphFrom.length; ++j){
            int from = graphFrom[j];
            int to = graphTo[j];
            ret.get(from-1).add(to);
            ret.get(to-1).add(from);
        }
        return ret;
    }
    static private ArrayList<Integer> buildNodeListToCheck(int graphNodes, int val, long[] ids){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(int i = 0; i < graphNodes; ++i){
            long id = ids[i];
            if(id == val){
                ret.add(i);
            }
        }
        return ret;
    }
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        // solve here
        ArrayList<ArrayList<Integer>> adjList = buildAdjList(graphNodes, graphFrom, graphTo);
        //find node with target color
        ArrayList<Integer> nodesToCheck = buildNodeListToCheck(graphNodes, val, ids);
        if(nodesToCheck.size() <= 1){
            return -1;
        }
        int shortestPath = Integer.MAX_VALUE;
        for(Integer nodeToCheck : nodesToCheck){
            //perform bfs
            ArrayList<Integer> bfsQueue = new ArrayList<Integer>();
            bfsQueue.add(nodeToCheck);
            boolean[] visited = new boolean[graphNodes];
            visited[nodeToCheck] = true;
            int currentPathSize = 0;
            while(bfsQueue.size() > 0){
                int head = bfsQueue.get(0);
                bfsQueue.remove(0);
                visited[head] = true;
                currentPathSize++;
                ArrayList<Integer> nodeAdjList = adjList.get(head);
                if(nodeAdjList.size() == 0){
                    currentPathSize = 0;
                    continue;
                }
                for(Integer headNodeAdjNode : nodeAdjList){
                    headNodeAdjNode--;
                    if(visited[headNodeAdjNode]){
                        continue;
                    }
                    if(ids[headNodeAdjNode] == val && currentPathSize < shortestPath){
                        shortestPath = currentPathSize;
                        currentPathSize = 0;
                    } else if(currentPathSize >= shortestPath) {
                        continue;
                    } else {
                        bfsQueue.add(headNodeAdjNode);
                    }
                }
            }
        }
        return shortestPath;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\anguskwong\\code\\java\\nearest_clone\\nearest_clone_testcase_2.txt"));
        String str = "";
        while((str=br.readLine())!=null && str.length()!=0) {

            String[] graphNodesEdges = str.split(" ");
            int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
            int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

            int[] graphFrom = new int[graphEdges];
            int[] graphTo = new int[graphEdges];

            for (int i = 0; i < graphEdges; i++) {
                String[] graphFromTo = br.readLine().split(" ");
                graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
                graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
            }

            long[] ids = new long[graphNodes];

            String[] idsItems = br.readLine().split(" ");

            for (int i = 0; i < graphNodes; i++) {
                long idsItem = Long.parseLong(idsItems[i]);
                ids[i] = idsItem;
            }

            int val = Integer.parseInt(br.readLine());
            int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);
            System.out.println("ans: " + ans);
        }
    }
}
