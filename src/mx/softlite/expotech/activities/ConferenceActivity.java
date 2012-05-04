package mx.softlite.expotech.activities;

import static mx.softlite.expotech.ApplicationConstant.URL_SPEAKERS;

import java.io.InputStream;
import java.net.URL;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Agenda;
import mx.softlite.expotech.model.Speaker;
import mx.softlite.expotech.util.JsonUtil;
import mx.softlite.expotech.util.ParseUtil;
import mx.softlite.expotech.util.Utils;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConferenceActivity extends Activity {

	private ProgressDialog progressDialog;
	private Speaker speaker;
	private JSONObject json;
	private Agenda agenda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conference);
		agenda = (Agenda) getIntent().getSerializableExtra("Agenda");
		
		if(Utils.haveInternet(getApplicationContext())){
			loadData(URL_SPEAKERS);
		}
		else{
			Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		if(Utils.haveInternet(getApplicationContext())){
			TextView txtSpeaker = (TextView) findViewById(R.id.conf_speaker);
			txtSpeaker.setOnClickListener(new OnClickListener() {				
				public void onClick(View v) {
					loadSpeakerAct();
				}
			} );
		}
		else{
			Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
		}
		
		TextView txtLocation = (TextView) findViewById(R.id.conf_location);
		txtLocation.setOnClickListener(new OnClickListener() {				
			public void onClick(View v) {
				loadLocationAct();
			}
		} );
	}
	
	private void loadSpeakerAct(){
		Intent intent = new Intent(this, SpeakerActivity.class);			
		intent.putExtra("Speaker", speaker);		
		startActivity(intent);
	}
	
	private void loadLocationAct(){
		Intent intent = new Intent(this, ZonesActivity.class);
		startActivity(intent);
	}

	private final Handler progressHandler = new Handler() {
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if(speaker != null){
				fillList();
			}
			else{
				messageEmpty();
			}
		}
	};
	
	private void loadData(final String url) {
		progressDialog = ProgressDialog.show(
				ConferenceActivity.this,
				"", 
				"Cargando...", 
				true);
		
		new Thread(new Runnable(){
			public void run() {
				json =  JsonUtil.getJsonfromURL(getApplicationContext(), url, "speakers.json");
				if(json != null){speaker = ParseUtil.getSpeakerFromId(json, agenda.getSpeakId());}
				progressHandler.sendEmptyMessage(0);
			}}).start();
    }	
	
	private void messageEmpty(){
		Toast.makeText(getApplicationContext(), "No se pudieron obtener los datos, Trate mas tarde.", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	private void fillList(){  
		TextView confHour = (TextView) findViewById(R.id.conf_hour);
		confHour.setText(getIntent().getExtras().getString("day") + " " + agenda.getHour());
		
		TextView confName = (TextView) findViewById(R.id.conf_name);
		confName.setText(agenda.getConfe());
				
		Bitmap bmp = null;
		try {
			ImageView imgSpeaker = (ImageView) findViewById(R.id.conf_img_speak);
			
			if(Utils.isFileSaved(getApplicationContext(), "speaker" +speaker.getUid()+ "-thumbnail.jpg")){
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(getApplicationContext(), "speaker" +speaker.getUid()+ "-thumbnail.jpg"));
			}
			else{
				URL url = new URL(speaker.getPhoto());
				InputStream imageInput = url.openConnection().getInputStream();
				Utils.saveFile(getApplicationContext(), imageInput, "speaker" +speaker.getUid()+ "-thumbnail.jpg");
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(getApplicationContext(), "speaker" +speaker.getUid()+ "-thumbnail.jpg"));
			}
			imgSpeaker.setImageBitmap(bmp);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		TextView confSpeak = (TextView) findViewById(R.id.conf_speaker);
		confSpeak.setText(speaker.getName() + " " + speaker.getSurname());
		
		TextView confLocation = (TextView) findViewById(R.id.conf_location);
		confLocation.setText(agenda.getLocaltion());
		
		TextView confDesc = (TextView) findViewById(R.id.conf_desc);
		confDesc.setText(agenda.getDesc());
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Handler getProgressHandler() {
		return progressHandler;
	}
	
}
