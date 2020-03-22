/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;

/**
 * @author Alexandre
 *
 */
public class Fitness {


	public int objectivefunction_equation(ArrayList<Integer>solution){
		
		  
		  int somme=0;
		  int fonction = 0;
		  
		  for (int i=0; i< solution.size();i++) {
			  
			  somme = (int)( somme + (Math.pow(2, solution.size()-i-1)*solution.get(i)));
			  
		  }
		  
		fonction = somme*somme*somme -60*somme*somme + 900*somme;
		
		System.out.println("*** "+solution + " decimal "+ somme + "  fit "+ fonction);
		
		return fonction;
		
		
		
		
	}
	
	
	public int objectivefonction_TSP(ArrayList<Integer>solution, int [][]matrice_distance) {
		
		int distance=0;
		
		for (int i=0; i< solution.size() -1; i++) {
			
			distance += matrice_distance[solution.get(i) -1 ][solution.get(i+1) -1]; 
			
		}
		distance += matrice_distance[solution.get(solution.size()-1)-1][solution.get(0)-1];
		
		return distance;
		
		
	}
	
	
	public float objectivefonction_TSP_float(ArrayList<Integer>solution, float [][]matrice_distance) {
		
		float distance= 0;
		
		for (int i=0; i< solution.size() -1; i++) {
			
			distance += matrice_distance[solution.get(i) -1 ][solution.get(i+1) -1]; 
			
		}
		distance += matrice_distance[solution.get(solution.size()-1)-1][solution.get(0)-1];
		
		return distance;
		
		
	}
	
}