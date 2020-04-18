package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.Collections;

import javafx.util.Pair;

public class AlgoKMedoid {

	/**
	 * This function calculate the k best station to minimize the travel by executing the k-medoid algorithm using a distance matrix.
	 * @param matrix	The distance matrix used.
	 * @param k			The number of stations.
	 * @return			A index list of the k stations chosen.
	 */
	public static ArrayList<Integer> execute(float[][] matrix, int k)
	{
		ArrayList<Integer> stationIndex = new ArrayList<>();
		ArrayList<Integer> associatedStations = new ArrayList<>();
		
		//Initialization
		for(int i = 0; i < k; i++)
		{
			stationIndex.add(i);
		}
		
		//Association
		for(int i = 0; i < matrix.length; i++)
		{
			associatedStations.add(getClosestCenter(matrix, i, stationIndex));
		}
		
		boolean isConfigurationCostDecrises = true;
		
		//Improvement loop
		while(isConfigurationCostDecrises)
		{
			isConfigurationCostDecrises = false;
			
			float configurationCost = calculateConfigurationCost(matrix, associatedStations);
			
			Pair<Integer, Float> betterConfigurationCost = null;
			
			for(int i = 0; i < matrix.length; i++)
			{
				//If the coordinate isn't a center.
				if(i != associatedStations.get(i))
				{
					ArrayList<Integer> tempAssociatedStations = new ArrayList<>(associatedStations);
					Collections.replaceAll(tempAssociatedStations, associatedStations.get(i), i);
					float alternativeConfigCost = calculateConfigurationCost(matrix, tempAssociatedStations);
					if(alternativeConfigCost < configurationCost)
					{
						isConfigurationCostDecrises = true;
						if(betterConfigurationCost == null)
							betterConfigurationCost = new Pair<>(i, alternativeConfigCost);
						else if(alternativeConfigCost < betterConfigurationCost.getValue())
							betterConfigurationCost = new Pair<>(i, alternativeConfigCost);
					}
				}
			}
			
			if(isConfigurationCostDecrises)
			{
				Collections.replaceAll(stationIndex, associatedStations.get(betterConfigurationCost.getKey()), betterConfigurationCost.getKey());
				Collections.replaceAll(associatedStations, associatedStations.get(betterConfigurationCost.getKey()), betterConfigurationCost.getKey());
				
				for(int i = 0; i < matrix.length; i++)
				{
					associatedStations.set(i, getClosestCenter(matrix, i, stationIndex));
				}
			}
		}
		
		return stationIndex;
	}
	
	/**
	 * This function return the index of the closest station from the address using the distance matrix.
	 * @param matrix			The distance matrix used.
	 * @param startIndex		The index of the address.
	 * @param stationIndex		List of the station indexes.
	 * @return
	 */
	
	private static int getClosestCenter(float[][] matrix, int startIndex, ArrayList<Integer> stationIndex)
	{
		int indexOfMinimum = stationIndex.get(0);
		for(int j = 0; j < stationIndex.size(); j++)
		{
			if(matrix[startIndex][stationIndex.get(j)] < matrix[startIndex][indexOfMinimum])
				indexOfMinimum = stationIndex.get(j);
		}
		return indexOfMinimum;
	}
	/**
	 * This function simply calculate the cumulative distance between each address and its current associated station.
	 * @param matrix				The distance matrix used.
	 * @param associatedStations	Each number is itself an index of the address chosen as the nearest station from the address associated to the index of that number.
	 * @return						The cumulative distance calculated.
	 */
	private static float calculateConfigurationCost(float[][] matrix, ArrayList<Integer> associatedStations)
	{
		float configurationCost = 0.0f;
		
		for(int i = 0; i < matrix.length; i++)
			configurationCost += matrix[i][associatedStations.get(i)];
		
		return configurationCost;
	}
}
