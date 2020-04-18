package isen.Utils;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



/**
 * 
 * Contains functions able to know the walking or driving distance and time
 * between two coordinates or addresses using the Bing Maps API
 * 
 */

public class BingMapAPI {
	
	
	/*
	 * Place to put the key needed to use the API, you can find it on the API report on teams.
	 * 
	 * Endroit pour mettre la clef de l'API, cette clef est trouvable sur le rapport concernant l'API.
	 */
	
	public static String key = "";
	
	
	/**
	 * Sends an http request to the Bing Maps API and return its XML answer.
	 * @param firstWP       The coordinate of the first address
	 * 						An example of coordinate : "50.631694,3.042437"
	 * @param secondWP      The coordinate of the second address
	 * @param movingMethod  The method used to go from one point to the other :
	 * 						Accepted terms are "Walking" or "Driving"
	 * @return				The xml answer, ready to be used
	 * @throws Exception	If the API key is missing.
	 */
	public static String Request(String firstWP, String secondWP, String movingMethod) throws Exception {
		
		if(key == "")
		{
			System.err.println("The API key was not added. Its need to be added in the BingMapAPI class and discarded after. It shall not be put on GitHub. It can be found on the API report on teams.");
			System.err.println("La clef de l'API n'a pas été ajoutée. Elle doit être ajoutée dans la class BingMapAPI et supprimée ensuite. Elle ne doit pas être mise sur GitHub. Elle peut être trouvée sur le rapport concernant l'API sur teams.");
			throw new Exception();
		}
		
        URL bingMap = new URL("http://dev.virtualearth.net/REST/V1/Routes/" + movingMethod + "?wp.0=" + firstWP + "&wp.1=" + secondWP + "&optmz=distance&output=xml&key=" + key);
        URLConnection bc = null;
        try {
            bc = bingMap.openConnection();
        }
        catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                bc.getInputStream()));
        String inputLine;

        String xml = "";
        
        while ((inputLine = in.readLine()) != null) 
        {
        	xml += inputLine;
        }
        in.close();
        
        xml = xml.substring(xml.indexOf('<'));

        System.out.println(xml);
        
        return xml;
	}
	
	/**
	 * Get useful information from the XML file, useful to create a Distance Matrix or a Travel Duration Matrix.
	 * @param xml           The XML file in a String
	 * @param infoKeys		The list of the information's keys needed from the XML file.
	 * 						Possible useful terms are : "TravelDistance", "TravelDuration", "TravelDurationTraffic" (only useful for Driving)
	 * 							"DistanceUnit", "DurationUnit"
	 * 
	 * @return				The list of information from the keys, sorted in the infoKeys' order.
	 * @throws Exception
	 */
    public static ArrayList<String> getInformationFromXML(String xml, ArrayList<String> infoKeys) throws Exception { 
        
        ArrayList<String> returnInformation = new ArrayList<>();
    	
    	try {
        
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        InputSource is = new InputSource(new StringReader(xml));
	        Document doc = dBuilder.parse(is);
	        doc.getDocumentElement().normalize();
	        
	        System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
	        NodeList nodes = doc.getElementsByTagName("Response");
	        System.out.println("==========================");
	        
	        
	        
	        Node node = nodes.item(0);
	        System.out.println(node.getNodeName());

	        Element el = (Element) node;
	        Node nodeTwo = getSubNode("Route", el);
	        System.out.println(nodeTwo.getNodeName());
	        el = (Element) nodeTwo;
	        
	        
	        for(int i = 0; i < infoKeys.size(); i++)
	        {
	        	returnInformation.add(getValue(infoKeys.get(i), el));
	        }
	        
			System.out.println("DistanceUnit: " + getValue("DistanceUnit", el));
			System.out.println("DurationUnit: " + getValue("DurationUnit", el));
			System.out.println("TravelDistance: " + getValue("TravelDistance", el));
			System.out.println("TravelDuration: " + getValue("TravelDuration", el));
			System.out.println("TravelDurationTraffic: " + getValue("TravelDurationTraffic", el));
			
	        
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return returnInformation;
    }
	/**
	 * Get the subNode of an XML node by its key.
	 * @param tag			The key of the subNode.
	 * @param element		The node itself.
	 * @return				The subNode.
	 */
	private static Node getSubNode(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag);
		Node node = (Node) nodes.item(0);
		return node;
	}
	/**
	 * Get the value of a subNode within a node by its key.
	 * @param tag			The key of the value searched.
	 * @param element		The node itself.
	 * @return				A String containing the value searched.
	 */
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
