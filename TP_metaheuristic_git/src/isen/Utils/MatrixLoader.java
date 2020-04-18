package isen.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.util.Pair;


public class MatrixLoader {
	
	public static String dataFolder = "data\\"; 
	
	/**
	 * This function will save the TravelDistance and TravelDuration Matrixes on the local device.
	 * It is mostly used to avoid calling the API each time the device needs to use the matrixes of the student addresses. 
	 * @param coordinates 	The coordinates list used to get all the informations.
	 * @param matrixes		The matrix (distance or duration) of the coordinates.
	 * @param prefix		The prefix used for the file name when saving the data.
	 */
	public static void SaveInformationForMatrixCreation(ArrayList<String> coordinates, float[][] matrix, String prefix) {

		
		String travelInfo = "";

		for(int i = 0; i < coordinates.size(); i++)
			travelInfo += ";" + coordinates.get(i);

		travelInfo += "\n";
		
		for(int i = 0; i < coordinates.size(); i++)
		{
			travelInfo += coordinates.get(i);
			
			for(int j = 0; j < coordinates.size(); j++)
				travelInfo += ";" + Float.toString(matrix[i][j]);
			
			travelInfo += "\n";
		}
		
		
		try {
			File file = new File(dataFolder + prefix + ".csv");
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
	    } catch (IOException e) {
	        e.printStackTrace();
		}
		try {
			
		    FileWriter fileWriter = new FileWriter(dataFolder + prefix + ".csv");
		    fileWriter.write(travelInfo);
		    fileWriter.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the information of the matrix saved in a csv file on the local device.
	 * @param prefix	The prefix of the file which needs to be load.
	 * @return			The matrix loaded and created.
	 */
	public static float[][] LoadMatrixInformation(String prefix)
	{
		String travelInfo = "";
		try {
			
			File file = new File(dataFolder + prefix + ".csv");
		    Scanner reader = new Scanner(file);
		    while (reader.hasNextLine()) {
		        travelInfo += reader.nextLine() + "\n";
		    }
		    
		    reader.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(travelInfo);
		
		String firstLine = travelInfo.substring(0, travelInfo.indexOf("\n"));
		
		//Count the number of ";" in the first csv line and "\n" in all the csv file, which is equal to the lenght of the matrix.
		int countX = firstLine.length() - firstLine.replace(";", "").length();
		int countY = travelInfo.length() - travelInfo.replace("\n", "").length() - 1;
		float[][] matrix = new float[countX][countY];

		//System.out.println("[" + Integer.toString(countX) + ";" + Integer.toString(countY) + "]");
		
		int i = 0;
		int indexString = travelInfo.indexOf("\n");
		indexString += travelInfo.substring(indexString).indexOf(";");
		
		while(indexString + 1 < travelInfo.length())
		{
			String subInfo = travelInfo.substring(indexString + 1);

			//System.out.println(subInfo);
			int nextSemicolon = subInfo.indexOf(";");
			int nextLine = subInfo.indexOf("\n");
			if((nextLine == -1 || nextSemicolon < nextLine) && nextSemicolon > -1)
			{
				//System.out.println(subInfo.substring(0, nextSemicolon));
				matrix[i % countY][i / countY] = Float.parseFloat(subInfo.substring(0, nextSemicolon));
				indexString += nextSemicolon + 1;
			}
			else if (nextSemicolon > nextLine)
			{
				//System.out.println(subInfo.substring(0, nextLine));
				matrix[i % countY][i / countY] = Float.parseFloat(subInfo.substring(0, nextLine));
				indexString += nextSemicolon + 1;
			}
			else {
				matrix[i % countY][i / countY] = Float.parseFloat(subInfo);
				indexString = travelInfo.length();
			}
			i += 1;
		}
		
		return matrix;
	}

	/**
	 * This function will use the Bing Map API to make two Matrixes at the same time, to reduce the request calls.
	 * @param coordinates	The coordinates list used to get all the informations.
	 * @param isWalking		Boolean to choose between walking and driving, important since these
	 * 						transportation methods can influence both the travel duration and the travel distance.
	 * @return				A pair of the Matrix of TravelDistance and the Matrix of TravelDuration.
	 */
	public static Pair<float[][], float[][]> getMatrixesFromAPI(ArrayList<String> coordinates, boolean isWalking)
	{
		String transportationMethod = isWalking ? "Walking" : "Driving";
		
		float[][] matrixDistance = new float[coordinates.size()][coordinates.size()];
		float[][] matrixDuration = new float[coordinates.size()][coordinates.size()];
		
		for(int i = 0; i < coordinates.size(); i++)
		{
			matrixDistance[i][i] = 0.0f; 
			matrixDuration[i][i] = 0.0f; 
			for(int j = 0; j < i; j++)
			{
				try {
					String XML = BingMapAPI.Request(coordinates.get(i), coordinates.get(j), transportationMethod);
					ArrayList<String> infoKeys = new ArrayList<String>();
					infoKeys.add("TravelDistance"); infoKeys.add("TravelDuration");
					ArrayList<String> informations = BingMapAPI.getInformationFromXML(XML, infoKeys);

					matrixDistance[i][j] = Float.parseFloat(informations.get(0));
					matrixDistance[j][i] = matrixDistance[i][j];
					matrixDuration[i][j] = Float.parseFloat(informations.get(1));
					matrixDuration[j][i] = matrixDuration[i][j];
					
			    } catch (Exception e) {
					System.err.println(Integer.toString(i) + ";" + Integer.toString(j));
					e.printStackTrace();
			    }
			}
		}
		return new Pair<float[][], float[][]>(matrixDistance, matrixDuration);
	}
}
