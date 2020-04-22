package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;

public class Tabu {
	
	
	
	
	

	
	public ArrayList<Integer> TS_binaire_short_memory( ArrayList<Integer> s){
		
		
		ArrayList<Integer>vecteur= new ArrayList<>();
		ArrayList<Integer>best_solution= new ArrayList<>();
		
		
		List <ArrayList<Integer>>Liste_tabu = new ArrayList<ArrayList<Integer>>();
		
		List <ArrayList<Integer>>Liste_de_voisins = new ArrayList<ArrayList<Integer>>();
		
		int resultat_objective  ;
		
		Encoding enc = new Encoding ();
		Fitnes fit = new Fitnes ();
		LocalSearch meilleur_voisin = new LocalSearch();
		
		resultat_objective = fit.objectivefunction_equation( s );
		best_solution = (ArrayList<Integer>) s.clone() ;
		
		Liste_tabu.add(s);
		resultat_objective = fit.objectivefunction_equation(s);
		Liste_de_voisins = enc.Hammingneighborhood(s);
		vecteur = meilleur_voisin.selection_best(Liste_de_voisins);
		
		
		for (int i=0 ; i< 100 ; i++) {
			

			
			// solution  qui n'appartient pas a la liste tabu
			if ( Liste_tabu.contains( vecteur ) ) {
				
				if ( fit.objectivefunction_equation( vecteur ) < resultat_objective   ) {
					
					
					
					s = (ArrayList<Integer>) vecteur.clone();
					
					Liste_tabu.add(s);
					Liste_de_voisins = enc.Hammingneighborhood(s);
					vecteur = meilleur_voisin.selection_best(Liste_de_voisins);
					
					
				}
				
				else {
					Liste_de_voisins.remove(vecteur);
					vecteur = meilleur_voisin.selection_best(Liste_de_voisins);
					
				}
			}
			
			else {
				
				Liste_tabu.add( vecteur ) ;
				
				if (resultat_objective < fit.objectivefunction_equation( vecteur )) {
					
					resultat_objective = fit.objectivefunction_equation( vecteur );
					best_solution = (ArrayList<Integer>) vecteur.clone() ;
					resultat_objective = fit.objectivefunction_equation( best_solution);
					}
					
					
					
					
					s= (ArrayList<Integer>) vecteur.clone() ;
					Liste_tabu.add(s);
					Liste_de_voisins = enc.Hammingneighborhood(s);
					vecteur = meilleur_voisin.selection_best(Liste_de_voisins);
					
				}
				
				
			
			
			if ( Liste_tabu.size() > 20) {
				
				Liste_tabu.remove(0);
				
				
			}
	}
		
		System.out.println( best_solution );
		System.out.println( resultat_objective );
		return (best_solution);
		
	} 

}
