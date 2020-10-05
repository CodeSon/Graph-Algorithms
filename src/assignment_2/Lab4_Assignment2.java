package assignment_2;/* READ ME: Program reads file and takes each node into a string array, then finds the shortest path using BFS with the source
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

public class Lab4_Assignment2 {


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

    public class Queue<String> implements Iterable<String>
    {
        private Node first; // link to least recently added node
        private Node last;  // link to most recently added node
        private int N;      // number of items on the queue
        private class Node
        {  // nested class to define nodes
            String item;
            Node next; }
        public boolean isEmpty() {  return first == null;  }  // Or: N == 0.
        public int size()        {  return N;  }
        public void enqueue(String item)
        {  // Add item to the end of the list.
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            N++;
        }
        public String dequeue()
        {  // Remove item from the beginning of the list.
            String item = first.item;
            first = first.next;
            if (isEmpty()) last = null;
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
            for (j = 0; vertices[j] != null; j++) { //For loop and if statement to find nodes index in vertices array
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

    public class BreadthFirstPaths {
        private boolean[] marked; // Is a shortest path to this vertex known?
        private String[] edgeTo;     // last vertex on known path to this vertex
        private final String s;      // source

        public BreadthFirstPaths(Graph G, String s, String v) {
            marked = new boolean[G.V()];
            edgeTo = new String[G.V()];
            this.s = s;
            bfs(G, s);
        }

        private void bfs(Graph G, String s) {
            Queue<String> queue = new Queue<String>();
            int count;
            for (count = 0; G.vertices[count] != null; count++) { //For loop and if statement to find nodes index in vertices array
                if (G.vertices[count].equals(s)) {

                    break;
                }
            }
            marked[count] = true;          // Mark the source
            queue.enqueue(s);          //   and put it on the queue.
            while (!queue.isEmpty()) {
                String v = queue.dequeue();// Remove next vertex from the queue.
                int i;
                for (i = 0; G.vertices[i] != null; i++) {
                    if (G.vertices[i].equals(v)) {

                        marked[i] = true;
                        break;
                    }
                }
                for (String w : G.adj(i)) {
                    int j; //For loop and if statement to find nodes index in vertices array
                    for (j = 0; G.vertices[j] != null; j++) {
                        if (G.vertices[j].equals(w)) {

                            break;
                        }
                    }
                    if (!marked[j])       // For every unmarked adjacent vertex,
                    {
                        edgeTo[j] = v;
                        marked[j] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }

        public boolean hasPathTo(Graph G, String v) {
            int i;
            for (i = 0; G.vertices[i]!=null; i++) {
                if (G.vertices[i].equals(v)) {
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
                for (n = 0; G.vertices[n]!=null; n++) { //For loop and if statement to find nodes index in vertices array
                    if (G.vertices[n].equals(x)) {

                        break;
                    }
                }
                path.push(x); //pushes x into list
                System.out.println(x); //prints x
            }
            path.push(s); //pushes s into the list
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


        Lab4_Assignment2 lab = new Lab4_Assignment2();
        Graph graph = lab.new Graph(words);
        sc = new Scanner(file);

        int counter =0;
        while (sc.hasNext()) {  //adds edges and vertices array with strings v and w alernately
            String v = sc.next();
            String w = sc.next();
            graph.vertices[counter++] = v;
            graph.vertices[counter++] = w;
            graph.addEdge(v, w);



        }
        BreadthFirstPaths bfs = lab.new BreadthFirstPaths(graph, "CA", "MA");



            String print = bfs.pathTo(graph, "MA").pop();



    }
}
