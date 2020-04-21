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
	 * @param coordinatesX	The coordinates list used to get all the informations for the X axis of the matrix.
	 * @param coordinatesY	The coordinates list used to get all the informations for the Y axis of the matrix.
	 * @param matrixes		The matrix (distance or duration) of the coordinates.
	 * @param prefix		The prefix used for the file name when saving the data.
	 */
	public static void SaveInformationForMatrixCreation(ArrayList<String> coordinatesX, ArrayList<String> coordinatesY, double[][] matrix, String prefix) {

		
		String travelInfo = "";

		for(int i = 0; i < coordinatesX.size(); i++)
			travelInfo += ";" + coordinatesX.get(i);

		travelInfo += "\n";
		
		for(int i = 0; i < coordinatesY.size(); i++)
		{
			travelInfo += coordinatesY.get(i);
			
			for(int j = 0; j < coordinatesX.size(); j++)
				travelInfo += ";" + Double.toString(matrix[j][i]);
			
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
	public static double[][] LoadMatrixInformation(String prefix)
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
		double[][] matrix = new double[countX][countY];

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
				//System.out.println("nextSemicolon");System.out.println(subInfo.substring(0, nextSemicolon));
				matrix[i % countX][i / countX] = Double.parseDouble(subInfo.substring(0, nextSemicolon));
				indexString += nextSemicolon + 1;
			}
			else if (nextSemicolon > nextLine)
			{
				//System.out.println("nextLine");System.out.println(subInfo.substring(0, nextLine));
				matrix[i % countX][i / countX] = Double.parseDouble(subInfo.substring(0, nextLine));
				indexString += nextSemicolon + 1;
			}
			else {
				matrix[i % countX][i / countX] = Double.parseDouble(subInfo);
				indexString = travelInfo.length();
			}
			i += 1;
		}
		
		return matrix;
	}

	/**
	 * This function will use the Bing Map API to make two Matrixes at the same time, to reduce the request calls.
	 * It works for a matrix of coordinates with themselves : a matrix which will be both squared and symmetrical. 
	 * Number of requests = coordinates.size() x (coordinates.size() - 1) / 2
	 * @param coordinates	The coordinates list used to get all the informations.
	 * @param isWalking		Boolean to choose between walking and driving, important since these
	 * 						transportation methods can influence both the travel duration and the travel distance.
	 * @return				A pair of the Matrix of TravelDistance and the Matrix of TravelDuration.
	 */
	public static Pair<double[][], double[][]> getMatrixesFromAPI(ArrayList<String> coordinates, boolean isWalking)
	{
		String transportationMethod = isWalking ? "Walking" : "Driving";
		
		double[][] matrixDistance = new double[coordinates.size()][coordinates.size()];
		double[][] matrixDuration = new double[coordinates.size()][coordinates.size()];
		
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

					matrixDistance[i][j] = Double.parseDouble(informations.get(0));
					matrixDistance[j][i] = matrixDistance[i][j];
					matrixDuration[i][j] = Double.parseDouble(informations.get(1));
					matrixDuration[j][i] = matrixDuration[i][j];
					
			    } catch (Exception e) {
					System.err.println(Integer.toString(i) + ";" + Integer.toString(j));
					e.printStackTrace();
			    }
			}
		}
		return new Pair<double[][], double[][]>(matrixDistance, matrixDuration);
	}
	
	/**
	 * This function will use the Bing Map API to make two Matrixes at the same time, to reduce the request calls.
	 * It works for all kind of matrixes : Non symmetrical as well as non squared matrixes.
	 * However, this function is not optimized for limited the HTTP requests therefore, it should be avoided if possible. 
	 * Number of requests = coordinatesX.size() x coordinatesY.size()
	 * @param coordinatesX	The coordinates list used to get all the informations for the X axis of the matrix.
	 * @param coordinatesY	The coordinates list used to get all the informations for the Y axis of the matrix.
	 * @param isWalking		Boolean to choose between walking and driving, important since these
	 * 						transportation methods can influence both the travel duration and the travel distance.
	 * @return				A pair of the Matrix of TravelDistance and the Matrix of TravelDuration.
	 */
	public static Pair<double[][], double[][]> getMatrixesFromAPI(ArrayList<String> coordinatesX, ArrayList<String> coordinatesY, boolean isWalking)
	{
		String transportationMethod = isWalking ? "Walking" : "Driving";
		
		double[][] matrixDistance = new double[coordinatesX.size()][coordinatesY.size()];
		double[][] matrixDuration = new double[coordinatesX.size()][coordinatesY.size()];
		
		for(int i = 0; i < coordinatesX.size(); i++)
		{
			for(int j = 0; j < coordinatesY.size(); j++)
			{
				try {
					String XML = BingMapAPI.Request(coordinatesX.get(i), coordinatesY.get(j), transportationMethod);
					ArrayList<String> infoKeys = new ArrayList<String>();
					infoKeys.add("TravelDistance"); infoKeys.add("TravelDuration");
					ArrayList<String> informations = BingMapAPI.getInformationFromXML(XML, infoKeys);

					matrixDistance[i][j] = Double.parseDouble(informations.get(0));
					matrixDuration[i][j] = Double.parseDouble(informations.get(1));
					
			    } catch (Exception e) {
					System.err.println(Integer.toString(i) + ";" + Integer.toString(j));
					e.printStackTrace();
			    }
			}
		}
		return new Pair<double[][], double[][]>(matrixDistance, matrixDuration);
	}
}
