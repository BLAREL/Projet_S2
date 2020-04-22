/**
 * 
 */
package isen.MetaHeuristic;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
<<<<<<< HEAD
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator.Fitness;
=======
import java.util.Map;

import isen.Analyzer.StationsAnalyzer;
import isen.Utils.BingMapAPI;
import isen.Utils.MatrixAlterator;
import isen.Utils.MatrixCoordinateTranscriptor;
import isen.Utils.MatrixLoader;
import javafx.util.Pair;
>>>>>>> master

/**
 * @author Alexandre
 *
 */
public class Application {
	

			
	
			
		
	static BufferedWriter out = null;
		
	public static void main( String[] args) {
		
		// test de toutes les méthodes de encoding
		///Commentaire de test de push
		
		Encoding enc = new Encoding();		 
      float matrice2[][];
		
		LocalSearch ls= new LocalSearch();		
		
		Annealing test = new Annealing();
		
		Fitnes obj = new Fitnes();
		float parcouretudiant = 0;
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("que voulez vous choiisir comme alpha [0,1]  ");
		
		float alpha = sc.nextFloat();
		
		ArrayList<SolutionGlobale> listesolutionglobale = new ArrayList<SolutionGlobale>(); 
//Ici je fais ma boucle ou je genere a chaque iteration une solution globale
		

		
		
		Scanner sc1 = new Scanner(System.in);
		System.out.println("choisissez le nombre de cluster  ");
			AlgoKMeans kmean = new AlgoKMeans();
			ArrayList<Position> listpos = new ArrayList();
			Position pos = new Position();
			Position_bus station = new Position_bus();
			int dim=2;
			int nbSamples = 100;
			kmean.createSamples(nbSamples, dim);
			int nbClusters = sc1.nextInt();
		
			for (int z =0 ; z< 10 ; z++) {
			
			Position etudiant = new Position();
			
			parcouretudiant =0;
			

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
					pos.x =  (float) kmean.clusters.get(nb).centroid.getValue(0) ;
					pos.y =  (float) kmean.clusters.get(nb).centroid.getValue(1) ;
					System.out.println("vas y augmente ta taille batard   " + kmean.clusters.get(nb).dataSet.size());
					for (int g=0 ; g<kmean.clusters.get(nb).dataSet.size() ; g++) {
						etudiant.x =  (float) kmean.clusters.get(nb).dataSet.get(g).normValues[0];
						etudiant.y = (float) kmean.clusters.get(nb).dataSet.get(g).normValues[1];
						parcouretudiant += (float) Math.sqrt(    Math.pow(etudiant.x -pos.x, 2)  +  Math.pow(etudiant.y -pos.y, 2));
						
					}
					listpos.add( pos);
					
					
					pos= new Position();
					etudiant = new Position();
					nb++;
					error += cluster.moyDist;
					
				}
			}
			error = error/(nb*dim);
			System.out.println("gobal error= " + String.format(Locale.ENGLISH, "%.2f", (error*100)) + " % ");
			
	
			System.out.println("distance totale des etudiants   " + parcouretudiant);
			
			kmean.clusters.clear();
			kmean.dataSet.clear();
		
			
			


	       matrice2 = station.matrixOfDist(listpos);
	       
	       
	       
	       ArrayList<Integer> masolu1 = new ArrayList<Integer>(); 
	       ArrayList<Integer> masolu2 = new ArrayList<Integer>(); 
	       
	       
	       
	       
	       
	       
	       
	       
	       	ArrayList<Integer> s = new ArrayList<Integer>();
	       
	    	   
	    	   masolu1 = ls.ls_tsp_float(nbClusters, 50, matrice2); 
	    	   s = enc.permutationencoding(nbClusters);
	    	   masolu2 = test.annealingtsp_float(s, 100, matrice2);
	    	   
	    	   SolutionGlobale solutionglobale = null;
	      
<<<<<<< HEAD
	    	   if (obj.objectivefonction_TSP_float(masolu1, matrice2) > obj.objectivefonction_TSP_float(masolu2, matrice2) ){
	    		    solutionglobale = new SolutionGlobale(alpha ,masolu2,parcouretudiant,  obj.objectivefonction_TSP_float(masolu2, matrice2),listpos);
	    		   listesolutionglobale.add(solutionglobale);
	    	   }
	       
	    	   else {
	    		    solutionglobale = new SolutionGlobale(alpha ,masolu1,parcouretudiant,  obj.objectivefonction_TSP_float(masolu1, matrice2),listpos);
	    		   listesolutionglobale.add(solutionglobale);
	    		   
	    	   }
System.out.println ("voici le pacourt des etudiants  " + parcouretudiant + "   une distance d'un itineraire   "+obj.objectivefonction_TSP_float(masolu1, matrice2) ) ;      
System.out.println ("voici 10 solutions globales avec alpha =  " + alpha + "    nombre de cluster =  "+nbClusters) ;
	       }
	       
			SolutionGlobale meilleursolutionglobale = null;
			int index1 =0;
	       float minimum = listesolutionglobale.get(0).objective;
	        for (int index = 0 ; index< listesolutionglobale.size() ; index++) {
	        	System.out.println(listesolutionglobale.get(index).objective);
	        	if (minimum > listesolutionglobale.get(index).objective) { 
	        		minimum = listesolutionglobale.get(index).objective;
	        	meilleursolutionglobale =  listesolutionglobale.get(index);
	        	}
       }
	        System.out.println("voici le minimum");
	        System.out.println(meilleursolutionglobale.objective);
	        
	        
	        
	        try {
	             
	            //    1) Instanciation de l'objet
	               out = new BufferedWriter(new FileWriter(new File("itineraire.txt")));
	             
	               try {
	                 out.write("parcourt de l'etudiant  : "+  "distance_itineraire  :   " + "resultat final  :" + "\n");
	            //         2) Utilisation de l'objet
	            	   
	                    out.write(meilleursolutionglobale.parcourtdeletudiant + "   ");
	                    out.write(meilleursolutionglobale.distance_itineraire + "   ");
	                    out.write(meilleursolutionglobale.objective + " \n ");
	                    out.write("coordonnees des stations  : \n");
	                    for (int o=0 ; o< meilleursolutionglobale.station.size() ; o++) {
	                    out.write("["+meilleursolutionglobale.station.get(o).x+";"+meilleursolutionglobale.station.get(o).y+"]  " ); }
	            	   out.write("\n itineraire  : \n  ["  );
	            	   for (int u=0 ; u< meilleursolutionglobale.itineraire.size() ; u++) {
	            		   out.write(meilleursolutionglobale.itineraire.get(u)+";"   ); 
	            		   
	            	   }
	            	   out.write("] \n");
	            	   out.write("nombre de cluster :    "+ nbClusters);
	                 
	               } finally {
	                 
	                    // 3) Libération de la ressource exploitée par l'objet
	                   out.close();
	                 
	               }
	             
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
=======
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
	      
>>>>>>> master
	}
}
