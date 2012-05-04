package mx.softlite.expotech.adapters;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Company;
import mx.softlite.expotech.util.Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CompanyAdapter extends BaseAdapter {

	private List<Company> companies;
	private Context context;
	
	public CompanyAdapter(Context context, List<Company> companies){
		this.companies = companies;
		this.context = context;
	}
	
	public int getCount() {
		return companies.size();
	}

	public Object getItem(int position) {
		return companies.get(position);
	}

	public long getItemId(int id) {
		return id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
		Company company = companies.get(position);
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_company, parent, false);						
		}
		
		TextView txtTmp = null;
		Bitmap bmp = null;
		try {
			ImageView imageView = (ImageView) convertView.findViewById(R.id.item_img_company);
			
			if(Utils.isFileSaved(context, company.getNid() + "-thumbnail.jpg")){
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(context, company.getNid() + "-thumbnail.jpg"));
			}
			else{
				URL url = new URL(company.getLogo());
				InputStream imageInput = url.openConnection().getInputStream();
				Utils.saveFile(context, imageInput, company.getNid() + "-thumbnail.jpg");
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(context, company.getNid() + "-thumbnail.jpg"));
			}
			imageView.setImageBitmap(bmp);
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		txtTmp = (TextView) convertView.findViewById(R.id.item_title);
		txtTmp.setText(company.getTitle());
		
//		txtTmp = (TextView) convertView.findViewById(R.id.item_url);
//		txtTmp.setText((company.getUrl()!=null)?company.getUrl():"");
		
		return convertView;
	}

	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

}
