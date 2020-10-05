package directedGraph_3;//READ ME: This program reads the file and creates a directed graph. Takes two nodes from the main method and checks if
//the nodes are connected by a path, and will print the path if there is one

import java.util.Scanner;
import java.io.File;
import java.util.Iterator;
import java.io.FileNotFoundException;


class Lab4_Assignment3 {

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


    public class Digraph {
        private final int V;// number of vertices
        private int E;// number of edges
        private Bag<String>[] adj;// adjacency lists
        private String[] vertices = new String[5000];


        public Digraph(int V) {
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
            for (i = 0; vertices[i] != null; i++) {
                if (vertices[i].equals(v)) { //For loop and if statement to find nodes index in vertices array
                    flag = true;
                    break;
                }
            }



            flag = false;
            adj[i].add(w);  // Add w to v’s list.

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

        public DepthFirstPaths(Digraph G, String s, String v) {
            marked = new boolean[G.V()];
            edgeTo = new String[G.V()];
            this.s = s;
            dfs(G, s);
        }

        private void dfs(Digraph G, String v) {
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

        public boolean hasPathTo(Digraph G, String v) {
            int i;
            for (i = 0; G.vertices[i]!=null; i++) { //For loop and if statement to find nodes index in vertices array
                if (G.vertices[i].equals(v)) {
                    return true;

                }

            }
            return false;
        }

        public Stack<String> pathTo(Digraph G, String v) {
            if (!hasPathTo(G, v)) System.out.println("Path incomplete as there is no path between these nodes");
            Stack<String> path = new Stack<String>();


            int n;
            for (String x = v; x != null; x = edgeTo[n]) {
                for (n = 0; G.vertices[n]!=null; n++) {
                    if (G.vertices[n].equals(x)) { //For loop and if statement to find nodes index in vertices array

                        break;
                    }
                }
                path.push(x);
                if(edgeTo[n] ==null && !x.equals(s)) //if the path finds the next node is empty and not the target node
                                                   //the program ends and prints the error statment
                {
                    System.out.println("Path incomplete as there is no path between these nodes");
                    System.exit(0);
                }

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


        Lab4_Assignment3 lab = new Lab4_Assignment3();
        Digraph graph = lab.new Digraph(words);
        sc = new Scanner(file);

        int counter =0;
        while (sc.hasNext()) {  //adds edges and vertices array with strings v and w alernately
            String v = sc.next();
            String w = sc.next();
            graph.vertices[counter++] = v;
            graph.vertices[counter++] = w;
            graph.addEdge(v, w);



        }
        DepthFirstPaths dfs = lab.new DepthFirstPaths(graph, "AL", "VA");

        while (true)
        {
            String print = dfs.pathTo(graph, "VA").pop();

            return;
        }
    }
}
