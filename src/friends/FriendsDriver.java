package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FriendsDriver 
{
	public static void main (String[]args) throws FileNotFoundException
	{
			File test = new File("test.txt");
			Scanner sc = new Scanner(test);
			Graph friendGraph = new Graph(sc);
			for (int i = 0; i < friendGraph.members.length; i++)
			{
				System.out.println(friendGraph.members[i].name + " is number " + i);
			}
			
			ArrayList<String> shortest = Friends.shortestChain(friendGraph, "sam", "aparna"); 
			System.out.println("The shortest path is: " + shortest);
			
			ArrayList<String> shortestr = Friends.shortestChain(friendGraph, "sam", "tom"); 
			System.out.println("The shortest path is: " + shortestr);
			
			ArrayList<ArrayList<String>> clique = Friends.cliques(friendGraph, "rugers");
			System.out.println("The cliques are: " + clique);
			
			ArrayList<ArrayList<String>> cliquer = Friends.cliques(friendGraph, "rutgers");
			System.out.println("The cliques are: " + cliquer);
			
			ArrayList<String> connect = Friends.connectors(friendGraph);
			System.out.println(connect);
			
	}
}
