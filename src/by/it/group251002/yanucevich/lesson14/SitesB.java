package by.it.group251002.yanucevich.lesson14;

import java.util.*;

public class SitesB {

    static class DSUNode{

        String data;
        int rank;
        DSUNode parent;
        int size;
        public DSUNode(String data){
            this.data = data;
            this.rank = 0;
            this.parent = this;
            this.size = 1;
        }

        public static DSUNode getRoot(DSUNode node){
            if (node.parent == node){
                return node;
            }
            else{
                DSUNode result = getRoot(node.parent);
                node.parent = result;
                return result;
            }
        }

        public static void merge(DSUNode n1, DSUNode n2){
            DSUNode nr1 = getRoot(n1);
            DSUNode nr2 = getRoot(n2);

            if (nr1!=nr2){
                if(nr1.rank>nr2.rank){
                    nr2.parent = nr1;
                    nr1.size+=nr2.size;
                }
                else if(nr1.rank<nr2.rank){
                    nr1.parent = nr2;
                    nr2.size+=nr1.size;
                }
                else{
                    nr1.parent = nr2;
                    nr2.size+=nr1.size;
                    nr2.rank++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, DSUNode> nodeMap = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while(!input.equals("end")) {
            // put the sites in the map if they don't exist yet
            // and then merge them
            String sites[] = input.split("\\+");
            if(!nodeMap.containsKey(sites[0])){
                nodeMap.put(sites[0],new DSUNode(sites[0]));
            }
            if(!nodeMap.containsKey(sites[1])){
                nodeMap.put(sites[1],new DSUNode(sites[1]));
            }
            DSUNode.merge(nodeMap.get(sites[0]),nodeMap.get(sites[1]));

            input = scan.nextLine();
        }
        scan.close();

        Map<String,Integer> clusterCount = new HashMap<>();
        for (String s : nodeMap.keySet()) {
            DSUNode root = DSUNode.getRoot(nodeMap.get(s));
            clusterCount.put(root.data, root.size);
        }

        ArrayList<Integer> outValues = new ArrayList<Integer>();
        outValues.addAll(clusterCount.values());

        Collections.sort(outValues);
        Collections.reverse(outValues);

        for (int value : outValues)
            System.out.print(value + " ");

    }
}