package mx.softlite.expotech.model;

import java.io.Serializable;

public class Agenda implements Serializable{

	private static final long serialVersionUID = -7070320539372675012L;
	private String nid;
	private String hour;
	private String confe;
	private String localtion;
	private String desc;
	private String speakId;
	
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getConfe() {
		return confe;
	}
	public void setConfe(String confe) {
		this.confe = confe;
	}
	public String getLocaltion() {
		return localtion;
	}
	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSpeakId() {
		return speakId;
	}
	public void setSpeakId(String speakId) {
		this.speakId = speakId;
	}
	
}
