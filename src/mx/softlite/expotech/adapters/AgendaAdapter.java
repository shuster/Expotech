package mx.softlite.expotech.adapters;

import java.util.List;

import mx.softlite.expotech.R;
import mx.softlite.expotech.model.Agenda;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AgendaAdapter extends BaseAdapter {

	private List<Agenda> agendas;
	private Context context;
	
	public AgendaAdapter(Context context, List<Agenda> agendas){
		this.agendas = agendas;
		this.context = context;
	}
	
	public int getCount() {
		return agendas.size();
	}

	public Object getItem(int position) {
		return agendas.get(position);
	}

	public long getItemId(int id) {
		return id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Agenda agenda = agendas.get(position);
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.agenda, parent, false);						
		}
		
		TextView txtTmp = null;		
		txtTmp = (TextView) convertView.findViewById(R.id.item_hour);
		txtTmp.setText(agenda.getHour());
		
		txtTmp = (TextView) convertView.findViewById(R.id.item_conf);
		txtTmp.setText(agenda.getConfe());
		
		txtTmp = (TextView) convertView.findViewById(R.id.item_location);
		txtTmp.setText("Lugar: " + agenda.getLocaltion());
		
		txtTmp = (TextView) convertView.findViewById(R.id.item_desc);
		txtTmp.setText(agenda.getDesc());
		
		return convertView;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
}
