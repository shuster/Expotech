package mx.softlite.expotech.model;

import java.io.Serializable;

public class Speaker implements Serializable{

	private static final long serialVersionUID = 5270780153929409422L;
	private String uid;
	private String confeId;
	private String name;
	private String surname;
	private String photo;
	private String desc;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getConfeId() {
		return confeId;
	}
	public void setConfeId(String confeId) {
		this.confeId = confeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
