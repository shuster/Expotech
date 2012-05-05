package mx.softlite.expotech.activities;

import java.io.InputStream;
import java.net.URL;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Company;
import mx.softlite.expotech.util.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyActivity extends Activity {

	private ProgressDialog progressDialog;
	private Company company;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company);
		
		company = (Company) getIntent().getSerializableExtra("Company");
		loadData();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		TextView txtWeb = (TextView) findViewById(R.id.company_web);
		txtWeb.setOnClickListener(new OnClickListener() {				
			public void onClick(View v) {
				loadPageWeb();
			}
		} );
	}
	
	private void loadPageWeb(){
		Intent browserInt = new Intent(Intent.ACTION_VIEW, Uri.parse(company.getUrl()));
		startActivity(browserInt);
	}
	
	private final Handler progressHandler = new Handler() {
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if(company != null){
				fillDataCompany();
			}
			else{
				messageEmpty();
			}
		}
	};
	
	private void loadData() {
		progressDialog = ProgressDialog.show(
				CompanyActivity.this,
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
	
	private void fillDataCompany(){
		Bitmap bmp = null;
		try {
			ImageView imageView = (ImageView) findViewById(R.id.company_img);
			
			if(Utils.isFileSaved(getApplicationContext(), company.getNid() + "-thumbnail.jpg")){
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(getApplicationContext(), company.getNid() + "-thumbnail.jpg"));
			}
			else{
				URL url = new URL(company.getLogo());
				InputStream imageInput = url.openConnection().getInputStream();
				Utils.saveFile(getApplicationContext(), imageInput, company.getNid() + "-thumbnail.jpg");
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(getApplicationContext(), company.getNid() + "-thumbnail.jpg"));
			}
			imageView.setImageBitmap(bmp);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "No se pudo cargar la imagen...", Toast.LENGTH_SHORT).show();
		}	
		
		TextView txtName = (TextView) findViewById(R.id.company_name);
		txtName.setText(company.getTitle());
		
		TextView txtDesc = (TextView) findViewById(R.id.company_desc);
		txtDesc.setText(company.getDesc());
	}
}
