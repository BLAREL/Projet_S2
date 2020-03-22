/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Alexandre
 *
 */
public class Application {
	
	public static void main( String[] args) {
		
		// test de toutes les méthodes de encoding
		
		
		Encoding enc = new Encoding();
		
		 ArrayList<Integer> s = new ArrayList<Integer>();
		 
		 ArrayList<Integer> sol = new ArrayList<Integer>();
		 
		 AntColony fourmi = new AntColony();
		 
		Tabu t = new Tabu();
		
		LocalSearch ls= new LocalSearch();		
		
		Annealing test = new Annealing();
		
		//solution.binaryencoding(10);
		
		//truc.discretencoding(10,2,9);
		
		//truc.realencoding(10);
		
		enc.permutationencoding(10);
		
		//List <ArrayList<Integer>>L = new ArrayList<ArrayList<Integer>>();
		// test de toutes les méthodes de voisinage chacune associées à son encoding
		
		//L=solution.Hammingneighborhood(solution.binaryencoding(3));
		
		//truc.Discretneighborhood(truc.discretencoding(4, 2, 5), 2, 5);
		
		//truc.permutationneighborhood(truc.permutationencoding(3));
		
		//ArrayList<Integer> s = ls.ls(5,50);
		
		//Fitness obj = new Fitness();
		
		
		//System.out.println("best sol found " +s);
		//System.out.println(obj.objectivefunction_equation(s));
		
		int [][] matrice= {
				{9999 ,   3 ,   5  , 48 ,  48 ,   8  ,  8  ,  5  ,  5  ,  3  ,  3  ,  0  ,  3  ,  5  ,  8  ,  8  ,  5},
				{ 3 ,9999  ,  3 ,  48 ,  48  ,  8  ,  8  ,  5  ,  5  ,  0  ,  0  ,  3  ,  0  ,  3  ,  8  ,  8  ,  5},
				{5  ,  3 ,9999  , 72 ,  72 ,  48  , 48  , 24 ,  24  ,  3  ,  3  ,  5  ,  3  ,  0 ,  48 ,  48  , 24},
				{48 ,  48 ,  74, 9999  ,  0  ,  6   , 6  , 12 ,  12 ,  48 ,  48  , 48  , 48 ,  74  ,  6 ,   6 ,  12},
				{48 ,  48  , 74  ,  0 ,9999  ,  6  ,  6  , 12 ,  12 ,  48 ,  48  , 48 ,  48 ,  74 ,   6  ,  6 ,  12},
				{8  ,  8  , 50   , 6 ,   6, 9999 ,   0  ,  8 ,   8 ,   8 ,   8 ,   8  ,  8 ,  50 ,   0  ,  0 ,   8},
				{8 ,   8  , 50   , 6  ,  6 ,   0 ,9999 ,   8  ,  8 ,   8 ,   8 ,   8 ,   8 ,  50  ,  0  ,  0 ,   8},
				{5 ,   5 ,  26   ,12  , 12  ,  8  ,  8 ,9999 ,   0  ,  5  ,  5  ,  5   , 5  , 26  ,  8  ,  8 ,   0},
				{5 ,   5 ,  26 ,  12 ,  12  ,  8 ,   8   , 0 ,9999  ,  5  ,  5  ,  5  ,  5  , 26 ,   8 ,   8 ,   0},
				{3  ,  0  ,  3 ,  48 ,  48  ,  8 ,   8   , 5   , 5 ,9999 ,   0 ,   3 ,   0    ,3 ,   8 ,   8  ,  5},
				{3  ,  0 ,   3 ,  48 ,  48  ,  8  ,  8  ,  5   , 5  ,  0 ,9999,    3  ,  0  ,  3  ,  8  ,  8  ,  5},
				{0 ,   3 ,   5  , 48  , 48  ,  8  ,  8 ,   5 ,   5 ,   3 ,   3 ,9999 ,   3 ,   5 ,   8 ,   8 ,   5},
				{3    ,0 ,   3 ,  48 ,  48 ,   8  ,  8 ,   5 ,   5 ,   0 ,   0 ,   3 ,9999  ,  3 ,   8 ,   8  ,  5},
				{5 ,   3   , 0  , 72   ,72  , 48  , 48 ,  24 ,  24 ,   3 ,   3 ,   5  ,  3 ,9999 ,  48 ,  48 ,  24},
				{8    ,8 ,  50 ,   6  ,  6   , 0,    0,    8,    8  ,  8 ,   8 ,   8  ,  8  , 50, 9999  ,  0 ,   8},
				{8 ,   8 ,  50   , 6,    6,    0 ,   0  ,  8   , 8  ,  8    ,8 ,   8 ,   8  , 50   , 0 ,9999 ,   8},
				{5  ,  5 ,  26  , 12,   12 ,   8  ,  8   , 0    ,0 ,   5 ,   5  ,  5  ,  5 ,  26 ,   8  ,  8 ,9999},
			
		};
		
		//System.out.println(matrice[s.get(3)][s.get(8)]);

		// problème du tsp resolu pour le vecteur (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17)
		//sol = ls.ls_tsp(17, 50, matrice);
		
		  
		//System.out.println("voici le best");
	//	System.out.println(sol);
		
		
		//test.annealing(5, 400);
		
		//test.annealingtsp(17, 1000, matrice);
		
		//ls.ls_tsp(17, 2000, matrice);
		
		
		//t.TS_binaire_short_memory( enc.binaryencoding(6)) ;
			float [][] feromone = {
			
			{(float) 0.3 , (float) 2.4 , (float) 1.3 , (float) 8.9},
			{(float) 2.1 , (float) 3.1 , (float) 1.9 , (float) 4.3},
			{(float) 0.9 , (float) 2.9 , (float) 1.9 , (float) 8.9},
			{(float) 2.5 , (float) 3.5 , (float) 1.5 , (float) 4.5},
	};
			
			int [][] distance = {
					
					
					{3 , 4 ,  3 , 9},
					{ 21 ,  31 ,  19 ,43},
					{43 , 31 , 19 , 21},
					{9 ,3 ,4 ,3 },
			};
	
			//fourmi.antcolony(feromone, (float)0.5,(float) 0.5, 4, distance, (float)0.3, 100);
			
			
			
			
			
			
			
			
			
			
			AlgoKMeans kmean = new AlgoKMeans();
			ArrayList<Position> listpos = new ArrayList();
			Position pos = new Position();
			position_bus station = new position_bus();
			
			
			
			
			
			
			int dim=2;
			int nbSamples = 100;
			kmean.createSamples(nbSamples, dim);
			int nbClusters = 10;
			System.out.println("classification of " +nbSamples+ " samples of dimension " + dim + " into " + nbClusters + " clusters.");
			long start = System.currentTimeMillis();
			kmean.initialize(nbClusters, dim);
			kmean.kMeanCluster();
			long end = System.currentTimeMillis();
			System.out.println("after " + (end-start) + " ms ");
			kmean.clusters.forEach(c->c.computeStats());
			// Print out clustering results.
			double error = 0;
			int nb=0;
			for(Cluster cluster:kmean.clusters)
			{
				if (cluster.dataSet.size()>0)
				{
					System.out.println(cluster);
					pos.x =  (float) kmean.clusters.get(nb).centroid.getValue(0) ;
					System.out.println("ICI    "+pos.x);
					pos.y =  (float) kmean.clusters.get(nb).centroid.getValue(1) ;
					listpos.add( pos);
					pos= new Position();
					nb++;
					error += cluster.moyDist;
					
				}
			}
			error = error/(nb*dim);
			System.out.println("gobal error= " + String.format(Locale.ENGLISH, "%.2f", (error*100)) + " % ");
			
			
			for (int k=0; k<listpos.size();k++) {
				System.out.print( listpos.get(k).x + "  ");
			}
			

	        float matrice2[][];
	       matrice2 = station.matrixOfDist(listpos);
	       
	        
	       ls.ls_tsp_float(10, 50, matrice2); 
	
	}
}
