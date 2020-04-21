package isen.Utils;

import java.util.ArrayList;

public class MatrixAlterator {
	
	/**
	 * Rotate the matrix.
	 * @param matrix	The initial matrix.
	 * @return			The matrix rotated.
	 */
	public static double[][] rotateMatrix(double[][] matrix)
	{
		double[][] returnMatrix = new double[matrix[0].length][matrix.length];
		
		for(int i = 0; i < matrix.length; i++)
	    	  for(int j = 0; j < matrix[i].length; j++)
	    		  returnMatrix[j][i] = matrix[i][j];
	    	
		return returnMatrix;
	}
	/**
	 * Filter the X or Y axis of the matrix, to only keep a subset of index from it.
	 * Useful, for example, to change a distance matrix of distances from addresses with themselves
	 * to a matrix of distance from addresses with the stations, if the stations are a subset of addresses.
	 * @param matrix		The initial matrix.
	 * @param indexList		The index list to keep.
	 * @param filterOnY		Boolean to know if the filter is used on the X axis or the Y axis.
	 * @return				The filtered matrix.
	 */
	public static double[][] subMatrix(double[][] matrix, ArrayList<Integer> indexList, boolean filterOnY)
	{
		double[][] returnMatrix = filterOnY ? new double[matrix.length][indexList.size()] : new double[indexList.size()][matrix[0].length];

		for(int i = 0; i < returnMatrix.length; i++)
	    	  for(int j = 0; j < returnMatrix[i].length; j++)
	    		  returnMatrix[i][j] = filterOnY ? matrix[i][indexList.get(j)] : matrix[indexList.get(i)][j];
	    			
		return returnMatrix;
	}
}
