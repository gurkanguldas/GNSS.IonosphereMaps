package IONEX.Preprocess;

public class RMS {
	double Lat , Lon , TimeOfDay , DayOfYear , Rms;

	public RMS() {
		super();
	}

	public RMS(double lat, double lon, double timeOfDay, double dayOfYear, double rms) {
		super();
		Lat = lat;
		Lon = lon;
		TimeOfDay = timeOfDay;
		DayOfYear = dayOfYear;
		Rms = rms;
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

	public double getRms() {
		return Rms;
	}

	public void setRms(double rms) {
		Rms = rms;
	}
	
}
