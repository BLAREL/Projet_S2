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
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator.Fitness;

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
	}
}
