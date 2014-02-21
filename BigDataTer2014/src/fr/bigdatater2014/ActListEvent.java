package fr.bigdatater2014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.listener.APIListener;
import fr.bigdatater2014.data.listener.APIRefreshDetails;
import fr.bigdatater2014.utils.string.StringUtils;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ActListEvent extends ListActivity implements OnItemClickListener{

	private static int numPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_act_list_event);
		
		//String[] listAffichage = {"Event 1","Evenement 2"};
		
		final List<HashMap<String, String>> listItem = new ArrayList<HashMap<String,String>>();
		
		Globals.dataAPI.setSynchronizedMode(true);
		Globals.dataAPI.smartRefresh(Globals.currentLatitude, Globals.currentLongitude, Globals.currentOrientation);
		
		for (int i = 0;i < Globals.dataAPI.getEvenementCount();i++) {
			HashMap<String, String> item = new HashMap<String, String>();
			Evenement e = Globals.dataAPI.getEvenement(i);
			
			item.put("itemTitle", e.getTitle());
			item.put("itemBegin",StringUtils.stringFromDate(e.getStartDate()));
			item.put("itemEnd", StringUtils.stringFromDate(e.getEndDate()));
			listItem.add(item);
		}


        SimpleAdapter mSchedule = new SimpleAdapter 
        		(this.getBaseContext(), listItem, R.layout.activity_act_item_list_event,
                new String[] {"itemTitle","itemBegin","itemEnd"}, new int[] {R.id.labDetailTitle, R.id.labBegin,R.id.labEnd });
	    
	    setListAdapter(mSchedule);

        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
	        {

	            Intent n = new Intent(getApplicationContext(), ActEventDetail.class);
	            n.putExtra("position", position);
	            startActivity(n);
	        }
	    });
                
        Button bNext = (Button) findViewById(R.id.btnNext);
        bNext.setOnClickListener(ecouteurNext);
        
        Button bExit = (Button) findViewById(R.id.btnListReturn);
        bExit.setOnClickListener(ecouteurDetailExit);
	}
	
    private OnClickListener ecouteurDetailExit = new OnClickListener() {
        @Override 
        public void onClick(View arg0) {
            //System.exit(RESULT_OK);
        	Intent intent = new Intent();
        	setResult(RESULT_OK, intent);
        	finish();
        }
    };
    
    private OnClickListener ecouteurNext = new OnClickListener() {
        @Override 
        public void onClick(View arg0) {
        	Context context = getApplicationContext();
        	Toast toast = new Toast(context);
    	    toast = Toast.makeText(context, "Chargement en cours ...", Toast.LENGTH_LONG);
            toast.show();
            numPage++;
        }
    };
	
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
            // Then you start a new Activity via Intent
            Intent intent = new Intent();
            intent.setClass(this, ActEventDetail.class);
            intent.putExtra("position", position);
            // Or / And
            intent.putExtra("id", id);
            startActivity(intent);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_list_event, menu);
		return true;
	}
	

}
