package mx.softlite.expotech.activities;

import mx.softlite.expotech.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ExpoTechActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       loadData();
    }
    
    private void gotoAgenda(){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		this.finish();
	}
    
    private void loadData() {
 
		new Thread(new Runnable(){
			public void run() {
				try {
					Thread.sleep(3000);
					gotoAgenda();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}}).start();
    }
}