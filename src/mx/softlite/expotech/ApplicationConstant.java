package mx.softlite.expotech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ApplicationConstant {

	/************** URL's to JSON Data *****************/
	String URL_AGENDA_DAY1 = "http://expotechpyme.com/agenda-d1";
	String URL_AGENDA_DAY2 = "http://expotechpyme.com/agenda-d2";
	String URL_COMPANIES = "http://expotechpyme.com/expositores-mobile";
	String URL_SPEAKERS = "http://expotechpyme.com/lista-de-ponentes";
	
	/************** Names for files JSON *****************/
	String FILE_NAME_AGENDA1 = "agenda1.json";
	String FILE_NAME_AGENDA2 = "agenda2.json";
	String FILE_NAME_SPEAKERS = "speakers.json";
	String FILE_NAME_COMPANIES = "companies.json";
	
	List<String> PARAMS_AGENDA = new ArrayList<String>(Arrays.asList("Hora", "Conferencia", "Lugar", "Cuerpo"));
	List<String> PARAMS_COMPANY = new ArrayList<String>(Arrays.asList("title", "Logo", "Cuerpo", "field_company_web"));
}
