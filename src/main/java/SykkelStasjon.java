package BysykkelAPI;

public class SykkelStasjon {

	private String name;
	private String station_id;
	private String num_bikes_available;
	private String num_docks_available;
	
	public SykkelStasjon(String newName, String id){
		this.name = newName;
		this.station_id = id;
	}
	
	public String toString(){
		return "Navn: " + this.name + ", tilgjengelige sykler: " + this.num_bikes_available + ", tilgjengelige laaser: " + this.num_docks_available;
	}
	
	public void setNumbers(String bikes, String docks){
		this.num_bikes_available = bikes;
		this.num_docks_available = docks;
	}
	
	public String getID(){
		return this.station_id;
	}

}
