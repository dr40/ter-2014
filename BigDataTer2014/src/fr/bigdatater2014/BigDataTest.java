package fr.bigdatater2014;

import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.data.listener.APIListener;
import fr.bigdatater2014.data.listener.APIRefreshDetails;

public class BigDataTest {

	static boolean apiRefreshDone = false;
	static ToulouseDataApi api = new ToulouseDataApi();
	
	public static void main(String[] args) {
		/* Initialize */
		api.addListener(new APIListener() {

			@Override
			public void onRefreshProgress(APIRefreshDetails details) {
				System.out.println("Progress: page(" + details.getCurrentPage() + ", " + details.getTotalPage() + ")"); 
				System.out.println("\thttp time: " + details.getMeasurement().httpGetElapsedTime() + "ms");
				System.out.println("\tjson time: " + details.getMeasurement().jsonElapsedTime() + "ms");
				System.out.println("\tanalyse time: " + details.getMeasurement().dataAnalyseElapsedTime() + "ms");

				System.out.print("Event 10=" + api.getTypes().toString());
			}
			@Override
			public void onRefreshError() {
				System.out.println("Error");
				apiRefreshDone = true;
			}
			
			@Override
			public void onRefreshDone(APIRefreshDetails details) {
				System.out.println("OK:"); 
				System.out.println("\thttp time: " + details.getMeasurement().httpGetElapsedTime() + "ms");
				System.out.println("\tjson time: " + details.getMeasurement().jsonElapsedTime() + "ms");
				System.out.println("\tanalyse time: " + details.getMeasurement().dataAnalyseElapsedTime() + "ms");
				System.out.println("Event count: " + api.getEvenementCount());
				
				apiRefreshDone = true;
			}
			@Override
			public void onRefreshCountRetrieved(int eventByPage, int pageCount,
					int totalEventCount) {
				System.out.println("Count: page=" + pageCount + ", eventByPage=" + eventByPage);
			}

		});
		
		api.refresh(0, 1, 10);
		
		while (!apiRefreshDone) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
