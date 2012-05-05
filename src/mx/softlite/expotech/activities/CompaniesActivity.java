package mx.softlite.expotech.activities;

import static mx.softlite.expotech.ApplicationConstant.FILE_NAME_COMPANIES;
import static mx.softlite.expotech.ApplicationConstant.URL_COMPANIES;

import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.adapters.CompanyAdapter;
import mx.softlite.expotech.model.Company;
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
import android.widget.ListView;
import android.widget.Toast;

public class CompaniesActivity extends ListActivity{

	private ProgressDialog progressDialog;
	private List<Company> companies;
	private JSONObject json;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_holder_company);
		
		if(Utils.haveInternet(getApplicationContext())){
			loadData(URL_COMPANIES);
		}
		else{
			if(Utils.isFileSaved(getApplicationContext(), FILE_NAME_COMPANIES)){
				loadData(URL_COMPANIES);
			}
			else{
				messageEmpty();
			}	
		}
		
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				loadActivity(position);
			}
		});
	}

	private void loadActivity(int position){
		Intent intent = new Intent(this, CompanyActivity.class);			
		intent.putExtra("Company", companies.get(position));		
		startActivity(intent);
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
				CompaniesActivity.this,
				"", 
				"Cargando...", 
				true);
		
		new Thread(new Runnable(){
			public void run() {
				json =  JsonUtil.getJsonfromURL(getApplicationContext(), url, FILE_NAME_COMPANIES);
				if(json != null){setCompanies(ParseUtil.getCompanyFromJson(json));}
				progressHandler.sendEmptyMessage(0);
			}}).start();
    }	
	
	private void fillList(){		
       setListAdapter(new CompanyAdapter(getApplicationContext(), companies));
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
	        		Utils.deleteFile(getApplicationContext(), FILE_NAME_COMPANIES);
	        		loadData(URL_COMPANIES);
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
	
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}
	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
}
