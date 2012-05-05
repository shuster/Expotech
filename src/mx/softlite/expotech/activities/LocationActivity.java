package mx.softlite.expotech.activities;

import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.LatLonPoint;
import mx.softlite.expotech.model.LocationItemizedOverlay;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationActivity extends MapActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        
        MapView mapView = (MapView) findViewById(R.id.location);
        mapView.setBuiltInZoomControls(true);
        MapController mapController = mapView.getController();
        mapController.setZoom(15);
        mapController.setCenter(new LatLonPoint(19.02033, -98.24172));
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_location);
        LocationItemizedOverlay itemOverlay = new LocationItemizedOverlay(drawable, this);
        
        GeoPoint point = new LatLonPoint(19.02033, -98.24172);
        OverlayItem overlayitem = new OverlayItem(point, "Complejo Cultural Universitario BUAP", "BLVD. ATLIXCAYOTL 2499 ZONA ANGELOPOLIS, DENTRO DE EL COMPLEJO UNIVERSITARIO BUAP, 72800 Heroica Puebla de Zaragoza, Puebla");
        
        itemOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_close , menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.close:
	            System.exit(0);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}
