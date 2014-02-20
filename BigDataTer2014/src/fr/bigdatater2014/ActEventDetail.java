package fr.bigdatater2014;

import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.ToulouseDataApi;
import fr.bigdatater2014.utils.string.StringUtils;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ActEventDetail extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_act_event_detail);
	
	    Intent intent = getIntent();
	    // On récupère la position
	    int position = intent.getIntExtra("position", 0);
	    // On récupère l'event correpondant
		Evenement e = Globals.dataAPI.getEvenement(position);
	    
		String[] list = {e.getTitle(),
				e.getDescription(),
				StringUtils.stringFromDate(e.getStartDate()),
				StringUtils.stringFromDate(e.getEndDate()),
				e.getPhone()};
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	       
	    setListAdapter(adapter);
		
        Button bExit = (Button) findViewById(R.id.btnDetExit);
        bExit.setOnClickListener(ecouteurDetailExit);

    }

    private OnClickListener ecouteurDetailExit = new OnClickListener() {
        @Override 
        public void onClick(View arg0) {
            System.exit(RESULT_OK);
        }
    };
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_event_detail, menu);
		return true;
	}

}
