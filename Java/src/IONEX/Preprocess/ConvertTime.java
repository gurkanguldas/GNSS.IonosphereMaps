package IONEX.Preprocess;

public class ConvertTime {

	public static double DOY(double year , double month , double day)//Day of year.
	{
		double SumDay = 0.0;
		switch((int)month) {
			case 12:
				SumDay += 30.0;
			case 11:
				SumDay += 31.0;
			case 10:
				SumDay += 30.0;
			case 9:
				SumDay += 31.0;
			case 8:
				SumDay += 31.0;
			case 7:
				SumDay += 30.0;
			case 6:
				SumDay += 31.0;
			case 5:
				SumDay += 30.0;
			case 4:
				SumDay += 31.0;
			case 3:
				if(year % 4.0 == 0.0)
					SumDay += 29.0;
				else
					SumDay += 28.0;
			case 2:
				SumDay += 31.0;
			break;
		}
		SumDay += day;
		
		if(year % 4.0 == 0.0)
			SumDay = (SumDay - 1.0) / 366.0;
		else
			SumDay = (SumDay - 1.0) / 365.0;
		
		return year+SumDay;
	}
	public static double TOD(double hour , double minute , double second)
	{
		return (hour + minute / 60.0 + second / 3600.0) / 24.0;
	}
}
