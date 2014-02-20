package fr.bigdatater2014;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActEventDetail extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_act_event_detail);
	
	    Intent intent = getIntent();
	    int position = intent.getIntExtra("position", 0);
	
		String[] listAffichage = {"Event 1","Evenement 2"};
	    
	    // Here we turn your string.xml in an array
	    //String[] myKeys = getResources().getStringArray(R.array.sections);
	
	    TextView myTextView = (TextView) findViewById(R.id.labNameEvent);
	    //myTextView.setText(myKeys[position]);
	    myTextView.setText(listAffichage[position]);
	    
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
