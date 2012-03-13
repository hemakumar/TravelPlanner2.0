//Open-Android-TravelPlanner Copyright © 2012
//@author "Hema Kumar"
////** This file is part of TravelPlanner2.1 .This is free software: you can redistribute it
//* and/or modify it under the terms of the GNU General Public License as published by the
//* Free Software Foundation, either version 3 of the License, or any later version.
//* Travel Planner is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
//* without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//* See theGNU General Public License for more details.
//*
//* You should have received a copy of the GNU General Public License along with TravelPlanner2.1.
//* If not, see <http://www.gnu.org/licenses/>.
//* For feedback please mail at email id hemasid@gmail.com 


package com.TravelPlanner;

import java.util.Calendar;

import TravelPlanner.db.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class NewPlan extends Activity implements OnClickListener{
	private static final int SUB_ACTIVITY_REQUEST_CODE = 1337;

	
	private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    private static final String[] MONTHS = {"JAN","FEB","MAR","APR","MAY","JUN","JULY","AUG","SEP","OCT","NOV","DEC"};
    EditText txtFrom;
	private DBAdapter db = null;
	int result=1;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.newplan);
	    mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
     //   mMap = (Button)findViewById(R.id.Map);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
 

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date (this method is below)
        updateDisplay();
        
        Spinner travelType = (Spinner) findViewById(R.id.travelTypeSpinner);
        ArrayAdapter travelTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.TravelTypes, android.R.layout.simple_spinner_item);
        travelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelType.setAdapter(travelTypeAdapter);
        
        Spinner travelMode = (Spinner) findViewById(R.id.travelModeSpinner);
        ArrayAdapter travelModeAdapter = ArrayAdapter.createFromResource(
                this, R.array.TravelMode, android.R.layout.simple_spinner_item);
        travelModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelMode.setAdapter(travelModeAdapter);
        
        Spinner travelTime = (Spinner) findViewById(R.id.travelTimeSpinner);
        ArrayAdapter travelTimeAdapter = ArrayAdapter.createFromResource(
                this, R.array.TravelTime, android.R.layout.simple_spinner_item);
        travelTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelTime.setAdapter(travelTimeAdapter);
	    final Button addButton = (Button) findViewById(R.id.addTravelPlanButton);
	    addButton.setOnClickListener(this);
	    final Button cancleButton = (Button) findViewById(R.id.cancelTravelPlanButton);
	    cancleButton.setOnClickListener(this);
	    db = new DBAdapter(this);
	    
    final Button mMapFrom = (Button) findViewById(R.id.MapFrom);
    mMapFrom.setOnClickListener(this);
   final Button mMapTo = (Button) findViewById(R.id.MapTo);
   	mMapTo.setOnClickListener(this);
	    
	}
	


	private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(MONTHS[mMonth]).append(" ")
                    .append(mDay).append(", ")
                    .append(mYear).append(" "));
    }
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
            @Override
            protected Dialog onCreateDialog(int id) {
                switch (id) {
                case DATE_DIALOG_ID:
                    return new DatePickerDialog(this,
                                mDateSetListener,
                                mYear, mMonth, mDay);
                }
                return null;
            }
            
            @Override
            public void onActivityResult(int requestCode,int resultCode,Intent data)
            {
             super.onActivityResult(requestCode, resultCode, data);
          //   if (resultCode==1)
             EditText txtField;
			{String extraData;
          //   String extraData=data.getStringExtra("SearchTo");
           //  txtFrom = (EditText) NewPlan.this.findViewById(R.id.travelFromEntry);
           //                    txtFrom.setText(extraData);}
//             else{
            	// String fromField = (((EditText) findViewById(R.id.travelFromEntry)).getText().toString());
 				//if(fromField.length()==0){
			if(requestCode==0){//if(resultCode!=0){
            	 extraData=data.getStringExtra("SearchFrom");
            	// if(extraData.length()>0)
            	 txtField = (EditText) NewPlan.this.findViewById(R.id.travelFromEntry);
                 txtField.setText(extraData);}
            	 else
            	 {
            		 extraData=data.getStringExtra("SearchFrom");
            		// if(extraData.length()>0)
                	 { txtField = (EditText) NewPlan.this.findViewById(R.id.travelToEntry);
                     txtField.setText(extraData);}
            	 }
                 //}
// 				else
// 				{
// 					extraData=data.getStringExtra("SearchTo");
//         txtField = (EditText) NewPlan.this.findViewById(R.id.travelToEntry);
//                               txtField.setText(extraData);}
		}

            }
	    
	    @Override
		public void onClick(View v) {
	    	
			
				switch (v.getId()) {
				case R.id.addTravelPlanButton:
					
			    String name = (((EditText) findViewById(R.id.travelNameEntry)).getText().toString()).toUpperCase();
			    String mode = ((String)((Spinner) findViewById(R.id.travelModeSpinner)).getSelectedItem()).toUpperCase();
			    String type = ((String)((Spinner) findViewById(R.id.travelTypeSpinner)).getSelectedItem()).toUpperCase();
			    String date = ((TextView) findViewById(R.id.dateDisplay)).getText().toString();
			    String time = (String)((Spinner) findViewById(R.id.travelTimeSpinner)).getSelectedItem();
			    String from = (((EditText) findViewById(R.id.travelFromEntry)).getText().toString()).toUpperCase();
			    String to = (((EditText) findViewById(R.id.travelToEntry)).getText().toString()).toUpperCase();
			    long id;
			    if(name.length() == 0){
			    	
			    AlertDialog.Builder alert = new AlertDialog.Builder(NewPlan.this); 
			    alert.setMessage("Please enter the proper name of the application");	
			    alert.show();
			    }
			    //if(from.length() ==0)
			    //if(to.length() == 0)
			    
			    id = db.insertTravelPlan(name, mode, type, date, time, from, to);
			    			    
				Intent addNewPlanCheckIntent = new Intent(this, AddNewPlanCheck.class);
				Bundle bundle = new Bundle();
				bundle.putLong("id", id);
								  
				addNewPlanCheckIntent.putExtras(bundle);
				startActivity(addNewPlanCheckIntent);
				break;
				case R.id.cancelTravelPlanButton:
					Intent MainScreen = new Intent(this, MainActivity.class);
					startActivity(MainScreen);
					//Intent existingIntent = new Intent(this, ExistingPlan.class);
					//startActivity(existingIntent);
					break;
			case R.id.MapFrom:
					//string set;
				
				Intent intentFrom=new Intent(NewPlan.this,GooglemapsActivity.class);
				//String fromField = (((EditText) findViewById(R.id.travelFromEntry)).getText().toString());
				//if(fromField.length()==0)
				   intentFrom.putExtra("SearchFrom", "ActivityFrom");
				//else
					//intentFrom.putExtra("SearchTo", "ActivityTo");
				 result=0;
				   startActivityForResult(intentFrom, result);
				   break;
	case R.id.MapTo:
//				//string set;
		Intent intentFrom1=new Intent(NewPlan.this,GooglemapsActivity.class);
		intentFrom1.putExtra("SearchFrom", "ActivityFrom");
		 result=1;
		   startActivityForResult(intentFrom1, result);
		   break;
		
			
			/*Intent intentTo=new Intent(NewPlan.this,GooglemapsToActivity.class);
			   intentTo.putExtra("SearchTo", "ActivityTo");
			   //final int result=1;
			   startActivityForResult(intentTo, result);
			   break;*/
				   

				}
				}

//		@Override
//		protected void onActivityResult(int requestCode, int resultCode,
//				Intent data) {
//			// TODO Auto-generated method stub
//			//super.onActivityResult(requestCode, resultCode, data);
//			switch (requestCode) { 
//			case CHOOSE_FIGHTER:       
//				// This is the standard resultCode that is sent back if the       
//				// activity crashed or didn't doesn't supply an explicit result. 
//				if (resultCode == RESULT_CANCELED){              
//					myMessageboxFunction("Fight cancelled");     
//					}            
//				else {                
//						myFightFunction(data);         
//						}      
//				default:    
//					break;
//			}
//		}
}

