import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
/**
 * The TastSorter class takes in a file that contains a list of tasks and their prerequisites, then prints a list of the order
 * in which the tasks should be completed.
 *
 * @author Anna Van Boven
 * @version December 12, 2020
 */
public class TaskSorter
{
    /**
     * The main method parses a file of tasks and creates a directed graph out of them. If no file is inputted, it prints an error.
     * 
     * @param a file that lists each task, followed by the tasks that need to be completed prior to the task.
     */
    public static void main(String[] args){
        try{
            File file = new File(args[0]);
            Scanner temp = new Scanner(file); //the temp scanner is used to determine the number of tasks in the file
            Scanner fileReader = new Scanner(file);
            
            int size = 0;
            while(temp.hasNextLine()){
                String line = temp.nextLine();
                size++;
            }
            //creates a directed graph the size of the number of tasks in the file
            DirectedGraph dg = new DirectedGraph(size);
            
            //for each task in the file
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                String[]tasks = line.split("\t");
                if(!dg.contains(tasks[0].toLowerCase())){ //if the task is not already in the graph
                    dg.addVertex(tasks[0].toLowerCase());
                }
                
                for(int i = 1; i<tasks.length; i++){ //for every prereq
                    if(!dg.contains(tasks[i].toLowerCase())){//if the task is not already in the graph
                        dg.addVertex(tasks[i].toLowerCase());
                    }
                    //add an edge from the prereq to the task
                    dg.addEdge(tasks[i].toLowerCase(),tasks[0].toLowerCase());
                }
            }
            //call the private userInteraction method
            userInteraction(dg, file.getName());
        }
        catch( FileNotFoundException e){ //if no file was put in, or a file not in the package.
            System.out.println("File not found.");
        } 
        
        catch(Exception e){
            System.out.println("File not found.");
        }
        
        
    }
    
    /**
     * The userInteraction method prints the order in which to complete the tasks from the file provided from the main method.
     * 
     * @param dg    the directed graph created in the main method   
     * @param fn    the file name of the file passed to the program
     */
    private static void userInteraction(DirectedGraph dg, String fn){
        //this section determines whether there are cycles in the graph
        String mit;
        if(!dg.isCycle()){
            mit = "no";
        }
        else{
            mit = "";
        }
        System.out.println("The file \"" + fn + "\" contains " + mit + " mutually dependent tasks. You must:");
        //grab the list of kernels created by sorting the directed graph
        DirectedGraph.Kernel[] k = dg.sort();
        for(int i = 0; i<k.length; i++){ //prints the list of kernels in the correct order
            System.out.println((i+1) + ": " + k[i].toString());
        }
        System.out.println("Thanks!");
    }
}
