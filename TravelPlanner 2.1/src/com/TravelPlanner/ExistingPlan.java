package com.TravelPlanner;


import TravelPlanner.db.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ExistingPlan extends Activity implements OnClickListener{
	
	
	private DBAdapter db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new DBAdapter(this);
		setContentView(populateLayOut());
			
	}
	
	private ScrollView populateLayOut() {
		ScrollView sv = new ScrollView(this);
		sv.setBackgroundColor(Color.parseColor("#3500ffff"));
		TableLayout main = new TableLayout(this);
		
		Cursor travel = db.getAllTravelPlan();
		if (travel.moveToFirst())  {      
			displayTravelPlan(travel,main);        
		}
		
		
		        
        sv.addView(main);
        return sv;
        
		
	}
	
		
	private void displayTravelPlan(final Cursor c, TableLayout main) {
		if (c.moveToFirst())
        {
            do {   
            	TableRow tr = new TableRow(this);
        		tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
              /* Create a Button to be the row-content. */
        		TextView name= new TextView(this);
        		name.setWidth(120);
        		name.setText(c.getString(1));
        		
        		tr.addView(name);
        		
        		Button b1 = new Button(this);
        		b1.setText("Detail");
        		b1.setHeight(10);
        		final int id = c.getInt(0);
        		b1.setId(id);
        		b1.setOnClickListener(this);
        		tr.addView(b1);
        		
        		Button delete = new Button(this);
        		delete.setText("Remove");
        		delete.setHeight(10);
       
        		delete.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						db.delete(id);
						db.deleteTravelPlan(id);
					    setContentView(populateLayOut());			
					}
        			
        			
        			
        		});
        		tr.addView(delete);
        		
        		main.addView(tr);
                
            } while (c.moveToNext());
        }
		
	}
	
	@Override
	public void onClick(View v1) {
		
		Intent showTravelCheckListIntent = new Intent(this,ShowTravelCheckList.class);
		Bundle bundle = new Bundle();
		bundle.putLong("id", v1.getId());
						  
		showTravelCheckListIntent.putExtras(bundle);
		startActivity(showTravelCheckListIntent);
		
	}
}
