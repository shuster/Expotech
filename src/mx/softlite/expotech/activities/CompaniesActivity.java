package mx.softlite.expotech.activities;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class CompaniesActivity extends ListActivity{

	private ProgressDialog progressDialog;
	private List<Company> companies;
	private JSONObject json;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listplaceholder);
		
		if(Utils.haveInternet(getApplicationContext())){
			loadData(URL_COMPANIES);
		}
		else{
			Toast.makeText(getApplicationContext(), "Necesita Conexion a Internet, Trate mas tarde.", Toast.LENGTH_LONG).show();
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
				CompaniesActivity.this,
				"", 
				"Cargando...", 
				true);
		
		new Thread(new Runnable(){
			public void run() {
				json =  JsonUtil.getJsonfromURL(getApplicationContext(), url, "companies.json");
				if(json != null){setCompanies(ParseUtil.getCompanyFromJson(json));}
				progressHandler.sendEmptyMessage(0);
			}}).start();
    }	
	
	private void fillList(){		
       setListAdapter(new CompanyAdapter(getApplicationContext(), companies));
	}
	
	private void messageEmpty(){
		Toast.makeText(getApplicationContext(), "No se pudieron obtener los datos, Trate mas tarde.", Toast.LENGTH_LONG).show();
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
