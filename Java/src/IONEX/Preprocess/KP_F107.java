package IONEX.Preprocess;

public class KP_F107 {
	double DayOfYear, TimeOfDay ,KP_Index , SolarFlux;

	public KP_F107(double dayOfYear, double timeOfDay, double kP_Index, double solarFlux) {
		super();
		TimeOfDay = timeOfDay;
		DayOfYear = dayOfYear;
		KP_Index = kP_Index;
		SolarFlux = solarFlux;
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

	public double getKP_Index() {
		return KP_Index;
	}

	public void setKP_Index(double kP_Index) {
		KP_Index = kP_Index;
	}

	public double getSolarFlux() {
		return SolarFlux;
	}

	public void setSolarFlux(double solarFlux) {
		SolarFlux = solarFlux;
	}
	
	
}
