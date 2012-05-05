package mx.softlite.expotech.activities;

import java.io.InputStream;
import java.net.URL;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Speaker;
import mx.softlite.expotech.util.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SpeakerActivity extends Activity{

	private ProgressDialog progressDialog;
	private Speaker speaker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speaker);
		speaker = (Speaker) getIntent().getSerializableExtra("Speaker");	
		
		loadData();
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
	
	private void loadData() {
		progressDialog = ProgressDialog.show(
				SpeakerActivity.this,
				"", 
				"Cargando...", 
				true);
		
		new Thread(new Runnable(){
			public void run() {
				progressHandler.sendEmptyMessage(0);
			}}).start();
    }	
	
	private void messageEmpty(){
		Toast.makeText(getApplicationContext(), "No se pudieron obtener los datos; necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	private void fillList(){  		
		TextView nameSpeak = (TextView) findViewById(R.id.speak_name);
		nameSpeak.setText(speaker.getName() + " " + speaker.getSurname());
				
		Bitmap bmp = null;
		try {
			ImageView imgSpeaker = (ImageView) findViewById(R.id.speak_img);
			
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
			Toast.makeText(getApplicationContext(), "No se pudo cargar la imagen...", Toast.LENGTH_SHORT).show();
		}	
		
		TextView speakDesc = (TextView) findViewById(R.id.speak_desc);
		speakDesc.setText(speaker.getDesc());
	}
}
