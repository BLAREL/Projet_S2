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
public class GeneticAlgorithm {
	
	public ArrayList<Integer> crossoverbinary ( int taille ){
		
		Encoding parent = new Encoding();
		ArrayList<Integer> papa = parent.binaryencoding(taille);
		ArrayList<Integer> maman = parent.binaryencoding(taille);
		ArrayList<Integer> fils = new ArrayList<Integer>();
		
		int proportion = (int)(0.1 * taille);
		
		for (int i=0 ; i< (taille - proportion) ; i++) {
			fils.add( papa.get(i) );
			
		}
		
		for (int k=(taille - proportion) ; k< taille ;k++ ) {
			
			fils.add(maman.get(k));
			
		}
		return fils;
	}

	
	public ArrayList<Integer> mutation( int taille ){
		
		Encoding test = new Encoding ();
		ArrayList<Integer> vecteur = new ArrayList ();
		vecteur = test.binaryencoding(taille);
		int index = (int) (Math.random() * (taille)) ;
		
		if (vecteur.get(index)==0) {  vecteur.set(index, 1);  }
		else {vecteur.set(index, 0);}
	
	return vecteur;
	}
	
}
