package fr.bigdatater2014.data.core;

import fr.bigdatater2014.Constants;
import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.data.listener.APIListener;
import fr.bigdatater2014.data.listener.APIRefreshDetails;
import fr.bigdatater2014.data.measurement.Measurement;
import fr.bigdatater2014.utils.http.HTTPGet;
import fr.bigdatater2014.utils.http.StringURL;
import fr.bigdatater2014.utils.json.JSON;

public abstract class APIRefreshTask implements APIListener, Runnable {
	

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////

	protected long _startTime;
	protected long _totalHTTPTime;
	protected long _totalJSONTime;
	protected long _totalAnalyseTime;
	protected int _eventLoadedCount;
	protected int _eventTotalCount;
	protected int _eventByPage;
	protected int _eventPageCount;
	protected ToulouseDataApi _api;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////

	public APIRefreshTask(ToulouseDataApi api, int eventByPage) {
		_api = api;
		_startTime = 0;
		_totalHTTPTime = 0;
		_totalJSONTime = 0;
		_totalAnalyseTime = 0;
		_eventLoadedCount = 0;
		_eventTotalCount = 0;
		_eventByPage = eventByPage;
		_eventPageCount = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Main
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void run() {
		/* Initialize measurement */
		_startTime = System.currentTimeMillis();
		_totalHTTPTime = 0;
		_totalJSONTime = 0;
		_totalAnalyseTime = 0;
		/* Get count */
		if (!getEventCount()) {
			return ;
		}
		/* Get all pages */
		for(int i = 1; i <= _eventPageCount; i++) {
			if (!getEventPage(i)) {
				return ;
			}
		}
		/* Finalize */
		onRefreshDone(new APIRefreshDetails(
			new Measurement(_totalHTTPTime, _totalJSONTime, _totalAnalyseTime),
			_eventTotalCount,
			_eventPageCount,
			_eventPageCount
		));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Get page events
	///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean getEventCount() {
		/* Initialize measurement */
		long localStartTime = System.currentTimeMillis();
		/* URL */
		StringURL url = new StringURL(Constants.API_URL);
		url.setParameter("query", "count");
		/* Get */
		HTTPGet http1 = new HTTPGet();
		String result = http1.getSync(url);
		long localHTTPGetTime = System.currentTimeMillis() - localStartTime;
		_totalHTTPTime += localHTTPGetTime;
		if (result != null) {
			/* OK */
			try {
				_eventTotalCount = Integer.parseInt(result);
				_eventPageCount = (_eventTotalCount / _eventByPage);
				onRefreshCountRetrieved(_eventByPage, _eventPageCount, _eventTotalCount);
				return true;
			} catch (Exception e) {
				/* Error */
				onRefreshError();
				return false;
			}
		} else {
			/* Error */
			onRefreshError();
			return false;
		}
	}
	
	private boolean getEventPage(int currentPage) {
		/* Initialize measurement */
		long localStartTime = System.currentTimeMillis();
		long localHTTPGetTime = 0;
		long localJSONGetTime = 0;
		long localAnalyseDataTime = 0;
		int newEventCount = 0;
		/* URL */
		StringURL url = new StringURL(Constants.API_URL);
		url.setParameter("query", "export");
		url.setParameter("curPage", String.valueOf(currentPage));
		url.setParameter("nbMaxPage", String.valueOf(_eventByPage));
		/* Get */
		HTTPGet http1 = new HTTPGet();
		String result = http1.getSync(url);
		localHTTPGetTime = System.currentTimeMillis() - localStartTime;
		_totalHTTPTime += localHTTPGetTime;
		if (result != null) {
			/* Ok */
			if (result.length() > 0) {
				localStartTime = System.currentTimeMillis();
				JSON json = new JSON(result);
				System.out.println("JSON event count: " + json.size());
				localJSONGetTime = System.currentTimeMillis() - localStartTime;
				_totalJSONTime += localJSONGetTime;
				/* Analyse */
				localStartTime = System.currentTimeMillis();
				analyseJSON(json);
				localAnalyseDataTime = System.currentTimeMillis() - localStartTime;
				_totalJSONTime += localAnalyseDataTime;
				/* Prevent listeners */
				APIRefreshDetails details = new APIRefreshDetails(
						new Measurement(localHTTPGetTime, localJSONGetTime, localAnalyseDataTime),
						newEventCount,
						currentPage,
						_eventPageCount
				);
				onRefreshProgress(details);
			}
			return true;
		} else {
			/* Error */
			onRefreshError();
			return false;
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Analyse JSON
	///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean analyseJSON(JSON json) {
		if (_api != null) {
			for(int i = 0, max = json.size(); i < max; i++) {
				Evenement e = new Evenement(json.get(i));
				_api.addEvenement(e);
			}
		}
		return true;
	}
	
	
}
