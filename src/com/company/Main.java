package com.company;/* READ ME: Program reads file and takes each node into a string array, then finds a path
 using DFS with the source
of the path and end to be found in the main. Each node has an adjacency list built with all other connected nodes.

OUTPUT:
MA
NY
PA
WV
KY
MO
OK
NM
AZ
CA
*/

import java.util.Scanner;
import java.io.File;
import java.util.Iterator;
import java.io.FileNotFoundException;

class Lab4Test_4_assignment_1 {

    public class Stack<String> implements Iterable<String> {
        private Node first; // top of stack (most recently added node)
        private int N;      // number of items

        private class Node {  // nested class to define nodes
            String item;
            Node next;
        }

        public boolean isEmpty() {
            return first == null;
        }  // Or: N == 0.

        public int size() {
            return N;
        }

        public void push(String item) {  // Add item to top of stack.
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            N++;
        }

        public String pop() {  // Remove item from top of stack.
            String item = first.item;
            first = first.next;
            N--;
            return item;
        }

        public Iterator<String> iterator() {
            return new ListIterator();
        }

        private class ListIterator implements Iterator<String> {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
            }

            public String next() {
                String item = current.item;
                current = current.next;
                return item;
            }
        }


    }


    public class Bag<String> implements Iterable<String> {
        private Node first;  // first node in list

        private class Node {
            String item;
            Node next;
        }

        public void add(String item) {  // same as push() in Stack
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }

        public Iterator<String> iterator() {
            return new ListIterator();
        }

        private class ListIterator implements Iterator<String> {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
            }

            public String next() {
                String item = current.item;
                current = current.next;
                return item;
            }
        }
    }


    public class Graph {
        private final int V;// number of vertices
        private int E;// number of edges
        private Bag<String>[] adj;// adjacency lists
        private String[] vertices = new String[5000];


        public Graph(int V) {
            this.V = V;
            this.E = 0;
            adj = (Bag<String>[]) new Bag[V];// Create array of lists.
            for (int v = 0; v < V; v++)         // Initialize all lists
                adj[v] = new Bag<String>();   //   to empty.
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }

        public void addEdge(String v, String w) {
            boolean flag = false;
            int i = 0;
            for (i = 0; vertices[i] != null; i++) { //For loop and if statement to find nodes index in vertices array
                if (vertices[i].equals(v)) {
                    flag = true;
                    break;
                }
            }



            flag = false;
            adj[i].add(w);  // Add w to v’s list.
            int j;
            for (j = 0; vertices[j] != null; j++) {
                if (vertices[j].equals(w)) {
                    flag = true;
                    break;
                }
            }

            adj[j].add(v);                     // Add v to w’s list.
            E++;
        }


        public Iterable<String> adj(int i) {
            return adj[i];
        }
    }

    public class DepthFirstPaths {
        private boolean[] marked; // Has dfs() been called for this vertex?
        private String[] edgeTo;     // last vertex on known path to this vertex
        private final String s;      // source

        public DepthFirstPaths(Graph G, String s, String v) {
            marked = new boolean[G.V()];
            edgeTo = new String[G.V()];
            this.s = s;
            dfs(G, s);
        }

        private void dfs(Graph G, String v) {
            int i;
            for (i = 0; G.vertices[i]!=null; i++) { //For loop and if statement to find nodes index in vertices array
                if (G.vertices[i].equals(v)) {

                    marked[i] = true;
                    break;
                }
            }

            for (String w : G.adj(i)) {
                int j;
                for (j = 0; G.vertices[j]!=null; j++) { //For loop and if statement to find nodes index in vertices array
                    if (G.vertices[j].equals(w)) {

                        break;
                    }
                }
                if (!marked[j]) {

                    edgeTo[j] = v;

                    dfs(G, w);
                }

            }
        }

        public boolean hasPathTo(Graph G, String v) {
            int i;
            for (i = 0; G.vertices[i]!=null; i++) {
                if (G.vertices[i].equals(v)) { //For loop and if statement to find nodes index in vertices array
                    return true;

                }

            }
            return false;
        }

        public Stack<String> pathTo(Graph G, String v) {
            if (!hasPathTo(G, v)) return null;
            Stack<String> path = new Stack<String>();


            int n;
            for (String x = v; x != null; x = edgeTo[n]) {
                for (n = 0; G.vertices[n]!=null; n++) {
                    if (G.vertices[n].equals(x)) { //For loop and if statement to find nodes index in vertices array

                        break;
                    }
                }
                path.push(x);
                System.out.println(x);
            }
            path.push(s);
            return path;

        }

    }


    public static void main(String[] args) throws FileNotFoundException {

        int words = 0;
        File file = new File("C:/Users/king_/Desktop/Algolabs/Lab4/database.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {

            words++;
            sc.next();

        }


        Lab4Test_4_assignment_1  lab = new Lab4Test_4_assignment_1();
        Graph graph = lab.new Graph(words);
        sc = new Scanner(file);

        int counter =0;
        while (sc.hasNext()) {  ///adds edges and vertices array with strings v and w alernately
            String v = sc.next();
            String w = sc.next();
            graph.vertices[counter++] = v;
            graph.vertices[counter++] = w;
            graph.addEdge(v, w);


        }
        DepthFirstPaths dfs = lab.new DepthFirstPaths(graph, "AL", "MA");

        while (true)
        {
            String print = dfs.pathTo(graph, "VA").pop();


            return;
        }
    }
}










