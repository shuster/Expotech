package mx.softlite.expotech.activities;

import static mx.softlite.expotech.ApplicationConstant.URL_AGENDA_DAY1;
import static mx.softlite.expotech.ApplicationConstant.URL_AGENDA_DAY2;
import mx.softlite.expotech.R;
import mx.softlite.expotech.util.Utils;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DaysActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listplaceholder);

		String[] days = getResources().getStringArray(R.array.days_agenda);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.days_item, days));
		
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(), AgendaActivity.class);
				intent.putExtra("url", (id == 0)?URL_AGENDA_DAY1:URL_AGENDA_DAY2);
				
				if(Utils.haveInternet(getApplicationContext())){
					startActivity(intent);
				}
				else{
					Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
