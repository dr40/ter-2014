package fr.bigdatater2014;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActItemListEvent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_item_list_event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_item_list_event, menu);
		return true;
	}

}
