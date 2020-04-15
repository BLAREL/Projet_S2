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
		///Commentaire de test de push
		
		Encoding enc = new Encoding();
		
		 ArrayList<Integer> s = new ArrayList<Integer>();
		 
		 ArrayList<Integer> sol = new ArrayList<Integer>();
		 
		 AntColony fourmi = new AntColony();
		 
		Tabu t = new Tabu();
		
		LocalSearch ls= new LocalSearch();		
		
		Annealing test = new Annealing();

			
			
			AlgoKMeans kmean = new AlgoKMeans();
			ArrayList<Position> listpos = new ArrayList();
			Position pos = new Position();
			position_bus station = new position_bus();
			
			
			
			
			
			
			int dim=2;
			int nbSamples = 200;
			kmean.createSamples(nbSamples, dim);
			int nbClusters = 20;
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
			

			

	        float matrice2[][];
	       matrice2 = station.matrixOfDist(listpos);
	       
	       
	       
	       
	       
	       ArrayList<Integer> masolu = new ArrayList<Integer>(); 
	       
	       masolu = ls.ls_tsp_float(20, 50, matrice2); 
	       s=enc.permutationencoding(20);
	      
	      test.annealingtsp_float(s, 100, matrice2);
	      
	      System.out.println("local search "+masolu );
	      System.out.println("fitness associee   "+obj.objectivefonction_TSP_float(masolu, matrice2));
	}
}
