/**
 * 
 */
package isen.MetaHeuristic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre
 *
 */
public class LocalSearch {
	


	

	
		public ArrayList<Integer> selection_best(List <ArrayList<Integer>>L ) {
			Fitnes f = new Fitnes();
			
			int max = f.objectivefunction_equation(L.get(0));
			int index=0;
			for (int k=1; k<L.size(); k++) {
				if (max < f.objectivefunction_equation(L.get(k)) ){
					
					max = f.objectivefunction_equation(L.get(k));
					index=k;
				}
			
					}
			return( L.get(index));
		
		}	
		
	public ArrayList<Integer>ls(int sol_size , int maxit){

		Encoding solution = new Encoding();
		solution.binaryencoding(sol_size);

		Fitnes f = new Fitnes();
		
		ArrayList<Integer> s = solution.binaryencoding(sol_size);
		int max = f.objectivefunction_equation(s);
		
		
		//System.out.println(max);
		
		
		
		List <ArrayList<Integer>>L;
		int cpt =0;
		while (cpt < maxit) {
			
			System.out.println("iteration " + cpt);
			
			L =   solution.Hammingneighborhood(s) ;
			ArrayList<Integer>temp_sol=selection_best(L);
			
			//System.out.println(max);
		    
			if (max > f.objectivefunction_equation(temp_sol)) {//optimum local
				break;
				
			}
			
			s=temp_sol;
			max=f.objectivefunction_equation(s);
			cpt++;
			
	}
		
		
		
		return s;
	}
	
	
	
	
	public ArrayList<Integer> selection_parcours_best(List <ArrayList<Integer>>L , int matrice_distance[][]) {
		Fitnes f = new Fitnes();
		
		int min = f.objectivefonction_TSP( L.get(0), matrice_distance);
		int index=0;
		for (int k=1; k<L.size(); k++) {
			if (min > f.objectivefonction_TSP(L.get(k), matrice_distance)){
				
				min = f.objectivefonction_TSP(L.get(k), matrice_distance);
				index=k;
			}
		
				}
		return( L.get(index));
	
	}	
	
	public ArrayList<Integer> ls_tsp(int sol_size , int maxit, int matrice_distance[][]){
		
		
		Encoding solution = new Encoding();

		Fitnes f = new Fitnes();
		
		ArrayList<Integer> s = solution.permutationencoding(sol_size);
		int min = f.objectivefonction_TSP(s, matrice_distance);
		
		
		List <ArrayList<Integer>>L;
		int cpt =0;
		while (cpt < maxit) {
			
			System.out.println("iteration " + cpt);
			System.out.println(s + "    distance totale " + f.objectivefonction_TSP(s, matrice_distance));
			
			L =   solution.permutationneighborhood(s) ;
			ArrayList<Integer>temp_sol=selection_parcours_best(L, matrice_distance);
			
			//System.out.println(max);
		    
			if (min < f.objectivefonction_TSP(temp_sol, matrice_distance)) {//optimum local
				break;
				
			}
			
			s=temp_sol;
			min=f.objectivefonction_TSP(s,matrice_distance);
			cpt++;
			
	}
		
		
		
		return s;
		
		

	}
	
	
	
	public ArrayList<Integer> selection_parcours_best(List <ArrayList<Integer>>L , float matrice_distance[][]) {
		Fitnes f = new Fitnes();
		
		float min = f.objectivefonction_TSP_float( L.get(0), matrice_distance);
		int index=0;
		for (int k=1; k<L.size(); k++) {
			if (min > f.objectivefonction_TSP_float(L.get(k), matrice_distance)){
				
				min = f.objectivefonction_TSP_float(L.get(k), matrice_distance);
				index=k;
			}
		
				}
		return( L.get(index));
	
	}	
	
	public ArrayList<Integer> ls_tsp_float(int sol_size , int maxit, float matrice_distance[][]){
		
		
		Encoding solution = new Encoding();

		Fitnes f = new Fitnes();
		
		ArrayList<Integer> s = solution.permutationencoding(sol_size);
		float min = f.objectivefonction_TSP_float(s, matrice_distance);
		
		
		List <ArrayList<Integer>>L;
		int cpt =0;
		while (cpt < maxit) {
			
			System.out.println("iteration " + cpt);
			System.out.println(s + "    distance totale " + f.objectivefonction_TSP_float(s, matrice_distance));
			
			L =   solution.permutationneighborhood(s) ;
			ArrayList<Integer>temp_sol=selection_parcours_best(L, matrice_distance);
			
			//System.out.println(max);
		    
			if (min < f.objectivefonction_TSP_float(temp_sol, matrice_distance)) {//optimum local
				break;
				
			}
			
			s=temp_sol;
			min=f.objectivefonction_TSP_float(s,matrice_distance);
			cpt++;
			
	}
		
		
		
		return s;
		
		

	}

}
