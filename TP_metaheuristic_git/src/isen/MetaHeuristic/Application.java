/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import isen.Utils.BingMapAPI;
import isen.Utils.MatrixLoader;
import javafx.util.Pair;

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
	      //System.out.println("fitness associee   "+obj.objectivefonction_TSP_float(masolu, matrice2));
	
	      ArrayList<String> coordinates = new ArrayList<>();
	      coordinates.add("50.634483,3.048296");
	      coordinates.add("50.631694,3.042437");
	      coordinates.add("50.631029,3.043061");
	      coordinates.add("50.6318837,3.0396486");
	      coordinates.add("50.635309,3.046764");
	      coordinates.add("50.635433,3.047143");
	      coordinates.add("50.6351554,3.0465156");
	      
	      ///Example of utilization of the MatrixLoader class.
	      //Pair<float[][], float[][]> matrixes = MatrixLoader.getMatrixesFromAPI(coordinates, true);
	      //float[][] matrix = matrixes.getKey();
	      //MatrixLoader.SaveInformationForMatrixCreation(coordinates, matrix, "testMatrix");
	      
	      
	      //Load from the data/testMatrix.csv file
	      float[][] matrix = MatrixLoader.LoadMatrixInformation("testMatrix");
	      
	      ArrayList<Integer> stationIndexes = AlgoKMedoid.execute(matrix, 2);

	      System.out.println("\n\nStations found from the k-medoid algorithm : ");
	      
	      for(int i = 0; i < stationIndexes.size(); i++)
	    	 System.out.println("Station index number " + Integer.toString(i) + " : " + Integer.toString(stationIndexes.get(i)));
	}
}
