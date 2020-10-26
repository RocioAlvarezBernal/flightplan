
/*
* FlightPlanner.java
* ------------------
* Reads in a file of cities and their corresponding flights,
* and allows the user to plan a flight route.
*/
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class FlightPlan extends ConsoleProgram {
	
	private HashMap<String, ArrayList<String>> flights;
	private ArrayList<String> cities; 
	
	public void run() {
		println("Flight Planner");
		readFlightData("flights.txt");
		println("list of all the cities ");
		printCityList(cities);
		println("you can now plan a trip");
		String startCity = readLine("starting city? ");
		ArrayList<String> route = new ArrayList<String>();
		route.add(startCity);
		String currentCity = startCity;
		while (true) {
			String nextCity = getNextCity(currentCity);
			route.add(nextCity);
			if (nextCity.equals(startCity))
				break;
			currentCity = nextCity;
		}
		printRoute(route);
	}

	private String getNextCity(String city) {
		ArrayList<String> destinations = getDestinations(city);
		String nextCity = null;
		while (true) {
			println("From " + city + " you can fly to:");
			printCityList(destinations);
			String prompt = "Where do you want to go from " + city + "? ";
			nextCity = readLine(prompt);
			if (destinations.contains(nextCity))
				break;
			println("No direct flights.");
		}
		return nextCity;
	}


	private ArrayList<String> getDestinations(String fromCity) {
		return flights.get(fromCity);
	}


	private void printRoute(ArrayList<String> route) {
		println("your route ");
		for (int i = 0; i < route.size(); i++) {
			if (i > 0)
				print(" ->");
			print(route.get(i));
		}
		println();
	}
	
	private void printCityList(ArrayList<String> cityList) {
		for (int i = 0; i < cityList.size(); i++) {
			String city = cityList.get(i);
			println(" " + city);
		}
	}


	private void readFlightEntry(String line) {
		int arrow = line.indexOf("->");
		if (arrow == -1) {
			throw new ErrorException("error " + line);
		}
		String fromCity = line.substring(0, arrow).trim();
		String toCity = line.substring(arrow + 2).trim();
		defineCity(fromCity);
		defineCity(toCity);
		getDestinations(fromCity).add(toCity);
	}
	
	private void readFlightData(String filename) {
		flights = new HashMap<String, ArrayList<String>>();
		cities = new ArrayList<String>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				if (line.length() != 0) {
					readFlightEntry(line);
				}
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	private void defineCity(String cityName) {
		if (!cities.contains(cityName)) {
			cities.add(cityName);
			flights.put(cityName, new ArrayList<String>());
		}
	}


}