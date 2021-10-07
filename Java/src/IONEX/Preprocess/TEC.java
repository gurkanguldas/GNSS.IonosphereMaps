package IONEX.Preprocess;

public class TEC{
	double Lat , Lon , TimeOfDay , DayOfYear , Tec;

	public TEC() {
		
	}
	
	public TEC(double lat, double lon, double timeOfDay, double dayOfYear, double tec) {
		super();
		Lat = lat;
		Lon = lon;
		TimeOfDay = timeOfDay;
		DayOfYear = dayOfYear;
		Tec = tec;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLon() {
		return Lon;
	}

	public void setLon(double lon) {
		Lon = lon;
	}

	public double getTimeOfDay() {
		return TimeOfDay;
	}

	public void setTimeOfDay(double timeOfDay) {
		TimeOfDay = timeOfDay;
	}

	public double getDayOfYear() {
		return DayOfYear;
	}

	public void setDayOfYear(double dayOfYear) {
		DayOfYear = dayOfYear;
	}

	public double getTec() {
		return Tec;
	}

	public void setTec(double tec) {
		Tec = tec;
	}
	
}
