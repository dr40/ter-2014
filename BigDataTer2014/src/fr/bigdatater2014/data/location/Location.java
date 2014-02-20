package fr.bigdatater2014.data.location;

import fr.bigdatater2014.utils.json.JSON;
import fr.bigdatater2014.utils.string.StringUtils;

public class Location {
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected double _latitude;
	protected double _longitude;
	protected String _address;
	protected String _addressExtra;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////

	public Location(JSON json) {
		_latitude = json.getChildNumberValue("coordinate_latitude");
		_longitude = json.getChildNumberValue("coordinate_longitude");
		_address = json.getChildStringValue("locationAddress");
		_addressExtra = json.getChildStringValue("locationExtra");
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Getters
	///////////////////////////////////////////////////////////////////////////////////////////////

	public double getLatitude() {
		return _latitude;
	}
	public double getLongitude() {
		return _longitude;
	}
	public String getAddressHTML() {
		return _address;
	}
	public String getAddressExtraHTML() {
		return _addressExtra;
	}
	public String getAddress() {
		return StringUtils.removeHTMLMarkup(_address);
	}
	public String getAddressExtra() {
		return StringUtils.removeHTMLMarkup(_addressExtra);
	}
	
}
