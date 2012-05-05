package mx.softlite.expotech.adapters;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Speaker;
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

public class SpeakerAdapter extends BaseAdapter{

	private List<Speaker> speakers;
	private Context context;
	
	public SpeakerAdapter(Context context, List<Speaker> speakers){
		this.speakers = speakers;
		this.context = context;
	}
	
	public int getCount() {
		return speakers.size();
	}
	
	public Object getItem(int position) {
		return speakers.get(position);
	}
	
	public long getItemId(int id) {
		return id;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Speaker speaker = speakers.get(position);
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_speaker, parent, false);						
		}
		
		TextView txtTmp = null;
		Bitmap bmp = null;
		try {
			ImageView imgSpeaker = (ImageView) convertView.findViewById(R.id.item_img_speaker);
			
			if(Utils.isFileSaved(context, "speaker" +speaker.getUid()+ "-thumbnail.jpg")){
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(context, "speaker" +speaker.getUid()+ "-thumbnail.jpg"));
			}
			else{
				URL url = new URL(speaker.getPhoto());
				InputStream imageInput = url.openConnection().getInputStream();
				Utils.saveFile(context, imageInput, "speaker" +speaker.getUid()+ "-thumbnail.jpg");
				bmp = BitmapFactory.decodeStream(Utils.getFileSaved(context, "speaker" +speaker.getUid()+ "-thumbnail.jpg"));
			}
			imgSpeaker.setImageBitmap(bmp);
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		txtTmp = (TextView) convertView.findViewById(R.id.item_name_speaker);
		txtTmp.setText(speaker.getName() + " " + speaker.getSurname());
		
		return convertView;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}
