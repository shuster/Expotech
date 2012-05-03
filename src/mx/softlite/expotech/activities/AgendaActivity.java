package mx.softlite.expotech.activities;

import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.adapters.AgendaAdapter;
import mx.softlite.expotech.model.Agenda;
import mx.softlite.expotech.util.JsonUtil;
import mx.softlite.expotech.util.ParseUtil;

import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class AgendaActivity extends ListActivity{

	private ProgressDialog progressDialog;
	private List<Agenda> agendas;
	private JSONObject json;
	private String fileName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_holder_agenda);

		Bundle extras = getIntent().getExtras();
		if(extras != null){
			String url = extras.getString("url");
			fileName = (url.contains("agenda-d1"))?"agenda1.json":"agenda2.json";
			loadData(url);
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
				AgendaActivity.this,
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
		this.finish();
	}
	
	public List<Agenda> getAgendas() {
		return agendas;
	}
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
