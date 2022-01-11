import java.util.*;
import java.util.ArrayList;
/**
 * The DirectedGraph class creates an unweighted directed graph whose kernels, vertices, and edges are objects.
 *
 * @author Anna Van Boven
 * @version December 12,2020
 * I worked with SaraJane on this project
 */
public class DirectedGraph
{
    public int numVertices; //the number of total vertices created so far
    public int numEdges; //the number of total edges created so far
    public Vertex[] vertices; //an array of inputted size that holds the vertices
    public HashMap<String,Integer> tasks; //holds the spot in the vertices array that each vertex is at

    /**
     * The DirectedGraph creates a directed graph that can hold the number of vertices given to it
     */
    public DirectedGraph(int size){
        vertices = new Vertex[size];
        tasks = new HashMap<>();
        numVertices = 0;
        numEdges = 0;
    }

    /**
     * The Vertex class creates a Vertex that holds its label as a string.
     */
    public class Vertex{
        public ArrayList<Edge> edges; //an arraylist of edges that begin at this vertex and lead to another
        public String task; //the task label

        /**
         * Creates a vertex with the given name.
         */
        Vertex(String name){
            edges = new ArrayList<Edge>();
            task = name;
        }

        /**
         * Returns a list of the edges connected to this vertex.
         * 
         * @return  edge ArrayList
         */
        public ArrayList<Edge> getEdges(){
            return edges;
        }

        /**
         * The sharedEdge method finds the edge shared between two vertices.
         * @param v     the other vertex
         * @return      the shared edge
         */
        public Edge sharedEdge(Vertex v){
            Edge edge = null;
            //loops through each edge connected to the current vertex, checking to see if the edge
            //connects the current vertex with the inputted vertex
            for(int i = 0; i < edges.size(); i++){
                if(edges.get(i).getOther(this).task.equals(v.task)){
                    edge = edges.get(i);
                }
            }
            return edge;
        }

        /**
         * @return  the task name
         */
        public String toString(){
            return task;
        }

    }

    /**
     * The Edge class creates an unweighted, directed Edge object that holds the two vertices on either end of it.
     * 
     */
    public class Edge{
        public Vertex start; //starting vertex
        public Vertex end; //ending vertex

        /**
         * Creates an edge with the given starting and ending vertices.
         */
        Edge(Vertex vert1, Vertex vert2){
            start = vert1;
            end = vert2;
        }

        /**
         * Returns whatever vertex isn't given that shares the same edge.
         * 
         * @param v     the initial vertex
         * @return      the other vertex
         */
        public Vertex getOther(Vertex v){
            if(v.task.equals(start.task)){
                return end;
            }
            else if (v.task.equals(end.task)){
                return start;
            }
            else{
                return null;
            }
        }

        /**
         * Returns the end vertex
         * 
         * @return end
         */
        public Vertex getDestination(){
            return end;
        }

        /**
         * Returns the start vertex
         * 
         * @return start
         */
        public Vertex getStart(){
            return start;
        }

        /**
         * Creates a string representation of the edge
         * 
         * @return s    holds the start and end vertex of each edge
         */
        public String toString(){
            String s = "start: " + start.task + ", end: " + end.task;
            return s;
        }
    }

    /**
     * The Kernel class creates a kernel that holds a group of vertices that are part of the same group of strongly connected
     * components.
     */
    public class Kernel{
        public ArrayList<Vertex> verts; //an arraylist of all the vertices in the kernel
        public int numVerts; //number of vertices in the kernel
        public int kernelNum; //a label for the kernel

        /**
         * Creates a kernel object with the kernel number given.
         */
        public Kernel(int kn){
            kernelNum = kn;
            verts = new ArrayList<Vertex>();
            numVerts = 0;
        }

        /**
         * The addVertex method takes in a vertex and adds it to the kernel
         * 
         * @param v     vertex to add
         */
        public void addVertex(Vertex v){
            verts.add(v);
            numVerts++;
        }

        /**
         * The getNum method returns which kernel number this kernel object is
         * 
         * @return kernelNum
         */
        public int getNum(){
            return kernelNum;
        }

        /**
         * The contains method returns true if this kernel contains a vertex and false otherwise
         */
        public boolean contains(Vertex v){
            return verts.contains(v);
        }

        /**
         * The toString method returns a string representation of the kernel, as a list of its vertices
         */
        public String toString(){
            String s = "";
            for(int i = 0; i<verts.size()-1; i++){
                s+= verts.get(i).toString() + ", ";
            }
            s+=verts.get(verts.size()-1).toString();
            return s;
        }
    }

    /**
     * The addEdge method adds an edge to the directed graph
     * 
     * @param start the starting vertex
     * @param end   the ending vertex
     */
    public void addEdge(String start, String end){
        //get the spot in the vertex array that holds the correct vertices
        try{
            int v1 = tasks.get(start);
            int v2 = tasks.get(end);
            addEdge(vertices[v1],vertices[v2]); //calls the private addEdge method on the vertices
            numEdges++;
        }
        catch(NullPointerException e){
            System.out.println("graph does not contain one of the two vertices");
        }
    }

    private void addEdge(Vertex start, Vertex end){
        Edge edge = new Edge(start,end); //creates a new edge object
        start.edges.add(edge); //adds the edge to the start vertex's list of edges
    }

    /**
     * The addVertex method adds a vertex to the directed graph
     * 
     * @param task  tha vertex label
     */
    public void addVertex(String task){
        tasks.put(task,numVertices); //puts the new vertex in the hashmap with the key being its spot in the vertices array
        Vertex v = new Vertex(task); //creates a new vertex object
        vertices[numVertices] = v; //places the new vertex in the vertices array
        numVertices++; //increment counter
    }

    /**
     * The countVertices method returns the number of vertices created this far
     */
    public int countVertices(){
        return numVertices;
    }

    /**
     * The countEdges method returns the number of edges created this far
     */
    public int countEdges(){
        return numEdges;
    }

    /**
     * The getAdjacencyList returns a list of all the vertices that are adjacent to a vertex.
     * 
     * @param v1    vertex to explore
     * @return      array of adjacent vertices
     */
    public Vertex[] getAdjacencyList(Vertex v1){
        Vertex[] adj = new Vertex[v1.edges.size()];
        //loops through all the edges connected to the vertex and adds the vertex at the other end of the edge to the
        //array
        for(int i = 0; i < v1.edges.size(); i++){
            adj[i] = v1.edges.get(i).getOther(v1);
        }
        return adj;
    }

    /**
     * The areAdjacent method returns whether two vertices share an edge.
     * 
     * @param v1,v2 the two vertices
     * @return  true if they share an edge, false if otherwise
     */
    private boolean areAdjacent(Vertex v1, Vertex v2){
        for(int i = 0; i < v1.edges.size(); i++){
            if(v1.edges.get(i).getOther(v1).task.equals(v2.task)){
                return true;
            }
        }
        return false;
    }

    /**
     * The isCycle method returns true if the directed graph contains a cycle, and false otherwise.
     */
    public boolean isCycle(){
        boolean[]marked = new boolean[this.numVertices]; //an array determining if each vertex has been inspected
        boolean[]onStack = new boolean[this.numVertices]; //an array determining if each vertex is on the stack

        for(int v = 0; v<vertices.length; v++){
            if(marked[v]){ //if it has already been inspected, carry on
                continue;
            }

            if(cycleFinding(v,marked,onStack)){ //if a cycle was found, return true
                return true;
            }
        }
        return false; //if no cycles were found, return false
    }

    /**
     * The cycleFinding method reutrns true if a cycle is found from the given vertex, and false otherwise.
     * 
     * @param i the index of the vertex to explore
     * @param marked    an array of which vertices have already been inspected
     * @param onStack   an array of which vertices are on the stack
     * @return whether there is a cycle from the starting vertex
     */
    private boolean cycleFinding(int i, boolean[]marked, boolean[]onStack){
        Vertex v = vertices[i]; //grab the vertex
        marked[i] = true; //it has been inspected
        onStack[i] = true; //it is currently on the stack
        Vertex n = null; //neighboring vertex
        Vertex[]list = getAdjacencyList(v); //all vertices directly reachable from i
        for(int k = 0; k<list.length; k++){
            n = list[k]; //neighbor vertex
            int index = tasks.get(n.task); //index of neighbor vertex
            if(onStack[index]){ //if it is already on the stack, then it has been reached by another vertex
                return true;
            }

            if(!marked[index]){ //if it hasn't already been explored, see if you can find a way to return to this vertex 
                if(cycleFinding(index, marked, onStack)){
                    return true;
                }
            }
        }
        onStack[i] = false; //take off the stack
        return false;
    }

    /**
     * The contains method returns true if a vertex is in the graph, and false otherwise
     */
    public boolean contains(String task){
        //checks to see if the string is already in the hashmap
        if(tasks.get(task)!=null){
            return true;
        }
        return false;
    }

    /**
     * The reverseGraph method reverses the graph by reversing all of the edges
     * 
     * @return reverse  a directed graph with all the edges reversed
     */
    public DirectedGraph reverseGraph(){
        DirectedGraph reverse = new DirectedGraph(this.numVertices); //reverse dg
        Vertex v; //vertex to explore
        Vertex[] list; //array of adjacent vertices
        Vertex w; // neighboring vertex
        boolean[]marked = new boolean[this.numVertices]; //an array holding whether a vertex has been explored
        for(int i = 0; i<this.numVertices; i++){
            v = vertices[i];
            if(!marked[i]){ //if the vertex hasn't already been explored
                reverse.addVertex(v.task);
                marked[i] = true;
            }

            list = getAdjacencyList(v);
            for(int k = 0; k<list.length; k++){
                w = list[k]; //grab its neighbor
                if(!marked[tasks.get(w.task)]){ //if its neighbor isn't yet on the graph
                    reverse.addVertex(w.task);
                    marked[tasks.get(w.task)] = true;

                }
                reverse.addEdge(w.task,v.task); //add edge from neighbor to original vertex

            }

        }
        return reverse;
    }

    /**
     * The sortTopologically method sorts the current directed graph topologically, regardless of whether there is a 
     * valid topological order to the graph
     * 
     * @return an array of vertices in the sorted orer
     */
    public Vertex[] sortTopologically(){
        boolean[] marked = new boolean[vertices.length]; //an array of whether each vertex has been explored
        Vertex[]list = new Vertex[vertices.length]; // the list to add the vertices too
        int[] index = new int[1]; //the spot to add the vertices too in the array
        index[0] = list.length-1;

        for(int i = 0; i<vertices.length; i++){
            if(marked[i]){ //if already explored, continue
                continue;
            }
            topoSortDFT(i, marked, list, index); //perform a dft from the vertex
        }

        return list;
    }

    /**
     * The topoSortDFT method performs a depth-first traversal of the directed graph, adding vertices to the list as it goes.
     * 
     * @param i index of the vertex to sort from
     * @param marked    array of whether each vertex has been explored  
     * @param list      list of vertices to add to
     * @index           where to add each vertex to in the list
     */
    private void topoSortDFT(int i, boolean[] marked, Vertex[]list, int[] index){
        marked[i] = true; //mark as explored
        Vertex v = vertices[i]; //grab the vertex
        Vertex n = null; //neighbor
        Vertex[]l = getAdjacencyList(v); //list of adjacent vertices
        for(int k = 0; k<l.length; k++){
            n = l[k];
            int ind = tasks.get(n.task); //index of neighbor
            if(!marked[ind]){//if the neighbor isn't already marked, sort from that vertex
                topoSortDFT(ind, marked, list, index);
            }
        }

        list[index[0]] = v; //add vertex to the correct spot on the list
        index[0]--; //decrement the index
    }

    /**
     * The sort method performs a sort on the directed graph.
     * 
     * @return k    an array of kernels in topological order
     */
    public Kernel[] sort(){
        Kernel[] k;

        if(isCycle()){ //if there is a cycle
            Kernel[] temp = cycleSort();
            int i = 0;
            while(temp[i] == null){ //count the number of blank spaces in the cycleSort() array
                i++;
            }
            k = new Kernel[temp.length-i]; //create a new kernel of the length of only the filled spots

            for(int m=0; m<k.length; m++){ //fill the new array
                k[m] = temp[i+m];

            }

        }
        else{ //if there is no cycle
            Vertex[] temp = sortTopologically();
            k = new Kernel[temp.length];
            Kernel kern;
            for(int i = 0; i<temp.length; i++){ //make each vertex into its own kernel, add to the kernel array
                kern = new Kernel(i);
                kern.addVertex(temp[i]);
                k[i] = kern;
            }
        }
        return k;
    }

    /**
     * 
     * The cycleSort method sorts a directed graph given that it contains at least one cycle.
     */
    private Kernel[] cycleSort(){
        boolean[] marked = new boolean[numVertices]; //array containing whether each vertex has been explored
        Vertex[] topoList = new Vertex[numVertices]; //pseaudo-topological list of the vertices 
        Vertex[] reverse = reverseGraph().sortTopologically(); //pseudo-topologically sorted list of the reverse graph
        Kernel[] kernels = new Kernel[numVertices]; //a kernel array of its maximum size (one vertex/kernel)
        int[] index = new int[1]; //index of where to add the vertex to in the topoList array
        index[0] = topoList.length-1;
        int i=0; //grabs the index of the next unmarked vertex on the reverse list
        int rev; // grabs the next unmarked vertex on the reverse list
        int kcounter = 0; //tracks how many kernels are created
        Kernel k; //current kernel
        int kadder = index[0]; //tracks which vertex to add to the current kerner
        int kindex = kernels.length-1; //tracks where in the kernel array to add the current kernel
        //System.out.println("reverse list: " + arrayToString(reverse));
        while(topoList[0]==null){
            do{ //grab the next unmarked vertex
                rev = tasks.get(reverse[i].task);
                i++;
            }
            while(marked[rev]);
            //make a new kernel
            k = new Kernel(kcounter);
            //sort topologically from the unmarked vertex, adding to the reverse list
            topoSortDFT(rev,marked,topoList,index);
            //kadder starts from the previous searches' latest addition and goes until there are no more vertices on the list
            while(topoList[kadder]!=null){
                k.addVertex(topoList[kadder]); //add vertex to the current kernel
                kadder--; //decrement
                if(kadder<0){ //when it hits below zero, you're done
                    break;
                }
            }
            kcounter++; //increment k counter
            kernels[kindex] = k; //add current kernel to kernel array
            kindex--; //decrement k index
        }

        return kernels;
    }

    public void printgraph(){
        for(int i = 0; i<numVertices; i++){
            System.out.println("vertex " + vertices[i].task + " has the following edges:");
            for(int j = 0; j<vertices[i].edges.size(); j++){
                System.out.println(vertices[i].edges.get(j).toString());
            }
        }
    }
}
