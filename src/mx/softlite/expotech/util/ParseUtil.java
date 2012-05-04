package mx.softlite.expotech.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.softlite.expotech.model.Agenda;
import mx.softlite.expotech.model.Company;
import mx.softlite.expotech.model.Speaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseUtil {

	/**
	 * Metodo generico para obtener datos de un JSON
	 * 
	 * @param json - objeto JSON 
	 * @param params - para metros a obtener
	 * @return
	 */
	public static List<Map<String, String>> getDataFromJson(JSONObject json, List<String> params){
		
		List<Map<String, String>> agenda = new ArrayList<Map<String,String>>();
        
        try {
			JSONArray  nodes = json.getJSONArray("nodes");
			
			for (int i = 0; i < nodes.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject nodeAll = nodes.getJSONObject(i);
				
				JSONObject node = nodeAll.getJSONObject("node");
				
				for (String param : params) {
					map.put(param,node.getString(param));
				}				
				agenda.add(map);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return agenda;
	}
	
	public static List<Agenda> getAgendaFromJson(JSONObject json){		
		List<Agenda> agendas = new ArrayList<Agenda>();
        
        try {
			JSONArray  nodes = json.getJSONArray("nodes");
			
			for (int i = 0; i < nodes.length(); i++) {
				Agenda agenda = new Agenda();
				
				JSONObject nodeAll = nodes.getJSONObject(i);				
				JSONObject node = nodeAll.getJSONObject("node");
				
				// Seteamos los datos de json a los atributos agenda
				agenda.setNid(node.getString("Nid"));
				agenda.setHour(node.getString("Hora"));
				agenda.setConfe(node.getString("Conferencia"));
				agenda.setLocaltion(node.getString("Lugar"));
				agenda.setDesc(node.getString("Cuerpo"));
				agenda.setSpeakId(node.getString("Ponente"));
				
				agendas.add(agenda);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return agendas;
	}
	
	public static List<Company> getCompanyFromJson(JSONObject json){		
		List<Company> companies = new ArrayList<Company>();
        
        try {
			JSONArray  nodes = json.getJSONArray("nodes");
			
			for (int i = 0; i < nodes.length(); i++) {
				Company company = new Company();
				
				JSONObject nodeAll = nodes.getJSONObject(i);				
				JSONObject node = nodeAll.getJSONObject("node");
				
				// Seteamos los datos de json a los atributos de company
				company.setNid(node.getString("nid"));
				company.setTitle(node.getString("title"));
				company.setLogo(node.getString("Logo"));				
				if(!node.isNull("field_company_web")){
					company.setUrl(node.getString("field_company_web"));
				}								
				company.setDesc(node.getString("Cuerpo"));
				
				companies.add(company);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return companies;
	}
	
	public static List<Speaker> getSpeakerFromJson(JSONObject json){		
		List<Speaker> speakers = new ArrayList<Speaker>();
        
        try {
			JSONArray  nodes = json.getJSONArray("nodes");
			
			for (int i = 0; i < nodes.length(); i++) {
				Speaker speaker = new Speaker();
				
				JSONObject nodeAll = nodes.getJSONObject(i);				
				JSONObject node = nodeAll.getJSONObject("node");
				
				// Seteamos los datos de json a los atributos de speaker
				speaker.setUid(node.getString("Uid"));
				if(!node.isNull("Conferencia")){
					speaker.setConfeId(node.getString("Conferencia"));
				}
				speaker.setName(node.getString("Nombre"));
				speaker.setSurname(node.getString("Apellidos"));
				speaker.setPhoto(node.getString("Foto"));
				if(!node.isNull("Descripción")){
					speaker.setDesc(node.getString("Descripción"));
				}
				
				speakers.add(speaker);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return speakers;
	}
	
	public static Speaker getSpeakerFromId(JSONObject json, String id){
		Speaker found = null;
		List<Speaker> speakers = getSpeakerFromJson(json);
		
		for (Speaker speaker : speakers) {
			if(speaker.getUid().equals(id)){
				found = speaker;
				break;
			}
		}
		
		return found; 
	}
}
