package mx.softlite.expotech.activities;

import mx.softlite.expotech.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class HomeActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Resources res = getResources(); 
	    TabHost tabHost = getTabHost();  
	    TabHost.TabSpec spec;  
	    Intent intent;
	    
	    intent = new Intent().setClass(this, DaysActivity.class);
	    spec = tabHost.newTabSpec("agenda").setIndicator("Agenda",
                res.getDrawable(R.drawable.ic_tab_agenda))
            .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, CompaniesActivity.class);
	    spec = tabHost.newTabSpec("empresa").setIndicator("Empresas",
                res.getDrawable(R.drawable.ic_tab_company))
            .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, LocationActivity.class);
	    spec = tabHost.newTabSpec("ubicacion").setIndicator("Ubicacion",
                res.getDrawable(R.drawable.ic_tab_location))
            .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, AboutActivity.class);
	    spec = tabHost.newTabSpec("acercade").setIndicator("Acerca de",
                res.getDrawable(R.drawable.ic_tab_about))
            .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
	
}
