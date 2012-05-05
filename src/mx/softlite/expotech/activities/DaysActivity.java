package mx.softlite.expotech.activities;

import static mx.softlite.expotech.ApplicationConstant.FILE_NAME_AGENDA1;
import static mx.softlite.expotech.ApplicationConstant.FILE_NAME_AGENDA2;
import static mx.softlite.expotech.ApplicationConstant.URL_AGENDA_DAY1;
import static mx.softlite.expotech.ApplicationConstant.URL_AGENDA_DAY2;

import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.adapters.AgendaAdapter;
import mx.softlite.expotech.model.Agenda;
import mx.softlite.expotech.util.JsonUtil;
import mx.softlite.expotech.util.ParseUtil;
import mx.softlite.expotech.util.Utils;

import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DaysActivity extends ListActivity{
	
	private ProgressDialog progressDialog;
	private List<Agenda> agendas;
	private JSONObject json;
	private String fileName;
	private String url;
	private boolean btnLeft = false;
	private boolean btnRight = true;
	private String dayCurrent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_holder_agenda);
		url = URL_AGENDA_DAY1;
		setItemsAgenda(url, R.string.mayo1, false, true);
			
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				loadActivity(position);
			}
		});
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		ImageButton arrowLeft = (ImageButton) findViewById(R.id.arrow_left);
		arrowLeft.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(btnLeft){
					url = URL_AGENDA_DAY1;
					setItemsAgenda(url, R.string.mayo1, false, true);
				}
			}
		});
		
		ImageButton arrowRight = (ImageButton) findViewById(R.id.arrow_right);
		arrowRight.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(btnRight){
					url = URL_AGENDA_DAY2;
					setItemsAgenda(url, R.string.mayo2, true, false);
				}
			}
		});
	}
	
	private void loadActivity(int position){
		Intent intent = new Intent(this, ConferenceActivity.class);			
		intent.putExtra("Agenda", agendas.get(position));
		intent.putExtra("day", getDayCurrent());
		
		startActivity(intent);
	}
	
	private void setItemsAgenda(String url, int resId, boolean btnLeft, boolean btnRight){
		fileName = (url.contains("agenda-d1"))?FILE_NAME_AGENDA1:FILE_NAME_AGENDA2;
		TextView txt = (TextView) findViewById(R.id.txt_day);
		txt.setText(resId);
		setDayCurrent(getResources().getString(resId));
		setBtnLeft(btnLeft);
		setBtnRight(btnRight);
		
		if(Utils.haveInternet(getApplicationContext())){
			loadData(url);
		}
		else{
			if(Utils.isFileSaved(getApplicationContext(), FILE_NAME_AGENDA1)){
				loadData(url);
			}
			else{
				messageEmpty();
			}
		}
	}
	
	private final Handler progressHandler = new Handler() {
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if(json != null){
				fillList();
			}
			else{
				messageEmpty();
			}
		}
	};

	private void loadData(final String url) {
		progressDialog = ProgressDialog.show(
				DaysActivity.this,
				"", 
				"Cargando...", 
				true);
		
		new Thread(new Runnable(){
			public void run() {
				json =  JsonUtil.getJsonfromURL(getApplicationContext(), url, fileName);
				if(json != null){setAgendas(ParseUtil.getAgendaFromJson(json));}
				progressHandler.sendEmptyMessage(0);
			}}).start();
    }	
	
	private void fillList(){       
       setListAdapter(new AgendaAdapter(getApplicationContext(), agendas));
	}
	
	private void messageEmpty(){
		Toast.makeText(getApplicationContext(), "No se pudieron obtener los datos; necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.refresh:
	        	if(Utils.haveInternet(getApplicationContext())){
	        		Utils.deleteFile(getApplicationContext(), fileName);
	    			loadData(url);
	    		}
	    		else{
	    			messageEmpty();
	    		}
	            return true;
	        case R.id.close:
	            System.exit(0);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}
	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}
	public List<Agenda> getAgendas() {
		return agendas;
	}
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isBtnLeft() {
		return btnLeft;
	}
	public void setBtnLeft(boolean btnLeft) {
		this.btnLeft = btnLeft;
	}
	public boolean isBtnRight() {
		return btnRight;
	}
	public void setBtnRight(boolean btnRight) {
		this.btnRight = btnRight;
	}
	public String getDayCurrent() {
		return dayCurrent;
	}
	public void setDayCurrent(String dayCurrent) {
		this.dayCurrent = dayCurrent;
	}
	
}
