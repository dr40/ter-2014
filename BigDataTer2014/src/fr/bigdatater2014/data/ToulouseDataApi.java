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
	protected int _curPage;
	protected int _totalPage;
	protected boolean _finishDone;
	protected boolean _syncronized;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public ToulouseDataApi() {
		_events = new LinkedList<Evenement>();
		_listeners = new LinkedList<APIListener>();
		_curPage = 0;
		_totalPage = 0;
		_finishDone = false;
		_syncronized = false;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Properties
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isSyncronizedMode() {
		return _syncronized;
	}
	public void setSynchronizedMode(boolean syncMode) {
		_syncronized = syncMode;
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
	// Smart refresh methods
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void smartRefresh(double latitude, double longitude, double directionAngle) {
		refresh(0, 1, 10);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Refresh methods
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void refresh() {
		refresh(0, 0, Constants.API_EVENT_GET_DEFAULT_COUNT, null);
	}
	public void refresh(APIListener listener) {
		refresh(0, 0, Constants.API_EVENT_GET_DEFAULT_COUNT, listener);
	}

	public void refresh(int pageIndex, int pageLimit, int itemByPage) {
		refresh(pageIndex, pageLimit, itemByPage, null);
	}
	public void refresh(int pageIndex, int pageLimit, int itemByPage, APIListener listener) {
		
		class GetTask extends APIRefreshTask {
			APIListener _listener;
			public GetTask(ToulouseDataApi api, int eventByPage, int pageIndex, int pageLimit, APIListener listener) {
				super(api, eventByPage, pageIndex, pageLimit);
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
				_finishDone = true;
			}
			@Override
			public void onRefreshError() {
				if (_listener != null) {
					_listener.onRefreshError();
				}
				for(APIListener l : _listeners) {
					l.onRefreshError();
				}
				_finishDone = true;
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
		clearEvenement();
		_finishDone = false;
		new Thread(new GetTask(this, itemByPage, pageIndex, pageLimit, listener)).start();
		if (_syncronized) {
			while (!_finishDone) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
