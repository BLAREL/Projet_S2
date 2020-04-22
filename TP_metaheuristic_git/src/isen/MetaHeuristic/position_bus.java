package isen.MetaHeuristic;

import java.util.ArrayList;

public class Position_bus {
	

	
	public float [][] matrixOfDist ( ArrayList<Position> pos){
		
        float[][]newArr=new float[pos.size()][pos.size()];
        float a,b,c,d ;
        for(int i=0;i<pos.size();i++)
        {
            for(int j=0;j<pos.size();j++)
            {
            	a = pos.get(i).x;
            	b=pos.get(j).x;
            	c=pos.get(i).y;
            	d=pos.get(j).y;
                newArr[i][j]=  (float) Math.sqrt(    Math.pow(a-b, 2)  +  Math.pow(c-d, 2));
                System.out.print(newArr[i][j] + "   ");
            }
            System.out.println();
	}
	
  return newArr;

	}

}
