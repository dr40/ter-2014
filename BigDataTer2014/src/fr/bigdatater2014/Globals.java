package fr.bigdatater2014;

import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.orientation.Orientation;

public class Globals {

	static ToulouseDataApi dataAPI = new ToulouseDataApi();
	static double currentLatitude = 43.617; 
	static double currentLongitude = 1.450;
	static double currentOrientationAngle = 0;
	static Orientation.Type currentOrientation = Orientation.Type.EAST;
	
}
