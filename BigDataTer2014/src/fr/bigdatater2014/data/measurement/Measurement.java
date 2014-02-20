package fr.bigdatater2014.data.measurement;

public class Measurement {

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected long _httpGetElapsedTime;
	protected long _jsonParseElapsedTime;
	protected long _dataAnalyseElapsedTime;
	

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public Measurement() {
		_httpGetElapsedTime = 0;
		_jsonParseElapsedTime = 0;
		_dataAnalyseElapsedTime = 0;
	}
	public Measurement(long httpGetElapsedTime, long jsonParseElapsedTime, long dataAnalyseElapsedTime) {
		_httpGetElapsedTime = httpGetElapsedTime;
		_jsonParseElapsedTime = jsonParseElapsedTime;
		_dataAnalyseElapsedTime = dataAnalyseElapsedTime;
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Getters
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public long httpGetElapsedTime() {
		return _httpGetElapsedTime;
	}
	public long jsonElapsedTime() {
		return _jsonParseElapsedTime;
	}
	public long dataAnalyseElapsedTime() {
		return _dataAnalyseElapsedTime;
	}
	
	
}
