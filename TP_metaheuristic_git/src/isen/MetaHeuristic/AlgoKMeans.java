package isen.MetaHeuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class AlgoKMeans {
	
	

	/**samples of values*/
	public static  double samples[][];
	/**list of the data*/
	public static ArrayList<Data> dataSet = new ArrayList<>();
	/**list of the clusters*/
	public static ArrayList<Cluster> clusters = new ArrayList<>();


	/**create some randomize data
	 * @param _nb nb of data to generate
	 * @param _dim diemnsion of the data */
	public static void createSamples(int _nb, int _dim)
	{
		samples =new double[_nb][_dim];
		for(int i=0; i<_nb; i++)
			Arrays.setAll(samples[i], j-> Math.random()*20);
	}

	/**initialise de Data list, normalize them, and create the clusters
	 * @param _nbClusters nb of clusters
	 * @param _dim size of a data
	 *  */
	public static void initialize(int _nbClusters, int _dim)
	{
		double[]maxs = new double[_dim];
		Arrays.fill(maxs, Double.NEGATIVE_INFINITY);
		for(double[] data:samples) 
		{
			dataSet.add(new Data(data));
			Arrays.setAll(maxs, i->(maxs[i]>data[i]?maxs[i]:data[i]));
		}
		dataSet.forEach(d->d.normalize(maxs));

		createClusters(_nbClusters);
	}

	/**create clusters well separated*/
	public static void createClusters(int _nbClusters) {
		Data centroid = dataSet.get((int)(dataSet.size()*Math.random()));
		Cluster firstCluster = new Cluster(centroid);
		clusters.add(firstCluster);
		int nbClusters = _nbClusters;
		for(int c = 1; c<nbClusters; c++) {
			Data farData = null;
			double maxDist = Double.NEGATIVE_INFINITY;
			for(Data data:dataSet) {
				double minDist = Double.POSITIVE_INFINITY;
				for(Cluster cluster:clusters) {
					double dist = data.distNorm(cluster.getCentroid());
					if(minDist>dist) minDist = dist;
				}
				if (maxDist<minDist) {
					maxDist  = minDist; 
					farData = data;
				}
			}
			
			Cluster cluster = new Cluster(farData.clone());
			clusters.add(cluster);
		}

		System.out.println("Initially, centroids are:");
		clusters.forEach(c->System.out.println(c.centroid.toString()));
	}
	
	
	/**algorithm of Kmeans :<br>
	 * set each data in th best cluster and do a loop: <br>
	 * -compute the new clusters centers; -moving  of data in best clusters if necessary
	 * */
	public static void kMeanCluster()
	{
		dataSet.forEach(data -> searchCluster(data).add(data));
		boolean moving = true;
		while(moving)
		{
			moving = false;
			clusters.forEach(c -> c.centralize());
			for(Data data:dataSet)
			{
				Cluster bestCluster = searchCluster(data);
				if(data.getCluster() != bestCluster){
					moving = true;
					data.getCluster().remove(data);
					bestCluster.add(data);
				}
			}
		}
	}



	/**search the best cluster for a data
	 * @param data the data to place in a cluster
	 * @return the cluster whose the centroid is the closest from the data*/
	public static Cluster searchCluster(Data data) {
		Cluster bestCluster = null;
		double minimum = Double.POSITIVE_INFINITY;
		double distance=0;
		for(Cluster cluster:clusters)
		{
			distance = data.distNorm(cluster.getCentroid());
			if(distance < minimum){ minimum = distance; bestCluster = cluster; }
		}
		return bestCluster;
	}




}
