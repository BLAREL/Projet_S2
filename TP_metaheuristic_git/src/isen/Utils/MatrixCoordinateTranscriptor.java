package isen.Utils;

import java.util.ArrayList;

public class MatrixCoordinateTranscriptor {
	
	/**
	 * This function changes a coordinate matrix into a list of string representing these coordinates.
	 * This list created is useful to then calculate the distance matrix of these coordinates.
	 * The coordinate matrix can be recovered using the MatrixLoader on the listCoordinate.csv.
	 * @param coordinates	A double[][] matrix, of size [coordinates.size(), 2].
	 * @return				The list of coordinates, in String. One field is a string representing
	 * 						the two part of a coordinates, separeted by a comma.
	 * 						Example of a field : "50.634483,3.048296".
	 */
	public static ArrayList<String> toArrayString(double[][] coordinates)
	{
		ArrayList<String> list = new ArrayList<>();
		
		for(int i = 0; i < coordinates.length; i++)
			list.add(Double.toString(coordinates[i][0]) + "," + Double.toString(coordinates[i][1]));
		
		return list;
	}
	
	/**
	 * Change the coordinates list to a float matrix.
	 * @param coordinates	The list of coordinates, in String. One field is a string representing
	 * 						the two part of a coordinates, separeted by a comma.
	 * 						Example of a field : "50.634483,3.048296".
	 * @return				A double[][] matrix, of size [coordinates.size(), 2].
	 */
	public static double[][] toMatrix(ArrayList<String> coordinates)
	{
		double[][] matrix = new double[coordinates.size()][2];
		
		for(int i = 0; i < coordinates.size(); i++)
		{
			matrix[i][0] = Double.parseDouble(coordinates.get(i).substring(0, coordinates.get(i).indexOf(",")));
			matrix[i][1] = Double.parseDouble(coordinates.get(i).substring(1+ coordinates.get(i).indexOf(",")));
		}
		
		return matrix;
	}
	
	
}
