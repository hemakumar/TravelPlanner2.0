package com.TravelPlanner;

import TravelPlanner.db.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewPlanCheck extends Activity implements OnClickListener{
	
	long id;
	private DBAdapter db = null;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checklistitem);
		Bundle bundle = getIntent().getExtras();
		id= bundle.getLong("id");
		db = new DBAdapter(this);
		Cursor c = db.getTravelPlan(id);
	    if (c.moveToFirst())        
            DisplayTitle1(c);
        else
            Toast.makeText(this, "No title found", 
            		Toast.LENGTH_LONG).show();
	     
	    View newButton = findViewById(R.id.addChecklistButton);
        newButton.setOnClickListener(this);
	
	}
	
	@Override
	public void onClick(View v) {
		
			switch (v.getId()) {
			case R.id.addChecklistButton:
				
				Intent checkListIntent = new Intent(this, CheckList.class);
				Bundle bundle = new Bundle();
				bundle.putLong("id", id);
								  
				checkListIntent.putExtras(bundle);
				startActivity(checkListIntent);
				
								             
				
			break;
			case R.id.existingPlanID:
				Intent existingIntent = new Intent(this, ExistingPlan.class);
				startActivity(existingIntent);
				break;
			
			// More buttons go here (if any) ...
			}
			}
	
	public void DisplayTitle(Cursor c)
    {
        Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "to:  " + c.getString(3) ,
                Toast.LENGTH_LONG).show();        
    } 

	public void DisplayTitle1(Cursor c)
    {
		TextView name = (TextView) this.findViewById(R.id.travelNameText);
		name.setText(c.getString(1));
		TextView mode = (TextView) this.findViewById(R.id.travelModeText);
		mode.setText(c.getString(2));
		TextView type = (TextView) this.findViewById(R.id.travelTypeText);
		type.setText(c.getString(3));
		TextView date = (TextView) this.findViewById(R.id.travelDateText);
		date.setText(c.getString(4));
		TextView time = (TextView) this.findViewById(R.id.travelTimeText);
		time.setText(c.getString(5));
		TextView from = (TextView) this.findViewById(R.id.travelFromText);
		from.setText(c.getString(6));
		TextView to = (TextView) this.findViewById(R.id.travelToText);
		to.setText(c.getString(7));
		c.close();
     
    } 

}
