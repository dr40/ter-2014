package fr.bigdatater2014.data.core;

import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.data.listener.APIRefreshDetails;

public abstract class SmartAPIRefreshTask extends APIRefreshTask {

	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////

	public SmartAPIRefreshTask(ToulouseDataApi api, double latitude, double longititude, double angle) {
		super(api);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Event
	///////////////////////////////////////////////////////////////////////////////////////////////

	protected boolean doAddEvenement(Evenement e) {
		/* By default: return true */
		return true;
	}
	
}
