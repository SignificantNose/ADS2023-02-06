package by.it.group251002.yanucevich.lesson14;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class StatesHanoiTowerC {

    static class DSUNode{
        int index;
        int height;
        int rank;
        DSUNode parent;
        int size;
        DSUNode(int index, int height){
            this.index = index;
            this.rank = 0;
            this.parent = this;
            this.height = height;
            this.size = 1;
        }

        static DSUNode getRoot(DSUNode node){
            if(node.parent==node){
                return node;
            }
            else{
                DSUNode result = getRoot(node.parent);
                node.parent = result;
                return result;
            }
        }
        static void merge(DSUNode n1, DSUNode n2){
            DSUNode nr1 = getRoot(n1);
            DSUNode nr2 = getRoot(n2);
            if (nr1 != nr2){
                if(nr1.rank>nr2.rank){
                    nr2.parent=nr1;
                    nr1.size+=nr2.size;
                }
                else if(nr1.rank<nr2.rank){
                    nr1.parent=nr2;
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

    static void moveDisk(Stack<Integer> pole1, Stack<Integer> pole2){
        if(pole1.isEmpty()){
            pole1.push(pole2.pop());
        }
        else if(pole2.isEmpty()){
            pole2.push(pole1.pop());
        }
        else{
            Integer disk1 = pole1.peek();
            Integer disk2 = pole2.peek();

            if (disk1 > disk2){
                pole1.push(pole2.pop());
            }
            else{
                pole2.push(pole1.pop());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        scan.close();

        Map<Integer, DSUNode> nodeMap = new HashMap<>();
        int clusterIndexes[] = new int[N];
        for(int i=0; i<N; i++){
            clusterIndexes[i]=-1;
        }

        int amntOfMoves = (1<<N)-1;

        // I use stacks in order to be able to output the
        // pole content in the perspective
        Stack<Integer> poleA = new Stack<Integer>();
        Stack<Integer> poleB = new Stack<Integer>();
        Stack<Integer> poleC = new Stack<Integer>();
        for(int i=N;i>=1;i--){
            poleA.push(i);
        }

        for(int i=1; i<=amntOfMoves; i++){
            switch (i%3){
                case 1:
                    // the move is from source to destination
                    moveDisk(poleA, poleC);
                    break;
                case 2:
                    // the move is from source to auxiliary
                    moveDisk(poleA, poleB);
                    break;
                case 0:
                    // the move is from auxiliary to destination
                    moveDisk(poleB, poleC);
                    break;
            }

            int max = Math.max(poleA.size(),Math.max(poleB.size(),poleC.size()));
            DSUNode temp = new DSUNode(i, max);
            nodeMap.put(i, temp);
            if(clusterIndexes[max-1]==-1){
                clusterIndexes[max-1]=i;
            }
            else{
                DSUNode.merge(temp, nodeMap.get(clusterIndexes[max-1]));
            }
        }

        int sizes[] = new int[N];
        for(int i=0; i<N; i++){
            if (clusterIndexes[i]!=-1){
                sizes[i]=nodeMap.get(clusterIndexes[i]).size;
            }
            else{
                sizes[i]=0;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < N; j++)
                if (sizes[maxIndex] < sizes[j])
                    maxIndex = j;
            if (sizes[maxIndex] == 0)
                break;
            int temp = sizes[maxIndex];
            sizes[maxIndex] = sizes[i];
            sizes[i] = temp;
            System.out.print(sizes[i] + " ");
            sb.insert(0, sizes[i] + " ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}