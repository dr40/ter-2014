package fr.bigdatater2014;

import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);
        
        Button bMap = (Button) findViewById(R.id.btnMap);
        bMap.setOnClickListener(ecouteurMap);
        
        Button bEvent = (Button) findViewById(R.id.btnEvent);
        bEvent.setOnClickListener(ecouteurEvent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_main, menu);
        return true;
    }
    
    private OnClickListener ecouteurMap = new OnClickListener() {
        @Override
        public void onClick(View view) {
        	Intent i = new Intent(view.getContext(), ActGoogleMap.class);
        	startActivity(i);
        }
    };
    
    private OnClickListener ecouteurEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
        	Intent i = new Intent(view.getContext(), ActListEvent.class);
        	startActivity(i);
        }
    };
}
