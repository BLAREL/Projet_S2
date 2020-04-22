/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alexandre
 *
 */
public class Annealing {
	
	// lineaire
	
	public float linear(float To , float B , int i) {
		
		To = To - B*i;	
			
			return( To );
		}
	
	// geometric
	
		public float geometric(float T, float alpha) {
			
			
			
			
			return  (alpha * T);
			
		}
		
		
		public float logarithmic(float To , int i) {
			
			To =  (float) (To   /  Math.log(i)); 
			
			return To;
			
		}
		
		
		public float slowdecrease(float T , float B ,int i) {
			
			return ( T / 1 + B*T);
		}
		
		
		
	public ArrayList<Integer> annealing(int taillevecteur , float To){
		
		int gap;
		float T= To;
		float B = 9;
		Fitnes f = new Fitnes();
		Encoding solution = new Encoding();
		ArrayList<Integer> s = solution.binaryencoding(taillevecteur);
		
		ArrayList<Integer> best_s = (ArrayList<Integer>) s.clone() ;
		int best_fitness = f.objectivefunction_equation(best_s);
		int nb_voisin = solution.Hammingneighborhood(s).size() ;
		int alea = (int) (Math.random()* ( nb_voisin));
		ArrayList<Integer> s_bis = solution.Hammingneighborhood(s).get(alea);
		
		int i=0;
		while (T > 0) {
			
			
			for (int k=0 ; k< 10 ; k++) {
			System.out.println(s);
			gap = f.objectivefunction_equation(s_bis) - f.objectivefunction_equation(s);
			
			if(best_fitness < f.objectivefunction_equation(s_bis)) {
				best_s = (ArrayList<Integer>) s_bis.clone();
				best_fitness = f.objectivefunction_equation(s_bis);
			}
			
			if (gap > 0) {
				s= (ArrayList<Integer>) s_bis.clone();
			}
			else {
				float alea2 = (float)Math.random();
				if (alea2 < Math.exp( (-gap  ) / T )) {
					s= (ArrayList<Integer>) s_bis.clone();
				}
			}
			
			}
			
				i++;
				T= linear (To , B , i);
				alea = (int) (Math.random()* ( nb_voisin));
				System.out.println("temperature:  "+ T +"  alea   "+ alea );
				s_bis = solution.Hammingneighborhood(s).get(alea);
			
		}
		System.out.println(best_s +"   fitness      "+best_fitness);
		return best_s ;
		}
	
	
public ArrayList<Integer> annealingtsp(int taillevecteur , float To , int [][] matrice_distance){
		
		int gap;
		float T= To;
		float B = (float) 0.9;
		
		Encoding solution = new Encoding();
		ArrayList<Integer> s = solution.permutationencoding(taillevecteur);
		Fitnes f = new Fitnes();
		ArrayList<Integer> best_s = (ArrayList<Integer>) s.clone() ;
		int best_fitness = f.objectivefonction_TSP(s, matrice_distance);

		int nb_voisin = solution.permutationneighborhood(s).size() ;
		int alea = (int) (Math.random()* ( nb_voisin));
		ArrayList<Integer> s_bis = solution.permutationneighborhood(s).get(alea);
		
		int i=0;
		while (T > 1) {	
			
			for (int k=0 ; k< 100 ; k++) {
			System.out.println(s);
			gap = f.objectivefonction_TSP(s_bis, matrice_distance) - f.objectivefonction_TSP(s, matrice_distance);
			if(best_fitness > f.objectivefonction_TSP(s_bis,matrice_distance)) {
				best_s = (ArrayList<Integer>) s_bis.clone();
				best_fitness = f.objectivefonction_TSP(s_bis,matrice_distance);
			}
			
			if (gap <= 0) {
				s= (ArrayList<Integer>) s_bis.clone();
			}
			else {
				float alea2 = (float)Math.random();
				//System.out.println(alea2);
				if ( (Math.exp( (-gap  ) / T ) ==1)||(alea2 <= Math.exp( (-gap  ) / T ) )) {
					
					s= (ArrayList<Integer>) s_bis.clone();
				}

				
			}
				
			}
				i++;
				T= geometric(T, B);
				alea = (int) (Math.random()* ( nb_voisin));
				s_bis = solution.permutationneighborhood(s).get(alea);
		}
		
		System.out.println(best_s + "  temps de parcours    "+best_fitness);
		
		return best_s ;
		}
	


public ArrayList<Integer> annealingtsp_float(ArrayList<Integer> S , float To , float [][] matrice_distance){
	
	float gap;
	float T= To;
	float B = (float) 0.9;
	float fitness_depart;
	
	Encoding solution = new Encoding();

	Fitnes f = new Fitnes();
	ArrayList<Integer> best_s = (ArrayList<Integer>) S.clone() ;
	float best_fitness = f.objectivefonction_TSP_float(S, matrice_distance);
	fitness_depart =  f.objectivefonction_TSP_float(S, matrice_distance);

	int nb_voisin = solution.permutationneighborhood(S).size() ;
	int alea = (int) (Math.random()* ( nb_voisin));
	ArrayList<Integer> s_bis = new ArrayList<Integer>();

	while (T > 1) {	
		
		alea = (int) (Math.random()* ( nb_voisin));
	    s_bis = solution.permutationneighborhood(S).get(alea);
		
		for (int k=0 ; k< 30 ; k++) {
		System.out.println(S);
		gap = f.objectivefonction_TSP_float(s_bis, matrice_distance) - f.objectivefonction_TSP_float(S, matrice_distance);
		System.out.println( gap);
		if(best_fitness > f.objectivefonction_TSP_float(s_bis,matrice_distance)) {
			best_s = (ArrayList<Integer>) s_bis.clone();
			best_fitness = f.objectivefonction_TSP_float(s_bis,matrice_distance);
			System.out.println(best_s + "  temps de parcours    "+best_fitness);
			S= (ArrayList<Integer>) s_bis.clone();
			alea = (int) (Math.random()* ( nb_voisin));
		    s_bis = solution.permutationneighborhood(S).get(alea);
			
		}

		else {
			float alea2 = (float)Math.random();
			//System.out.println(alea2);
			if ( (Math.exp( (-gap  ) / T ) ==1)||(alea2 <= Math.exp( (-gap  ) / T ) )) {
				
				S= (ArrayList<Integer>) s_bis.clone();
				System.out.println("on accepte la proba");
				alea = (int) (Math.random()* ( nb_voisin));
				s_bis = solution.permutationneighborhood(S).get(alea);
			}

			
		}
		alea = (int) (Math.random()* ( nb_voisin));
		s_bis = solution.permutationneighborhood(S).get(alea);
		}

			T= geometric(T, B);

	}
	
	System.out.println(best_s + "  temps de parcours    "+best_fitness);
	System.out.println("fitness de depart   "+ fitness_depart );
	
	return best_s ;
	}



	}

		
