package com.TravelPlanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View newButton = findViewById(R.id.newPlanID);
        newButton.setOnClickListener(this);
        
        View existingButton = findViewById(R.id.existingPlanID);
        existingButton.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		
			switch (v.getId()) {
			case R.id.newPlanID:
			Intent newIntent = new Intent(this, NewPlan.class);
			startActivity(newIntent);
			break;
			case R.id.existingPlanID:
				Intent existingIntent = new Intent(this, ExistingPlan.class);
				startActivity(existingIntent);
				break;
			
			// More buttons go here (if any) ...
			}
			}

}