package fr.bigdatater2014;

import fr.bigdatater2014.data.Evenement;
import fr.bigdatater2014.data.ToulouseDataApi;
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
	    // On récupère la position
	    int position = intent.getIntExtra("position", 0);
	    // On récupère l'event correpondant
		Evenement e = Globals.dataAPI.getEvenement(position);
	    
	    TextView title = (TextView) findViewById(R.id.labNameEvent);
	    title.setText(e.getTitle());
	    TextView desc = (TextView) findViewById(R.id.labDescEvent);
	    desc.setText(e.getDescription());
	    TextView start = (TextView) findViewById(R.id.labStartEvent);
	    start.setText(e.getStartDate().toString());
	    TextView end = (TextView) findViewById(R.id.labEndEvent);
	    end.setText(e.getEndDate().toString());
	    TextView phon = (TextView) findViewById(R.id.labPhoneEvent);
	    phon.setText(e.getPhone().toString());
	    
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
