package isen.Analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationsAnalyzer {

	/**
	 * This function analyze a basic information about a matrix between the addresses and its distances.
	 * It then put useful information in a Map.
	 * Currently, this analyzer output information such as the number of addresses and stations.
	 * @param stationsAddressesDistanceMatrix	The distance matrix between addresses (in X) and its stations (in Y)
	 * @return		The map containing the information. The key are string referencing the name
	 * 				of the information and the value is the associated value of that information.
	 */
	public static Map<String, String> analyzeBasicInformation(double[][] stationsAddressesDistanceMatrix)
	{
		Map<String, String> usefulValues = new HashMap<>();

		usefulValues.put("addressesQuantity", Integer.toString(stationsAddressesDistanceMatrix.length));
		usefulValues.put("stationQuantity", Integer.toString(stationsAddressesDistanceMatrix[0].length));
		
		return usefulValues;
	}
	/**
	 * This function analyze a matrix between the addresses and its stations.
	 * It then put useful information in a Map. 
	 * Currently, this analyzer calculate the total and the mean walking distance as well as the longest distance a student needs to walk. 
	 * @param stationsAddressesDistanceMatrix	The distance matrix between addresses (in X) and its stations (in Y)
	 * @param unit								The unit of the matrix's values (km or seconds)
	 * @return		The map containing the information. The key are string referencing the name
	 * 				of the information and the value is the associated value of that information.
	 */
	public static Map<String, String> analyzeWalkingDistance(double[][] stationsAddressesDistanceMatrix, String unit)
	{
		
		//Instantiate all the list of each station's information.
		ArrayList<Double> totalClustersWalkingDistance = new ArrayList<>();
		ArrayList<Double> longestClustersWalkingDistance = new ArrayList<>();
		for(int j = 0; j < stationsAddressesDistanceMatrix[0].length; j++)
		{
			totalClustersWalkingDistance.add((double) 0);
			longestClustersWalkingDistance.add((double) 0);
		}
		
		
		for(int i = 0; i < stationsAddressesDistanceMatrix.length; i++)
		{
			int j = getAssociatedStation(stationsAddressesDistanceMatrix, i);
			
			if(longestClustersWalkingDistance.get(j) < stationsAddressesDistanceMatrix[i][j])
				longestClustersWalkingDistance.set(j, stationsAddressesDistanceMatrix[i][j]);
			totalClustersWalkingDistance.set(j, totalClustersWalkingDistance.get(j) + stationsAddressesDistanceMatrix[i][j]);	
		}
		
		double totalWalkingDistance = 0;
		double longestWalkingDistance = 0;
		for(int j = 0; j < stationsAddressesDistanceMatrix[0].length; j++)
		{
			if(longestWalkingDistance < longestClustersWalkingDistance.get(j))
				longestWalkingDistance = longestClustersWalkingDistance.get(j);
			totalWalkingDistance += totalClustersWalkingDistance.get(j);
		}
		double meanWaklingDistance = totalWalkingDistance / stationsAddressesDistanceMatrix.length;
		
		Map<String, String> usefulValues = new HashMap<>();

		usefulValues.put("totalWalkingDistance", Double.toString(totalWalkingDistance));
		usefulValues.put("longestWalkingDistance", Double.toString(longestWalkingDistance));
		usefulValues.put("meanWaklingDistance", Double.toString(meanWaklingDistance));
		usefulValues.put("unit", unit);
		
		return usefulValues;
	}
	public static Map<String, String> analyzeStationEfficiency(double[][] stationsAddressesDistanceMatrix)
	{
		
		//Instantiate all the list of each station's information.
		ArrayList<Integer> numberAssociatedAddresses = new ArrayList<>();
		for(int j = 0; j < stationsAddressesDistanceMatrix[0].length; j++)
		{
			numberAssociatedAddresses.add(0);
		}
		
		
		for(int i = 0; i < stationsAddressesDistanceMatrix.length; i++)
		{
			int j = getAssociatedStation(stationsAddressesDistanceMatrix, i);
			numberAssociatedAddresses.set(j, numberAssociatedAddresses.get(j) + 1);
		}
		
		int minimumAssociatedAddresses = numberAssociatedAddresses.get(0);
		int maximumAssociatedAddresses = numberAssociatedAddresses.get(0);
		double meanAssociatedAddresses = stationsAddressesDistanceMatrix.length / stationsAddressesDistanceMatrix[0].length;
		
		
		double ecartTypeAssociatedAddresses = 0;

		for(int j = 0; j < stationsAddressesDistanceMatrix[0].length; j++)
		{
			if(numberAssociatedAddresses.get(j) < minimumAssociatedAddresses)
				minimumAssociatedAddresses = numberAssociatedAddresses.get(j);
			if(maximumAssociatedAddresses < numberAssociatedAddresses.get(j))
				maximumAssociatedAddresses = numberAssociatedAddresses.get(j);
			ecartTypeAssociatedAddresses += (numberAssociatedAddresses.get(j) - meanAssociatedAddresses) * (numberAssociatedAddresses.get(j) - meanAssociatedAddresses);
		}
		
		ecartTypeAssociatedAddresses /= stationsAddressesDistanceMatrix.length;
		ecartTypeAssociatedAddresses = Math.sqrt(ecartTypeAssociatedAddresses);
		
		Map<String, String> usefulValues = new HashMap<>();

		usefulValues.put("minimumAssociatedAddresses", Integer.toString(minimumAssociatedAddresses));
		usefulValues.put("maximumAssociatedAddresses", Integer.toString(maximumAssociatedAddresses));
		usefulValues.put("ecartTypeAssociatedAddresses", Double.toString(ecartTypeAssociatedAddresses));
		
		return usefulValues;
	}
	
	
	/**
	 * Get the associated station of an address.
	 * @param matrix		The addresses/stations matrix.
	 * @param index			The index of the address we want to get the associated station.
	 * @return				The index of the station.
	 */
	private static int getAssociatedStation(double[][] matrix, int index)
	{
		int indexOfMinimum = 0;
		for(int j = 0; j < matrix[0].length; j++)
		{
			if(matrix[index][j] < matrix[index][indexOfMinimum])
				indexOfMinimum = j;
		}
		return indexOfMinimum;
	}
	
	public static String createReport(Map<String, String> analyzedReport, String language)
	{
		String report = "";
		
		if(language == "fr")
		{
			if(analyzedReport.containsKey("addressesQuantity"))
				report += "L'algorithme a travaillé sur " + analyzedReport.get("addressesQuantity") + " adresses.\n";
			if(analyzedReport.containsKey("stationQuantity"))
				report += "Il y a eu " + analyzedReport.get("stationQuantity") + " stations choisies.\n";
			
			if(analyzedReport.containsKey("totalWalkingDistance") && analyzedReport.containsKey("unit"))
				report += "La distance totale est de " + analyzedReport.get("totalWalkingDistance") + " " + analyzedReport.get("unit") + ".\n";
			if(analyzedReport.containsKey("longestWalkingDistance") && analyzedReport.containsKey("unit"))
				report += "La plus longue distance devant être parcourue doit être de " + analyzedReport.get("longestWalkingDistance") + " " + analyzedReport.get("unit") + ".\n";
			if(analyzedReport.containsKey("meanWaklingDistance") && analyzedReport.containsKey("unit"))
				report += "La distance moyenne entre une adresse et sa station associée est de " + analyzedReport.get("meanWaklingDistance") + " " + analyzedReport.get("unit") + ".\n";
			
			if(analyzedReport.containsKey("minimumAssociatedAddresses"))
				report += "La station ayant le moins d'adresses associées a " + analyzedReport.get("minimumAssociatedAddresses") + " adresses.\n";
			if(analyzedReport.containsKey("maximumAssociatedAddresses"))
				report += "La station ayant le plus d'adresses associées a " + analyzedReport.get("maximumAssociatedAddresses") + " adresses.\n";
			if(analyzedReport.containsKey("ecartTypeAssociatedAddresses"))
				report += "L'ecart-type du nombre d'adresses associées à chaque stations est de " + analyzedReport.get("ecartTypeAssociatedAddresses") + " .\n";
		}
		else if(language == "en")
		{
			if(analyzedReport.containsKey("addressesQuantity"))
				report += "The algorithm has worked on " + analyzedReport.get("addressesQuantity") + " addresses.\n";
			if(analyzedReport.containsKey("stationQuantity"))
				report += "There are " + analyzedReport.get("stationQuantity") + " stations chosen.\n";
			
			if(analyzedReport.containsKey("totalWalkingDistance") && analyzedReport.containsKey("unit"))
				report += "The total distance of all the path is " + analyzedReport.get("totalWalkingDistance") + " " + analyzedReport.get("unit") + ".\n";
			if(analyzedReport.containsKey("longestWalkingDistance") && analyzedReport.containsKey("unit"))
				report += "The longest distance needed to be traveled is " + analyzedReport.get("longestWalkingDistance") + " " + analyzedReport.get("unit") + ".\n";
			if(analyzedReport.containsKey("meanWaklingDistance") && analyzedReport.containsKey("unit"))
				report += "The mean distance between an address and its associated station is " + analyzedReport.get("meanWaklingDistance") + " " + analyzedReport.get("unit") + ".\n";
		
			if(analyzedReport.containsKey("minimumAssociatedAddresses"))
				report += "The station having the fewest number of associated addresses have " + analyzedReport.get("minimumAssociatedAddresses") + " addresses.\n";
			if(analyzedReport.containsKey("maximumAssociatedAddresses"))
				report += "The station having the largest number of associated addresses have " + analyzedReport.get("maximumAssociatedAddresses") + " addresses.\n";
			if(analyzedReport.containsKey("ecartTypeAssociatedAddresses"))
				report += "The standart deviation of the number of addresses associated to each station is equal to " + analyzedReport.get("ecartTypeAssociatedAddresses") + " .\n";
		}
		else
		{
			report = "The language " + language + " is not found.\nLook at the documentation for more information.";
		}
		
		return report;
	}
}
