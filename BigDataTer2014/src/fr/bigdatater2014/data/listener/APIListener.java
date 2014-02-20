package fr.bigdatater2014.data.listener;

public interface APIListener {

	public void onRefreshCountRetrieved(int eventByPage, int pageCount, int totalEventCount);
	public void onRefreshProgress(APIRefreshDetails details);
	public void onRefreshDone(APIRefreshDetails details);
	public void onRefreshError();
	
}
