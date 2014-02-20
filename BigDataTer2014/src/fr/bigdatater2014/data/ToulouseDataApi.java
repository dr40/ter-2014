package fr.bigdatater2014.data;

import java.util.LinkedList;

import fr.bigdatater2014.Constants;
import fr.bigdatater2014.data.core.APIRefreshTask;
import fr.bigdatater2014.data.listener.APIListener;
import fr.bigdatater2014.data.listener.APIRefreshDetails;

public class ToulouseDataApi {

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected LinkedList<Evenement> _events;
	protected LinkedList<APIListener> _listeners;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public ToulouseDataApi() {
		_events = new LinkedList<Evenement>();
		_listeners = new LinkedList<APIListener>();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Listener
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addListener(APIListener listener) {
		_listeners.add(listener);
	}
	public void removeListener(APIListener listener) {
		_listeners.remove(listener);
	}
	public void clearListener() {
		_listeners.clear();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Evenements
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clearEvenement() {
		_events.clear();
	}
	public void addEvenement(Evenement e) {
		_events.add(e);
	}
	public int getEvenementCount() {
		return _events.size();
	}
	public Evenement getEvenement(int index) {
		if ((index >= 0) && (index < _events.size())) {
			return _events.get(index);
		} else {
			return null;
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Types
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public LinkedList<String> getTypes() {
		LinkedList<String> items = new LinkedList<String>();
		for(Evenement e : _events) {
			for(int i = 0, max = e.getTypeCount(); i < max; i++) {
				boolean bFound = false;
				String str = e.getType(i);
				for(String s : items) {
					if (s.compareTo(str) == 0) {
						bFound = true;
						break;
					}
				}
				if (!bFound) {
					items.add(str);
				}
			}
		}
		return items;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Public
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public LinkedList<String> getPublics() {
		LinkedList<String> items = new LinkedList<String>();
		for(Evenement e : _events) {
			for(int i = 0, max = e.getPublicCount(); i < max; i++) {
				boolean bFound = false;
				String str = e.getPublic(i);
				for(String s : items) {
					if (s.compareTo(str) == 0) {
						bFound = true;
						break;
					}
				}
				if (!bFound) {
					items.add(str);
				}
			}
		}
		return items;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Types
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public LinkedList<String> getDiscipline() {
		LinkedList<String> items = new LinkedList<String>();
		for(Evenement e : _events) {
			for(int i = 0, max = e.getDisciplineCount(); i < max; i++) {
				boolean bFound = false;
				String str = e.getDiscipline(i);
				for(String s : items) {
					if (s.compareTo(str) == 0) {
						bFound = true;
						break;
					}
				}
				if (!bFound) {
					items.add(str);
				}
			}
		}
		return items;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Refresh methods
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void refresh() {
		refresh(null);
	}
	
	public void refresh(APIListener listener) {
		
		class GetTask extends APIRefreshTask {
			APIListener _listener;
			public GetTask(ToulouseDataApi api, int eventByPage, APIListener listener) {
				super(api, eventByPage);
				_listener = listener;
			}
			@Override
			public void onRefreshProgress(APIRefreshDetails details) {
				if (_listener != null) {
					_listener.onRefreshProgress(details);
				}
				for(APIListener l : _listeners) {
					l.onRefreshProgress(details);
				}
			}
			@Override
			public void onRefreshDone(APIRefreshDetails details) {
				if (_listener != null) {
					_listener.onRefreshDone(details);
				}
				for(APIListener l : _listeners) {
					l.onRefreshDone(details);
				}
			}
			@Override
			public void onRefreshError() {
				if (_listener != null) {
					_listener.onRefreshError();
				}
				for(APIListener l : _listeners) {
					l.onRefreshError();
				}
			}
			@Override
			public void onRefreshCountRetrieved(int eventByPage, int pageCount,
					int totalEventCount) {
				if (_listener != null) {
					_listener.onRefreshCountRetrieved(eventByPage, pageCount, totalEventCount);
				}
				for(APIListener l : _listeners) {
					l.onRefreshCountRetrieved(eventByPage, pageCount, totalEventCount);
				}
			}
		}
		new Thread(new GetTask(this, Constants.API_EVENT_GET_DEFAULT_COUNT, listener)).start();
	}
	
}