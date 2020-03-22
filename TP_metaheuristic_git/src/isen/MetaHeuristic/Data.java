package isen.MetaHeuristic;

import java.util.Arrays;
import java.util.Locale;

public class Data implements Cloneable{

	/**nb of values in a data*/
	int length;
	/**the values*/
	private double[]values;
	/**the values, normalized*/
	 double[]normValues;
	/**cluster associated to the data*/
	Cluster cluster;

	
	/**
	 * initialize the values, the length and the sum
	 * @param _length the nb of values*/
	public Data(int _length)
	{
		length = _length;
		values = new double[length];
		normValues= new double[length];
	}
	/**
	 * initialize the values, the length and the sum
	 * @param tab the list of values*/
	public Data(double ...tab)
	{
		this(tab.length);
		values = Arrays.copyOf(tab, tab.length);
	}

	/**
	 * change a value, abd the sum
	 * @param i indice of the value to be changed
	 * @param x new value
	 * */
	public void setNormValue(int i, double x)
	{ normValues[i] = x; }

	/**
	 * change a value, abd the sum
	 * @param i indice of the value to be changed
	 * @param x new value
	 * */
	public void setValue(int i, double x)
	{ values[i] = x; }

	/**
	 * @return the ith value of he data
	 * */
	public double getNormValue(int i)
	{ return normValues[i]; }

	/**
	 * @return the ith value of he data
	 * */
	public double getValue(int i)
	{ return values[i]; }


	/**compute the euclidian distance between the current value and another one
	 * @param o the Other value to be compared with
	 * @return the distance between the current data and the other*/
	public double distNorm(Data o)
	{
		double dist, sum = 0;
		for(int i=0; i<length; i++)
			sum += Math.pow(normValues[i] - o.normValues[i], 2);
		dist = Math.sqrt(sum);
		return  dist;
	}

	/**
	 * @return a clone of the data*/
	protected Data clone()
	{
		Data copy = new Data(values);
		copy.normValues = Arrays.copyOf(normValues, length);
		return copy;
	}
	
	/**
	 * @return the list of the values
	 * */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("(");
		String sep=", ";
		for(double v:values) sb.append(String.format(Locale.ENGLISH,"%.2f", v)).append(sep);
		sb.append(")");
		return sb.toString();
	}

	
	/**create the array of normalized values
	 * @param maxs arrays of maximal values*/
	void normalize(double[]maxs)
	{
		normValues = new double[length];
		Arrays.setAll(normValues, i->values[i]/maxs[i]);		
	}
	
	public void setCluster(Cluster cluster) { this.cluster = cluster; }

	public Cluster getCluster() { return cluster; }


}
