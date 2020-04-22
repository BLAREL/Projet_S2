/**
 * 
 */
package isen.MetaHeuristic;

import java.util.ArrayList;

/**
 * @author Alexandre
 *
 */
public class SolutionGlobale {

	
	ArrayList<Integer> itineraire ;
	float parcourtdeletudiant ;
	float distance_itineraire;
	ArrayList<Position> station ;
	float objective;
	
	
	public SolutionGlobale (float alpha ,ArrayList<Integer> pitineraire ,float pparcourtdeletudiant,float pdistance_itineraire ,ArrayList<Position> pstation) {
		
		this.itineraire =  pitineraire;
		this.parcourtdeletudiant = pparcourtdeletudiant;
		this.distance_itineraire = pdistance_itineraire ;
		this.station = pstation ;
		this.objective = alpha * parcourtdeletudiant + (1-alpha ) *distance_itineraire;
	}
	
	
}
