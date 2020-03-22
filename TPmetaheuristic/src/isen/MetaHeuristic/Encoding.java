package isen.MetaHeuristic;

/**
 * 
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandre
 *
 */
public class Encoding {
	

	
	

	
	public ArrayList<Integer>binaryencoding( int taillevecteur){
		
		ArrayList<Integer>vecteur= new ArrayList<>(); 
		for (int i=0 ; i< taillevecteur ; i++) {
			int ran = (int) (Math.random() * (2)) ;
			
			if (ran == 0) {
				vecteur.add(0);	
			}
			else {
				
			vecteur.add(1);	
			}
			
		}
		
		//System.out.println( vecteur );
		return(vecteur);
	}
	
	public ArrayList<Integer>discretencoding( int taillevecteur, int min, int max){
		
		ArrayList<Integer>vecteur= new ArrayList<>(); 
		for (int i=0 ; i< taillevecteur ; i++) {
			int ran = min + (int)(Math.random() * ((max - min) + 1));
			
				vecteur.add(ran);	

		}
		
		
		System.out.println( vecteur );
		return(vecteur);
	}
	
	
	public ArrayList<Float>realencoding( int taillevecteur){
		
		ArrayList<Float>vecteur= new ArrayList<>(); 
		for (int i=0 ; i< taillevecteur ; i++) {
			float ran = (float) (Math.random() * (1+ taillevecteur)) ;
			
				vecteur.add(ran);	

		}
		
		
		System.out.println( vecteur );
		return(vecteur);
	}
	
	public ArrayList<Integer>permutationencoding( int taillevecteur){
		
		ArrayList<Integer>vecteur= new ArrayList<>(); 
		ArrayList<Integer>vect_tampon= new ArrayList<>(); 
		
		for (int i=0 ; i< taillevecteur ; i++) {
						
				vecteur.add(i +1);	

		}
		
		int alea;
		for (int k=0 ; k< taillevecteur ; k++) {
			alea = (int) (Math.random()* (vecteur.size()));
			vect_tampon.add( vecteur.get(alea) );
			vecteur.remove(alea);
		}


		
		
		System.out.println( vect_tampon );
		return(vect_tampon);
	}
	
	
public List<ArrayList<Integer>> Hammingneighborhood( ArrayList<Integer> solution){
		
		List <ArrayList<Integer>>L = new ArrayList<ArrayList<Integer>>();
		
		//int taille_solution= 2^solution.size();
		int nombre_de_voisin= solution.size();
		
	//	System.out.println("methode de hamming");
		
		//System.out.println(solution);
		
		for (int k=0; k< nombre_de_voisin; k++) {
			
			if (solution.get(k)== 0) {
				solution.set(k, 1);
				L.add((ArrayList<Integer>) solution.clone());
				
				//System.out.println(solution);
				solution.set(k, 0);
			}
			else {
				solution.set(k, 0);
				
				L.add((ArrayList<Integer>) solution.clone());
				
				//System.out.println(solution);
				solution.set(k, 1);
			}
			
		}
		
		return (L);
		
	}
	
	
	public List<ArrayList<Integer>> Discretneighborhood( ArrayList<Integer> solution, int min, int max){
		
		int taille= solution.size();
		List <ArrayList<Integer>>L = new ArrayList<ArrayList<Integer>>();
		
		int nbr_voisins= taille* (max - min);
		ArrayList<Integer>liste_symboles = new ArrayList<>(); 
		ArrayList<Integer>copysolution = new ArrayList<>(); 
		
		for (int k=min ; k< max+1 ; k++) {
			
			liste_symboles.add(k);
		}
		System.out.println("liste de symbole");
		System.out.println(liste_symboles);
		
		System.out.println("solution");
		System.out.println(solution);
		
		
		for (int i=0; i< taille ; i++) {
			
			int n=0;
			
			while (n != liste_symboles.size()) {
				if ( liste_symboles.get(n) != solution.get(i)  ) {
					copysolution.addAll(solution); 
					copysolution.set(i, liste_symboles.get(n));
					System.out.println(copysolution);

					L.add(copysolution);
					
					
				}
				copysolution.clear();
				n++;
				
			}
			
		}
		
		
		return (L);
	}
	
	public  List<ArrayList<Integer>> permutationneighborhood(ArrayList<Integer>solution){

		List <ArrayList<Integer>>L = new ArrayList<ArrayList<Integer>>();
		int perm;
		
		
		//System.out.println(solution);
		//System.out.println("voici tous les voisins pour encoding permutation");
		for (int k=0; k<solution.size();k++) {
			for (int i=0; i< solution.size() - 1; i++) {
				
				perm= solution.get(i);
				solution.set(i, solution.get(i+1));
				solution.set(i+1, perm);
				L.add((ArrayList<Integer>) solution.clone());
				//System.out.println(solution);
				
			}
			
			
			
		}
		
		
		
		return L;
		
		
	}
}