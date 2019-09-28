package BysykkelAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.net.HttpURLConnection;
import com.google.gson.Gson;



public class myURL {
	
	public static void main(String[] args) {
	
		try{
			
//			Leser inn stasjonsdata fra link
			String stringUrl = "https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json";
			URL url = new URL(stringUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			BufferedReader reader = new BufferedReader ( new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer stationResponse = new StringBuffer();
			while((inputLine = reader.readLine()) != null){
				stationResponse.append(inputLine);
			}
			reader.close();
			
//			Splitter for aa sitte igjen med bare stasjoner
//			String stationResponseString = stationResponse.toString().split("stations\": \\[")[1];
//			stationResponseString = stationResponseString.split("\\]\\}\\}")[0];
			//SE HER; HILKEN AV DISSE
			String allStationsString = stationResponse.toString().split("stations\": \\[")[1];
			allStationsString = allStationsString.split("\\]\\}\\}")[0];
			
//			Objekt for Gson
			Gson g = new Gson();
//			SykkelStasjon newSS = g.fromJson(stationResponse.toString(), SykkelStasjon.class);

//			Hashmap som skal inneholde alle stasjoner med id som noekkel, for aa kunne legge inn tilgjengelighetsdata med id
			HashMap<String,SykkelStasjon> completeStations = new HashMap<String,SykkelStasjon>();
			

			//Array med alle sykkelstasjoner
			SykkelStasjon[] stasjoner = new SykkelStasjon[allStationsString.split("station_id").length - 1];
			int i = 0;
			

			for (String station: allStationsString.split("\\}\\,")){
				//Renser input
				station = station + "}";
				station = station.replace("}}", "}");
				
				//Lager stasjon og legger til i liste
				stasjoner[i] = g.fromJson(station, SykkelStasjon.class);
//				legger til i dictionary
				completeStations.put(stasjoner[i].getID(), stasjoner[i]); //kan legge inn tom liste og sette inn verdiene etterpaa
				i++;
			}
			

			//Leser inn stasjonsstatus-data
			String stringUrlAvail = "https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json";
			URL urlAvail = new URL(stringUrlAvail);
			HttpURLConnection connAvail = (HttpURLConnection) urlAvail.openConnection();
			
			BufferedReader readerAvail = new BufferedReader ( new InputStreamReader(connAvail.getInputStream()));

			String inputLineAvail;
			StringBuffer stationResponseAvail = new StringBuffer();
			
			while((inputLineAvail = readerAvail.readLine()) != null){
				stationResponseAvail.append(inputLineAvail);
			}
			readerAvail.close();

			//String med alle stasjonsstatus, uten fordata
			String allAvailString = stationResponseAvail.toString().split("stations\": \\[")[1];
			allAvailString = allAvailString.split("\\]\\}\\}")[0];


			for (String avail: Arrays.copyOfRange(allAvailString.split("station_id"), 1, allAvailString.split("station_id").length)){
				
				//Leser inn og renser id, num_bikes og num_docks vha. split
				String thisID = avail.split("\"")[2];
				String num_bikes = avail.split("\"")[13];
				num_bikes = num_bikes.split(",")[0];
				num_bikes = num_bikes.split(":")[1];
				String num_docks = avail.split("\"")[15];
				num_docks = num_docks.split("}")[0];
				
				//Hvis denne id-en finnes i keys skal stasjonen oppdateres
				if (completeStations.containsKey(thisID)){
					completeStations.get(thisID).setNumbers(num_bikes,num_docks);
					//Printer ut liste
					System.out.println(completeStations.get(thisID));
				}
				

			}

			
		} catch(Exception e){
			e.printStackTrace();
		}

	}
}
