package mx.softlite.expotech.activities;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_holder_agenda);
		
		if(Utils.haveInternet(getApplicationContext())){
			url = URL_AGENDA_DAY1;
			setItemsAgenda(url, R.string.mayo1, false, true);
		}
		else{
			Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
		}
	
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		if(Utils.haveInternet(getApplicationContext())){
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
		else{
			Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
		}
	}
	
	private void setItemsAgenda(String url, int resId, boolean btnLeft, boolean btnRight){
		fileName = (url.contains("agenda-d1"))?"agenda1.json":"agenda2.json";
		TextView txt = (TextView) findViewById(R.id.txt_day);
		txt.setText(resId);
		loadData(url);
		setBtnLeft(btnLeft);
		setBtnRight(btnRight);
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
		Toast.makeText(getApplicationContext(), "No se pudieron obtener los datos, Trate mas tarde.", Toast.LENGTH_LONG).show();
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
	
}
