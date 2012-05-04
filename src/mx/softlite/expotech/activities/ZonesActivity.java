package mx.softlite.expotech.activities;

import mx.softlite.expotech.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ZonesActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zones);
		
		WebView webView = (WebView) findViewById(R.id.webviewMap);
		webView.loadUrl("file:///android_res/drawable/zones.png");
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
	}
}
