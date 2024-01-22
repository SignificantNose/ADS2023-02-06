package by.it.group251002.yanucevich.lesson14;

import java.util.*;

public class PointsA {

    private static class DSUNode{
        int x;
        int y;
        int z;
        int index;
        DSUNode parent;
        int rank;
        public DSUNode(int index, int x, int y, int z){
            this.x=x;
            this.y=y;
            this.z=z;
            this.parent = this;
            this.rank = 0;
            this.index = index;
        }
    }

    static DSUNode getRoot(DSUNode node){
        if (node.parent==node){
            return node;
        }
        else{
            return getRoot(node.parent);
        }
    }

    public static void mergeNodes(DSUNode node1, DSUNode node2){
        DSUNode nr1 = getRoot(node1);
        DSUNode nr2 = getRoot(node2);

        if (nr1 != nr2){
            if(nr1.rank > nr2.rank){
                nr2.parent=nr1;
            }
            else if (nr1.rank<nr2.rank) {
                nr1.parent = nr2;
            }
            else{
                nr1.parent = nr2;
                nr2.rank++;
            }
        }
    }

    public static boolean mergeCond(DSUNode node1, DSUNode node2, int maxDist){
        return Math.hypot(Math.hypot(node1.x - node2.x, node1.y - node2.y), node1.z - node2.z)<=maxDist;
    }

    static int getClusterIndex(DSUNode node){
        if(node.parent==node){
            return node.index;
        }
        else{
            return getClusterIndex(node.parent);
        }
    }

    public static void main(String[] args) {
        Map<Integer, DSUNode> nodeMap = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        int acceptDist = scan.nextInt();
        int nOfDots = scan.nextInt();

        // creation of a new DSU node is:
        //      add it to the DSU map
        //      make it the parent of itself
        // AFTER:
        //      merge the nodes based on the rank?
        int x, y, z;
        for(int i = 0; i<nOfDots;i++){
            x=scan.nextInt();
            y=scan.nextInt();
            z=scan.nextInt();
            nodeMap.put(i, new DSUNode(i,x,y,z));
        }

        scan.close();
        for(int i = 0; i < nOfDots; i++){
            for (int j = i+1; j<nOfDots; j++){
                if(mergeCond(nodeMap.get(i),nodeMap.get(j),acceptDist)){
                    mergeNodes(nodeMap.get(i), nodeMap.get(j));
                }
            }
        }


        int clusterCount[] = new int[nOfDots];
        // do not know Java
        for(int i=0;i<nOfDots;i++){
            clusterCount[i] = 0;
        }
        for(int i=0;i<nOfDots;i++){
            clusterCount[getClusterIndex(nodeMap.get(i))]++;
        }
        Arrays.sort(clusterCount);
        int i=0;
        while (i<nOfDots && clusterCount[nOfDots-i-1]!=0){
            System.out.print(clusterCount[nOfDots-i-1]+" ");
            i++;
        }

    }
}