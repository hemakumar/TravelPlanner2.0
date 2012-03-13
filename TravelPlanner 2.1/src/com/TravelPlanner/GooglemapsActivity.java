package com.TravelPlanner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
public class GooglemapsActivity extends MapActivity {
	protected final int SUCCESS_RETURN_CODE = 1;
    EditText txtSearch;
     MapView mapView; 
     MapController mc;
     GeoPoint p;
     String extraData;
    
 @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //EditText name = (EditText) this.findViewById(R.id.travelNameText);
    //  name.setText(txtSearch.getText().toString());
    }

 class MapOverlay extends com.google.android.maps.Overlay
         {
	 
         @Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		
		Point screenPts = new Point();
        mapView.getProjection().toPixels(p, screenPts);

		Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.marker);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);  
            super.draw(canvas, mapView, shadow); 
	}

		@Override
         public boolean draw(Canvas canvas, MapView mapView, 
         boolean shadow, long when) 
         {
             super.draw(canvas, mapView, shadow);                   

             //---translate the GeoPoint to screen pixels---
             Point screenPts = new Point();
             mapView.getProjection().toPixels(p, screenPts);

             //---add the marker---
             Bitmap bmp = BitmapFactory.decodeResource(
                 getResources(), R.drawable.marker);            
             canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
             return true;
         }

               @Override
                public boolean onTouchEvent(MotionEvent event, MapView mapView) 
                {   
                //---when user lifts his finger---
                  //---when user lifts his finger---
             if (event.getAction() == 1) {                
                 //GeoPoint 
            	 p = mapView.getProjection().fromPixels(
                     (int) event.getX(),
                     (int) event.getY());

                 Geocoder geoCoder = new Geocoder(
                     getBaseContext(), Locale.getDefault());
                 try {
                     List<Address> addresses = geoCoder.getFromLocation(
                         p.getLatitudeE6()  / 1E6, 
                         p.getLongitudeE6() / 1E6, 1);

                     String add = "";
                     if (addresses.size() > 0) 
                     {
                         for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                              i++)
                            add += addresses.get(0).getAddressLine(i) + "\n";
                     }
                    // List<Overlay> mapOverlays = mapView.getOverlays();
                     mc.animateTo(p);
                     Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                     txtSearch = (EditText) GooglemapsActivity.this.
                             findViewById(R.id.txtMapSearch);
                                       txtSearch.setText(add);

                 }
                 catch (IOException e) {                
                     e.printStackTrace();
                 }   
                 return true;
             }
             else                
                 return false;
         }


       }
        /** Called when the activity is first created. */
 
    @Override
    protected boolean isRouteDisplayed() 
    {
    return false;
    }

    public void changeMap(String area)
    {

        mapView = (MapView) findViewById(R.id.mapview);
        MapController mc=mapView.getController();


        GeoPoint myLocation=null;

        double lat = 0;
        double lng = 0;
        try
        {

         Geocoder g = new Geocoder(this, Locale.getDefault()); 

           java.util.List<android.location.Address> result=g.getFromLocationName(area, 1); 
         if(result.size()>0){
            Toast.makeText(GooglemapsActivity.this, "country: " + 
                    String.valueOf(result.get(0).getCountryName()), Toast.LENGTH_SHORT).show();
              lat = result.get(0).getLatitude();
              lng = result.get(0).getLongitude();
              EditText name = (EditText) this.findViewById(R.id.txtMapSearch);
                name.setText("country: " + 
                     String.valueOf(result.get(0).getCountryName()).toString());

                        }             
            else{
            Toast.makeText(GooglemapsActivity.this, "record not found"
                     ,Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch(IOException io)
        {
            Toast.makeText(GooglemapsActivity.this, "Connection Error"
                 , Toast.LENGTH_SHORT).show();
        }
        myLocation = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);

        
        //Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        mc.animateTo(myLocation);
        mc.setZoom(10); 
       mapView.invalidate();
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Intent sender=getIntent();
        extraData=sender.getExtras().getString("SearchFrom");
        
       if(extraData.length()<=0)
        	 extraData=sender.getExtras().getString("SearchTo");

        Button btnSearch=(Button) findViewById(R.id.btnSearch);
        //txtSearch=(EditText)findViewById(R.id.c);

        btnSearch.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {


    txtSearch=(EditText)findViewById(R.id.txtMapSearch);
    String area=txtSearch.getText().toString();
    GooglemapsActivity.this.changeMap(area);


            }
        });
        Button btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				txtSearch=(EditText)findViewById(R.id.txtMapSearch);
			    String area=txtSearch.getText().toString();
			   if(extraData.length()>0)
			    intent.putExtra("SearchFrom", area);
			   else if(extraData.length()<=0)
			   	intent.putExtra("SearchTo", area);
			    setResult(RESULT_OK, intent);
			    finish();
				
			}
		});
        

        mapView = (MapView) findViewById(R.id.mapview);

        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  


        View zoomView = mapView.getZoomControls(); 

        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        mc = mapView.getController();
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        p = new GeoPoint(
              (int) (lat * 1E6), 
              (int) (lng * 1E6));

          mc.animateTo(p);
          mc.setZoom(17); 
          mapView.invalidate();
          mc.animateTo(p);
          mc.setZoom(17); 
      //---Add a location marker---
              MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);  
       // listOfOverLays.add(marker);
       
  
        mapView.invalidate();


    }
}

