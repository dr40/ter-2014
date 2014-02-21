package fr.bigdatater2014;

import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import fr.bigdatater2014.orientation.Orientation;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActMain extends Activity implements SensorEventListener {

	private ImageView image;
	
	private float currentDegree = 0f;
	
	// device sensor manager
	private SensorManager mSensorManager;
	private Sensor mOrientation;
	
	TextView txtDirection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
		
		image = (ImageView) findViewById(R.id.imageCompass);
		
        // Degree
        txtDirection = (TextView) findViewById(R.id.textTitle);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        		
        Button bMap = (Button) findViewById(R.id.btnMap);
        bMap.setOnClickListener(ecouteurMap);
        
        Button bEvent = (Button) findViewById(R.id.btnEvent);
        bEvent.setOnClickListener(ecouteurEvent);
        
	  }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
        
        // Set text
		if (degree < 22) {
			txtDirection.setText("North");
			Globals.currentOrientation = Orientation.Type.NORTH;
		} else if (degree >= 22 && degree < 67) {
			txtDirection.setText("North East");
			Globals.currentOrientation = Orientation.Type.NORTH_EAST;
		} else if (degree >= 67 && degree < 112) {
			txtDirection.setText("East");
			Globals.currentOrientation = Orientation.Type.EAST;
		} else if (degree >= 112 && degree < 157) {
			txtDirection.setText("South East");
			Globals.currentOrientation = Orientation.Type.SOUTH_EAST;
		} else if (degree >= 157 && degree < 202) {
			txtDirection.setText("South");
			Globals.currentOrientation = Orientation.Type.SOUTH;
		} else if (degree >= 202 && degree < 247) {
			txtDirection.setText("South West");
			Globals.currentOrientation = Orientation.Type.SOUTH_WEST;
		} else if (degree >= 247 && degree < 292) {
			txtDirection.setText("West");
			Globals.currentOrientation = Orientation.Type.WEST;
		} else if (degree >= 292 && degree < 337) {
			txtDirection.setText("North West");
			Globals.currentOrientation = Orientation.Type.NORTH_WEST;
		} else if (degree >= 337) {
			txtDirection.setText("North");
			Globals.currentOrientation = Orientation.Type.NORTH;
		} else {
			txtDirection.setText("");
		}
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
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
