package mx.softlite.expotech.model;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class LocationItemizedOverlay extends ItemizedOverlay {

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context = null;
	
	public LocationItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public LocationItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
}
