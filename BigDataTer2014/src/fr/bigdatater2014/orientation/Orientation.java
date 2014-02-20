package fr.bigdatater2014.orientation;

public class Orientation {

	public enum Type {
		EAST,
		NORTH_EAST,
		NORTH,
		NORTH_WEST,
		WEST,
		SOUTH_WEST,
		SOUTH,
		SOUTH_EAST
	}
	
	public static Type getOrientation(double lat1, double long1, double lat2, double long2) {
		double angle = -Math.toDegrees(Math.atan2(long2-long1,lat2-lat1));

		if ((angle >= -105) && (angle <= -70)) {
			return Type.EAST;
		} else if ((angle >= -150) && (angle <= -105)) {
			return Type.NORTH_EAST;
		} else if (((angle >= -180) && (angle <= -150)) || ((angle >= 150) && (angle <= 180))) {
			return Type.NORTH;
		} else if ((angle >= 105) && (angle <= 150)) {
			return Type.NORTH_WEST;
		} else if ((angle >= 65) && (angle <= 105)) {
			return Type.WEST;
		} else if ((angle >= 20) && (angle <= 65)) {
			return Type.SOUTH_WEST;
		} else if (((angle >= -25) && (angle <= 0)) || ((angle >= 0) && (angle <= 20))) {
			return Type.SOUTH;
		} else {
			return Type.SOUTH_EAST;
		}
	}
	
}
