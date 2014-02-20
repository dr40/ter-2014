package fr.bigdatater2014.data;

import java.util.Date;
import java.util.LinkedList;

import fr.bigdatater2014.data.location.Location;
import fr.bigdatater2014.utils.json.JSON;
import fr.bigdatater2014.utils.string.StringUtils;

public class Evenement {

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Members
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected JSON _json;
	protected String _title;
	protected String _description;
	protected LinkedList<String> _types;
	protected LinkedList<String> _publics;
	protected LinkedList<String> _disciplines;
	protected Location _location;
	protected Date _startDate;
	protected Date _endDate;
	protected String _phone;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////////////
		
	public Evenement(JSON json) {
		_json = json;
		_title = json.getChildStringValue("title");
		_description = json.getChildStringValue("description");
		_location = new Location(json);
		_startDate = StringUtils.dateFromString(json.getChildStringValue("startDate"));
		_endDate = StringUtils.dateFromString(json.getChildStringValue("endDate"));
		_phone = json.getChildStringValue("locationPhone");
		_types = new LinkedList<String>();
		_publics = new LinkedList<String>();
		_disciplines = new LinkedList<String>();
		for(int i = 0, max = json.size(); i < max; i++) {
			JSON child = json.get(i);
			String name = child.getName();
			if (name.contains("type_")) {
				_types.add(child.getValue());
			} else if (name.contains("public_")) {
				_publics.add(child.getValue());
			} else if (name.contains("discipline_")) {
				_disciplines.add(child.getValue());
			}
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Getters
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getTitle() {
		String title = _title;
		title=title.replaceAll("\u2019", "'");
		title=title.replaceAll("&nbsp;", " ");
		title=title.replaceAll("/", "");
		title=title.replaceAll("\u2026", "?");
		return title;
	}
	public String getDescriptionHTML() {
		return _description;
	}
	public String getDescription() {
		String desc = StringUtils.removeHTMLMarkup(_description);
		desc=desc.replaceAll("\u2019", "'");
		desc=desc.replaceAll("&nbsp;", " ");
		desc=desc.replaceAll("/", "");
		desc=desc.replaceAll("\u2019", "'");
		return desc;
	}
	public Location getLocation() {
		return _location;
	}
	public Date getStartDate() {
		return _startDate;
	}
	public Date getEndDate() {
		return _endDate;
	}
	public String getPhone() {
		return _phone;
	}
	
	public int getTypeCount() {
		return _types.size();
	}
	public int getPublicCount() {
		return _publics.size();
	}
	public int getDisciplineCount() {
		return _disciplines.size();
	}
	
	public String getType(int index) {
		if ((index >= 0) && (index < _types.size())) {
			return _types.get(index);
		} else {
			return "";
		}
	}
	public String getPublic(int index) {
		if ((index >= 0) && (index < _publics.size())) {
			return _publics.get(index);
		} else {
			return "";
		}
	}
	public String getDiscipline(int index) {
		if ((index >= 0) && (index < _disciplines.size())) {
			return _disciplines.get(index);
		} else {
			return "";
		}
	}
	
	public String getTypesToString() {
		return _types.toString();
	}
	public String getPublicsToString() {
		return _publics.toString();
	}
	public String getDisciplinesToString() {
		return _disciplines.toString();
	}
	
	
}
