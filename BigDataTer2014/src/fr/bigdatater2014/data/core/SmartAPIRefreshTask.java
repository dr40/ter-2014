package fr.bigdatater2014.data.core;

import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.orientation.Orientation;

public abstract class SmartAPIRefreshTask extends APIRefreshTask {
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////

	protected int _eventLoadedCount;
	protected double _latitude;
	protected double _longititude;
	protected Orientation.Type _wantedOrientation;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////

	public SmartAPIRefreshTask(ToulouseDataApi api, double longititude, double latitude, Orientation.Type orientation) {
		super(api);
		_eventLoadedCount = 0;
		_longititude = longititude;
		_latitude = latitude;
		_wantedOrientation = orientation;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Event
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean doAddEvenement(Evenement e) {
		if ((e.getLocation().getLatitude() != 0) && (e.getLocation().getLongitude() != 0)) {
			/* Check the angle */
			boolean bAdd = false;
			switch (_wantedOrientation) {
			case NORTH:
				bAdd = (e.getLocation().getLatitude() >= _latitude);
			break;
			case NORTH_EAST:
				bAdd = ((e.getLocation().getLatitude() >= _latitude) && (e.getLocation().getLongitude() >= _longititude));
			break;
			case NORTH_WEST:
				bAdd = ((e.getLocation().getLatitude() >= _latitude) && (e.getLocation().getLongitude() <= _longititude));
			break;
			case EAST:
				bAdd =(e.getLocation().getLongitude() >= _longititude);
			break;
			case WEST:
				bAdd =(e.getLocation().getLongitude() <= _longititude);
			break;
			case SOUTH:
				bAdd = (e.getLocation().getLatitude() <= _latitude);
			break;
			case SOUTH_EAST:
				bAdd = ((e.getLocation().getLatitude() <= _latitude) && (e.getLocation().getLongitude() >= _longititude));
			break;
			case SOUTH_WEST:
				bAdd = ((e.getLocation().getLatitude() <= _latitude) && (e.getLocation().getLongitude() <= _longititude));
			break;
			}
			
			//Orientation.Type o = Orientation.getOrientation(_latitude, _longititude, e.getLocation().getLatitude(), e.getLocation().getLongitude());
			
			//bAdd = (o == _wantedOrientation);
			/* Add if angle correct */
			if ((bAdd) || (_pageReaded >=8)){
				_eventLoadedCount++;
				if (_eventLoadedCount > 5) {
					stop = true;
					return false;
				}
			}
			return bAdd;
		} else {
			return false;
		}
		
	}
	
}
