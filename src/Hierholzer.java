import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.util.Random;

public class Hierholzer {
	static private Graph graph;
	private LinkedList<Integer> eulerianCycle;
	static private String path;
	
	static void slow(){
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Color getRadomColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}

	private void InsertCycle(LinkedList<Integer> newCycle, int index){
		this.eulerianCycle.addAll(index, newCycle);
	}
	
	private static LinkedList<Integer> SearchSimpleCycle(int startVertice){
		LinkedList<Integer> list = new LinkedList<Integer>();
		int currentVertice = -1;
		LinkedList<Edge> neighbours = graph.getNeighbours(startVertice);
		Edge edge = neighbours.get(0);
		Color color = getRadomColor();
		
		while(startVertice != currentVertice) {
			list.add(edge.getU());
			currentVertice = edge.getV();
			graph.markNode(currentVertice, color);
			graph.removeEdge(edge);
			neighbours = graph.getNeighbours(currentVertice);
			if(!neighbours.isEmpty())
				edge = neighbours.getFirst();	
			slow();
		}
		list.add(startVertice);
		System.out.println(CycleToString(list));
		return list;
	}
	
	public LinkedList<Integer> SearchEulerianCycle(int startVertice){
		if(!graph.isEulerian())
			graph.turnEulerian();
		int i = startVertice;
		LinkedList<Integer> cycle;
		eulerianCycle = SearchSimpleCycle(startVertice);
		
		while(graph.existEdges())
		{
			if(graph.getNeighbours(i).isEmpty())
			{
				i++;
			}
			else
			{
				cycle = SearchSimpleCycle(i);
				if(cycle != null)
					InsertCycle(cycle, i);
			}
		}
	
		return eulerianCycle;
	}

/*******************************************************************************************/

	public static String CycleToString(LinkedList<Integer> cycle){
		String str = "";
		for (Integer integer : cycle) {
			str += integer +" -> ";
		}
		return str.substring(0, str.length()-4);
	}	

	public static void test1(){
		Graph graph = new Graph(5);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(0,4,5);
		graph.addEdge(4,1,5);
		graph.addEdge(4,2,5);
		graph.addEdge(4,3,5);

		System.out.println("Lista de adjacencia do grafo:");
		System.out.println(graph.toString());		
	}

	public static void test2(){
		Graph graph = new Graph(5);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(0,4,5);
		graph.addEdge(4,1,5);
		graph.addEdge(4,2,5);
		graph.addEdge(4,3,5);


		System.out.println("Lista de adjacencia do grafo ANTES da remocao:");
		System.out.println(graph.toString());
		System.out.println();

		graph.removeEdge(new Edge(1,4,5));
		graph.removeEdge(new Edge(0,4,5));
		graph.removeEdge(new Edge(0,2,5));

		System.out.println("Lista de adjacencia do grafo DEPOIS da remocao:");
		System.out.println(graph.toString());		
	}

	public static void test3(){
		Graph graph = new Graph(5);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(0,4,5);
		graph.addEdge(4,1,5);
		graph.addEdge(4,2,5);
		graph.addEdge(4,3,5);

		System.out.println("Vertices vizinhos do vertice 0:");
		for(Edge e : graph.getNeighbours(0)){
			System.out.print(e.getV()+" ");
		}
		System.out.println();

		System.out.println("Vertices vizinhos do vertice 2:");
		for(Edge e : graph.getNeighbours(2)){
			System.out.print(e.getV()+" ");
		}
		System.out.println();;
	}

	public static void test4(){
		Graph graph = new Graph(5);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(0,4,5);
		graph.addEdge(4,1,5);
		graph.addEdge(4,2,5);
		graph.addEdge(4,3,5);

		System.out.println("Lista de adjacencia do grafo 1:");
		System.out.print(graph.toString());
		System.out.println("Euleriano:" + graph.isEulerian() + "\n");

		graph = new Graph(6);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(1,4,5);
		graph.addEdge(1,2,5);
		graph.addEdge(2,3,5);
		graph.addEdge(2,5,5);
		graph.addEdge(3,5,5);
		graph.addEdge(4,5,5);

		System.out.println("Lista de adjacencia do grafo 2:");
		System.out.print(graph.toString());
		System.out.println("Euleriano: " + graph.isEulerian());
	}
	
	public static void test5(int startVertice){
		graph = new Graph(5);
		graph.addEdge(0,1,5);
		graph.addEdge(0,2,5);
		graph.addEdge(0,3,5);
		graph.addEdge(0,4,5);
		graph.addEdge(4,1,5);
		graph.addEdge(4,2,5);
		graph.addEdge(4,3,5);

		LinkedList<Integer> cycle =  SearchSimpleCycle(startVertice);
		System.out.println("Ciclo Simples Encontrado: comecando de " + startVertice);
		System.out.println(CycleToString(cycle));
	}
	
	public static void test6(int startVertice)
	{
		Hierholzer hierholzer = new Hierholzer ( ) ;
		//graph = new Graph("grafo1.csv" , "white.png" , "Roteamento ") ; // Grafo nao Euleriano
		graph = new Graph(path + "grafo2.csv" , path + "white.png" , "Roteamento" ) ; // Grafo Euleriano
		System.out.println();
		LinkedList<Integer> eulerianCycle = hierholzer.SearchEulerianCycle(startVertice);
		System.out.println("Ciclo Final :") ;
		System.out.println( Hierholzer.CycleToString(eulerianCycle)) ;
		Graph graph2 = new Graph( graph.getGraphics() , path + "white.png" , "Rota Final " ) ;
		for(Integer i : eulerianCycle){
			slow();
			Color color = Hierholzer.getRadomColor();
			graph2.markNode(i, color);
		}
	}

	public static void test7(int startVertice)
	{
		Hierholzer hierholzer = new Hierholzer( ) ;
		// graph = new Graph(path + "grafoMapa1.csv", path + "mapa1.png", "Roteamento");
		graph = new Graph(path + "grafoMapa2.csv" , path + "mapa2.png", "Roteamento");
		System.out.println();
		LinkedList<Integer> eulerianCycle = hierholzer.SearchEulerianCycle(0) ;
		System.out.println("Ciclo Final: ") ;
		System.out.println(Hierholzer.CycleToString(eulerianCycle));
		//Graph graph2 = new Graph(graph.getGraphics(), path + "mapa1.png", "Rota Final");
		Graph graph2 = new Graph(graph.getGraphics() ,path + "mapa2.png", "Rota Final");
		for ( Integer i : eulerianCycle ){
			slow() ;
			Color color = Hierholzer.getRadomColor() ;
			graph2.markNode(i, color) ;
		}
	}
	public static void main(String[] args) {
		path = "/home/hanoch/repo/ED/carteiroChines/src/";
		//test1();
		//test2();
		//test3();
		//test4();
		//test5(0); Quebrado
		//test6(0);
		test7(0);
	}
}
