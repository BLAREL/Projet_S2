/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import isen.Analyzer.StationsAnalyzer;
import isen.Utils.BingMapAPI;
import isen.Utils.MatrixAlterator;
import isen.Utils.MatrixCoordinateTranscriptor;
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
	            
	      
	      double[][] coordinateMatrix = MatrixLoader.LoadMatrixInformation("CoordinatesList");
	      coordinateMatrix = MatrixAlterator.rotateMatrix(coordinateMatrix);
	      ArrayList<String> coordinates = MatrixCoordinateTranscriptor.toArrayString(coordinateMatrix);
	      
	      
	      ///Use the API to get the coordinates' matrix, and save it.
	      /*
	      long startTimer = System.currentTimeMillis();
	      Pair<double[][], double[][]> matrixesWalking = MatrixLoader.getMatrixesFromAPI(coordinates, true);
	      long endTimer = System.currentTimeMillis();
	      
	      System.out.println("API time for " + Integer.toString(matrixesWalking.getKey().length) + " addresses : " + Long.toString(endTimer - startTimer) + " ms.");
	      
	      MatrixLoader.SaveInformationForMatrixCreation(coordinates, coordinates, matrixesWalking.getKey(), "75_Walking_TravelDistance");
	      MatrixLoader.SaveInformationForMatrixCreation(coordinates, coordinates, matrixesWalking.getValue(), "75_Walking_TravelDuration");
	      double[][] matrix = matrixesWalking.getKey();
	      */
	      
	      /*
	      Pair<double[][], double[][]> matrixesDriving = MatrixLoader.getMatrixesFromAPI(coordinates, false);
	      MatrixLoader.SaveInformationForMatrixCreation(coordinates, coordinates, matrixesDriving.getKey(), "75_Driving_TravelDistance");
	      MatrixLoader.SaveInformationForMatrixCreation(coordinates, coordinates, matrixesDriving.getValue(), "75_Driving_TravelDuration");
	      double[][] matrixDrivingDist = matrixesDriving.getKey();
	      */

	      ///Load from the data/testMatrix.csv file
	      double[][] matrix = MatrixLoader.LoadMatrixInformation("75_Walking_TravelDistance");
	      
	      int k = 6;
	      long startTimer = System.currentTimeMillis();
	      ArrayList<Integer> stationIndexes = AlgoKMedoid.execute(matrix, k);
	      long endTimer = System.currentTimeMillis();
	      
	      System.out.println("k-medoid time for " + Integer.toString(matrix.length) + " addresses : " + Long.toString(endTimer - startTimer) + " ms.");
	      
	      ArrayList<String> stationCoordinates = new ArrayList<>();

	      System.out.println("\n\nStations found from the k-medoid algorithm : ");
	      
	      for(int i = 0; i < stationIndexes.size(); i++)
	      {
	    	  System.out.println("Station index number " + Integer.toString(i) + " : " + Integer.toString(stationIndexes.get(i)));
	    	  stationCoordinates.add(coordinates.get(stationIndexes.get(i)));
	      }
	      		
	      ///Take the corresponding addresses/stations matrix from the initial addresses/addresses
	      ///matrix using the result of the k-medoid algorithm.
	      double[][] stationsAddressesMatrix = MatrixAlterator.subMatrix(matrix, stationIndexes, true);
	      
	      ///Save the addresses/stations matrix.
	      //MatrixLoader.SaveInformationForMatrixCreation(coordinates, stationCoordinates, stationsAddressesMatrix, "75_Walking_TravelDistance_6_Stations");
	     
	      //double[][] stationsAddressesMatrix = MatrixLoader.LoadMatrixInformation("40WalkingTravelDistance_4Stations");
	      
	      
	      
	      ///Report creation.
	      Map<String,String> analyzeBasic = StationsAnalyzer.analyzeBasicInformation(stationsAddressesMatrix);
	      Map<String,String> analyzeDistances = StationsAnalyzer.analyzeWalkingDistance(stationsAddressesMatrix, "km");
	      Map<String,String> analyzeStations = StationsAnalyzer.analyzeStationEfficiency(stationsAddressesMatrix);
	      String report = StationsAnalyzer.createReport(analyzeBasic, "fr");
	      report += StationsAnalyzer.createReport(analyzeDistances, "fr");
	      report += StationsAnalyzer.createReport(analyzeStations, "fr");
	      
	      System.out.println(report);
	      
	}
}
