/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre
 *
 */
public class AntColony {
	
	
	
	public float proba(int i, int j, float T [][], float a , float b , int size, int matrice_des_distances [][]) {
		
		
		float p =0;
		float somme =0;
		
		for (int k=0 ; k< size   ; k++) {
			
			somme += (Math.pow(T[i][k], a) * Math.pow((1 / matrice_des_distances[i][k]),b )) ;
			
		}
		
		return  (float) (  (Math.pow(T[i][j], a) * Math.pow((1 / matrice_des_distances[i][j]),b ))  /somme  );
	}
	
	
	
	public ArrayList<Integer> antcolony ( float T [][], float a , float b , int size, int matrice_des_distances [][], float ro, int generation) {
		
		Encoding codage = new Encoding();
		Fitnes f = new Fitnes();
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> vecteur1 = new ArrayList<Integer>();
		ArrayList<Integer> bestsolution = new ArrayList<Integer>();
		bestsolution = codage.permutationencoding(size);
		int nombre_de_fourmis =4;
		List<ArrayList<Integer>> fourmiliere = new ArrayList<ArrayList<Integer>>(); 
		
		for (int d=0 ; d < generation ; d++) {
			for (int r=0 ; r< nombre_de_fourmis ; r++) {
			
		solution = codage.permutationencoding(size);
		float alea;
		int alea2;
		float probabilite;
		int j;
		int index =0;
		int i = solution.get(0);
		vecteur1.add(i);
		solution.remove(0);
		
		while (solution.size() != 0) {
			alea= (float) Math.random();
			j= solution.get(index);
			//System.out.println(i + "  " + j);
			probabilite = proba( i-1, j-1, T,a ,b ,size,matrice_des_distances);
			while (   (alea < probabilite)  ) {
				
				if (index == solution.size() -1 ) {
					alea= (float) Math.random();
					index = -1;
				}
				
				index++;
				j= solution.get(index);
				probabilite = proba( i-1, j-1, T,a ,b ,size,matrice_des_distances);
			}
			
			vecteur1.add(j);
			solution.remove(index);
			i=j;
			index=0;
			}
		
		fourmiliere.add(vecteur1);
		
		if ( f.objectivefonction_TSP(bestsolution, matrice_des_distances) > f.objectivefonction_TSP(vecteur1, matrice_des_distances)) {
			
			bestsolution = (ArrayList<Integer>) vecteur1.clone();
			}
		
		}
			//System.out.println(fourmiliere);
		
		for (int n=0 ; n< size ; n++) {
			
			for (int m=0 ; m< size ; m++) {
				
			T[n][m] = (1- ro) * T[n][m];	
				
			}
			
		}
		
		for (int fourmi =0 ; fourmi< nombre_de_fourmis ; fourmi++) {
			
			for (int h = 0; h< size ; h++) {
				
				T[fourmi][h] = T[fourmi][h] + f.objectivefonction_TSP(fourmiliere.get(fourmi) , matrice_des_distances);
				
				}
			
			}
		System.out.println( bestsolution);
		System.out.println( f.objectivefonction_TSP(bestsolution, matrice_des_distances));
		fourmiliere.clear();
		
		}
		System.out.println( bestsolution);
		System.out.println( f.objectivefonction_TSP(bestsolution, matrice_des_distances));
		return bestsolution ;
		
	}
	
}
