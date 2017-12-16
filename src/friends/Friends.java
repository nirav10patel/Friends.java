package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) 
	{
		ArrayList<String> shortest = new ArrayList<String>();
		ArrayList<String> check = new ArrayList<String>();
		int i = g.map.get(p1);
		int j = g.map.get(p2);
		Person start = g.members[i];
		Person end = g.members[j];
//		System.out.println(start.name);
//		System.out.println(end.name);
		Queue<Person> path = new Queue<Person>();
		path.enqueue(start);
		int[] distances = new int[g.members.length];
		Arrays.fill(distances,-1);
		distances[i] = 0;
		
		while (!path.isEmpty())
		{
			Person node = path.dequeue();
			int nodeLoc = g.map.get(node.name);
			Friend ptr = node.first;
			while(ptr!= null)
			{
				if(distances[ptr.fnum] == -1)
				{
					distances[ptr.fnum] = distances[nodeLoc] + 1;
					path.enqueue(g.members[ptr.fnum]);
				}
				ptr = ptr.next;
			}
		}
		
		for(int k = 0; k < distances.length;k++)
		{
//			System.out.println("Person: " + k + " The distance to " + g.members[k].name + " is " + distances[k]);
		}
		
		int curDist = distances[j];
		//Friend ptr1 = end.first;
		if(curDist == -1)
		{
			return null;
		}
		shortest.add(end.name);
		
		while(curDist != 0)
		{
			Friend ptr1 = end.first;
			while(ptr1 != null)
			{
				int tempDist = distances[ptr1.fnum];
				if (tempDist == curDist - 1)
				{
					shortest.add(g.members[ptr1.fnum].name);
					curDist = tempDist;
					end = g.members[ptr1.fnum];
					break;
				}
				ptr1 = ptr1.next;
			}
			
			
			
			
			
			
		/*	while(end.first!= null)
			{
				int tempDist = distances[end.first.fnum];
				if (tempDist == curDist - 1)
				{
					shortest.add(g.members[end.first.fnum].name);
					curDist = tempDist;
					end = g.members[end.first.fnum];
					break;
				}
				end.first = end.first.next;
			} */
		}
		
		for (int l = shortest.size()-1; l >= 0;l--)
		{
			check.add(shortest.get(l));
		}
		return check;
	}
	

	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) 
	{
		ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
		boolean finish = false;
		boolean [] check = new boolean[g.members.length];
		Arrays.fill(check, false);
		if (!finish)
		{
			for(int i = 0; i < g.members.length; i++)
			{
				if (g.members[i].student && g.members[i].school.equals(school) && !check[i])
				{
					ArrayList<String> group = new ArrayList<String>();
					int [] value = new int [g.members.length];
					Arrays.fill(value, -1);
					Person start = g.members[i];
					Queue<Person> path = new Queue<Person>();
					check[i] = true;
					value[i] = 0;
					path.enqueue(start);
					while (!path.isEmpty())
					{
						Person node = path.dequeue();
						//int nodeLoc = g.map.get(node.name);
						Friend ptr = node.first;
						while(ptr!= null)
						{
							if(value[ptr.fnum] == -1)
							{
								int nextIndex = ptr.fnum;
								Person nextPerson = g.members[nextIndex];
	//							System.out.println("Enter " + nextPerson.name);
								if(nextPerson.student && nextPerson.school.equals(school))
								{
									value[ptr.fnum] = 0;
									check[ptr.fnum] = true;
									path.enqueue(g.members[ptr.fnum]);
	//								System.out.println("Enter " + nextPerson.name);
								}
							}
							ptr = ptr.next;
						}
					}
					for(int k = 0; k < value.length;k++)
					{
						if (value[k] == 0)
						{
							group.add(g.members[k].name);
						}
	//					System.out.println("Person: " + k + " The distance to " + g.members[k].name + " is " + value[k]);
					}
					groups.add(group);
				}
			}
		}
		if(groups.size() == 0)
		{
			return null;
		}
		
		return groups;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) 
	{
		ArrayList<String> connects = new ArrayList<String>();
		int[] dfsnum = new int[g.members.length];
		int[] back = new int[g.members.length];
		int[] visit = new int[g.members.length];
		int counter = 1;
		int[] isConnector = new int[g.members.length];
		Arrays.fill(dfsnum, 0);
		Arrays.fill(back, 0);
		Arrays.fill(visit, 0);
		Arrays.fill(isConnector, 0);
		
		for(int i = 0; i < g.members.length; i++)
		{
			if(visit[i] == 0)
			{
				isConnector[i] = 2;
				Person firstPerson = g.members[i];
				boolean first = true; 
				dfsMethod(firstPerson, dfsnum, back, visit, isConnector, g, counter, first);
			}
		}
		
		for(int j = g.members.length-1; j > 0; j--)
		{
			if(isConnector[j] == 1)
			{
				Person currPerson = g.members[j];
				String currName = currPerson.name;
				//System.out.println(currName);
				connects.add(currName);
			}
		}
		return connects;
	}

	private static void dfsMethod(Person firstPerson, int[] dfsnum, int[] back, int[]visit, int[] isConnector,Graph g, int counter, boolean first)
	{
		Person currPerson = firstPerson;
		String currName = currPerson.name;
		//System.out.println(currName);
		int index = g.map.get(currName);
		//System.out.println(index);
		Friend currNode = currPerson.first;
		dfsnum[index] = counter;
		back[index] = counter;
		counter++;
		visit[index] = 1;
		
		while(true)
		{
			if(currNode!= null)
			{
				int newIndex = currNode.fnum;
				if(visit[newIndex] == 0)
				{
					Person nextPerson = g.members[newIndex];
					dfsMethod(nextPerson, dfsnum, back, visit, isConnector, g, counter, first);
					if(dfsnum[index] > back[newIndex])
					{
						back[index] = Math.min(back[index], back[newIndex]);
					}
					else
					{
						if (isConnector[index] == 0)
						{
							isConnector[index] = 1;
						}
						else
						{
							if(isConnector[index] == 2)
							{
								isConnector[index] = 0;
							}
						}
					}
					//System.out.println(isConnector[index]);
				}
				else
				{
					back[index] = Math.min(back[index], dfsnum[newIndex]);
				}
			}
			else
			{
				break;
			}
			currNode = currNode.next;
		}
	}
}