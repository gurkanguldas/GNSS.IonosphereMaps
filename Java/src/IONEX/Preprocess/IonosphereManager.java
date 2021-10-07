package IONEX.Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class IonosphereManager {

	private ReadData readData = new ReadData();
	public static String File_Name = "";
	Boolean Title = true;
	public void WriteTec(int minYear , int maxYear , int minDay , int maxDay , String Folder) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File_Name));
		bufferedWriter.write("DayOfYear,TimeOfDay,Latitude,Longitude,TEC\n");  
		
		for(int i = minYear ; i <= maxYear ; i++)
		{
			
			for(int j = minDay; j <= (i%4==0 ? maxDay+1 : maxDay) ; j++)
			{
				String URL = Folder;
				if(j < 10)                 URL += "/"+i+"/"+"casg00"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else if(j >= 10 && j <100) URL += "/"+i+"/"+"casg0"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else                       URL += "/"+i+"/"+"casg"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				
				ArrayList<TEC> TecMap = readData.TEC(URL);
				for (TEC tec : TecMap)
				{
					bufferedWriter.write(tec.DayOfYear+","+tec.TimeOfDay+","+tec.Lat+","+tec.Lon+","+tec.Tec+"\n"); 
				} 
				
				System.out.println(i+". yılın "+j+". günü okundu.");
			}
		}
		bufferedWriter.close();
	}
	public void WriteKP_F107(int minYear , int maxYear , String Folder) throws IOException 
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File_Name));
		bufferedWriter.write("DayOfYear,TimeOfDay,KP-Index,SolarFlux\n");  
		
		for (int i = minYear; i <= maxYear; i++) 
		{
			ArrayList<KP_F107> kp_f107 = readData.KP_SolarFlux(Folder+"/Kp_ap_Ap_SN_F107_"+i+".txt");
			
			for (KP_F107 index : kp_f107) 
			{
				bufferedWriter.write(index.DayOfYear+","+index.TimeOfDay+","+index.KP_Index+","+index.SolarFlux+"\n");
			}
			System.out.println(i+". yıl okundu.");
		}
		bufferedWriter.close();
	}
	public void WriteIonosphere(int minYear , int maxYear , int minDay , int maxDay , String TecFolder , String KPFolder) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File_Name));
		if(Title)
			bufferedWriter.write("DayOfYear,TimeOfDay,Latitude,Longitude,KP-Index,SolarFlux,TEC\n");;  
		Title = false;
		for(int i = minYear ; i <= maxYear ; i++)
		{
			ArrayList<KP_F107> kp_f107 = readData.KP_SolarFlux(KPFolder+"/Kp_ap_Ap_SN_F107_"+i+".txt");
			
			for(int j = minDay; j <= (i%4==0 ? maxDay+1 : maxDay) ; j++)
			{
				int KD = 0;
				String URL = TecFolder;
				
				if(j < 10)                 URL += "/"+i+"/"+"casg00"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else if(j >= 10 && j <100) URL += "/"+i+"/"+"casg0"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else                       URL += "/"+i+"/"+"casg"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				
				ArrayList<TEC> TecMap = readData.TEC(URL);
				for (TEC tec : TecMap)
				{
					double MinTime = Double.POSITIVE_INFINITY;
					
					for (int k = KD; k < kp_f107.size(); k++) 
					{
						double DeltaTime = Math.abs(tec.TimeOfDay - kp_f107.get(k).TimeOfDay);
						
						if(DeltaTime < MinTime && tec.DayOfYear == kp_f107.get(k).DayOfYear)
							MinTime = DeltaTime;
						
						else if( DeltaTime >= MinTime)
						{
							bufferedWriter.write(tec.DayOfYear+","+tec.TimeOfDay+","+tec.Lat+","+tec.Lon+","+kp_f107.get(k-1).KP_Index+","+kp_f107.get(k-1).SolarFlux+","+tec.Tec+"\n"); 
							KD = k-1;
							break;
						}
					}
				} 
			}
			System.out.println(i+". yıl tamamlandı.");
		}
		bufferedWriter.close();
	}
	public void WriteNormalizedIonosphere(int minYear , int maxYear , int minDay , int maxDay , String TecFolder , String KPFolder) throws IOException
	{
		double NminYear = 2000.0;
		double NmaxYear = 2030.0;
		double NminLat  =-90.0;
		double NmaxLat  = 90.0;
		double NminLon  =-180.0;
		double NmaxLon  = 180.0;
		double NminTec  = 0.0;
		double NmaxTec  = 1500.0;
		double NminKp   = 0.0;
		double NmaxKp   = 10.0;
		double NminSolar= 0.0;
		double NmaxSolar= 350.0;
		double NminDay= 0.0;
		double NmaxDay= 1.0;
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File_Name,true));
		if(Title)
			bufferedWriter.write("DayOfYear,TimeOfDay,Latitude,Longitude,KP-Index,SolarFlux,TEC\n");  
		Title = false;
		for(int i = minYear ; i <= maxYear ; i++)
		{
			ArrayList<KP_F107> kp_f107 = readData.KP_SolarFlux(KPFolder+"/Kp_ap_Ap_SN_F107_"+i+".txt");
			
			for(int j = minDay; j <= maxDay ; j++)
			{
				int KD = 0;
				String URL = TecFolder;
				
				if(j < 10)                 URL += "/"+i+"/"+"casg00"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else if(j >= 10 && j <100) URL += "/"+i+"/"+"casg0"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				else                       URL += "/"+i+"/"+"casg"+j+"0."+(i%2000<10 ? "0"+i%2000 : i%2000)+"i";
				
				ArrayList<TEC> TecMap = readData.TEC(URL);
				for (TEC tec : TecMap)
				{
					double MinTime = Double.POSITIVE_INFINITY;
					
					for (int k = KD; k < kp_f107.size(); k++) 
					{
						double DeltaTime = Math.abs(tec.TimeOfDay - kp_f107.get(k).TimeOfDay);
						
						if(DeltaTime < MinTime && tec.DayOfYear == kp_f107.get(k).DayOfYear)
							MinTime = DeltaTime;
						
						else if( DeltaTime >= MinTime)
						{
							float Year  = (float)((tec.DayOfYear - (NmaxYear + NminYear) / 2.0) / ((NmaxYear-NminYear) / 2.0));
							float Day   = (float)((tec.TimeOfDay - (NmaxDay + NminDay) / 2.0) / ((NmaxDay-NminDay) / 2.0));
							float Lat   = (float)((tec.Lat - (NmaxLat + NminLat) / 2.0) / ((NmaxLat-NminLat) / 2.0));
							float Lon   = (float)((tec.Lon - (NmaxLon + NminLon) / 2.0) / ((NmaxLon-NminLon) / 2.0));
							float KP    = (float)((kp_f107.get(k-1).KP_Index - (NmaxKp + NminKp) / 2.0) / ((NmaxKp-NminKp) / 2.0));
							float Solar = (float)((kp_f107.get(k-1).SolarFlux - (NmaxSolar + NminSolar) / 2.0) / ((NmaxSolar-NminSolar) / 2.0));
							float Tec   = (float)((tec.Tec - (NmaxTec + NminTec) / 2.0) / ((NmaxTec-NminTec) / 2.0));
							//if(ConvertTime.DOY(i, 1, j) == tec.DayOfYear)
								bufferedWriter.write(Year+","+Day+","+Lat+","+Lon+","+KP+","+Solar+","+Tec+"\n"); 
							KD = k-1;
							break;
						}
					}
				} 
			}
			System.out.println(i+". yıl tamamlandı.");
		}
		bufferedWriter.close();
	}
	public void PlotMap(float time , String URL , String Name ) throws IOException
	{
		String Map=ReadCSV(time,URL);
		
		JGnuPlot.Plot.P = 
				  "set title 'Global Ionosphere Model' \n"
				+ "unset key\n"
				+ "set tic scale 0\n"
				+ "set palette defined (0 'black' ,  100 'dark-blue', 300 'cyan', 500 'green' , 700 'red' , 900 'yellow', 1000 'white' )\n"
				+ "set cblabel 'TEC'\n"
				+ "set cbrange [0:1000]\n"
				+ "set cbtics 1000 \n"
				+ "set xrange [-180:180]\n"
				+ "set yrange [-90:90]\n"
				+ "$map2 << EOD\n"
				+ Map +"\n"
				+ "EOD \n"
				+ "plot '$map2' using 2:1:3 with image\n"
				+ "pause -1";
		JGnuPlot.Plot.Write("gnuplot/"+Name+".plt");
		JGnuPlot.Run.exec("gnuplot/"+Name+".plt");
	}
	public void PlotMapGif(String URL , String Name , int start) throws IOException
	{
		JGnuPlot.Plot.P = "#\n"
				+"set terminal gif size 1366,768\n"
				+"set terminal gif animate delay 15\n"
				+ "set output 'gnuplot/"+Name+".gif'\n"
				+ "set title 'Global Ionosphere Model' \n"
				+ "unset key\n"
				+ "set tic scale 0\n"
				+ "set palette defined (0 'black' ,  100 'dark-blue', 300 'cyan', 500 'green' , 700 'red' , 900 'yellow', 1000 'white' )\n"
				+ "set cbrange [0:1000]\n"
				+ "set cblabel 'TEC'\n"
				+ "set cbtics 1000 \n"
				+ "set xrange [-180:180]\n"
				+ "set yrange [-90:90]\n";
		for (float i = start; i < 24.0f; i+=2.0f) {
			JGnuPlot.Plot.P +="$map2 << EOD\n"
							+ReadCSV(i,URL)+"\n"
							+ "EOD \n"
							+ "plot '$map2' using 2:1:3 with image\n";
		}

		JGnuPlot.Plot.Write("gnuplot/"+Name+".plt");
		JGnuPlot.Run.exec("gnuplot/"+Name+".plt");
	}
	public String ReadCSV(float time , String URL) throws IOException
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(URL));
		String row = csvReader.readLine();
		
		if(row.contains("TimeOfDay"))
			row = csvReader.readLine();
		
		String Data = "";
		
		Boolean ZeroCheck = false;
		while (row != null) {
		    String[] data = row.split(",");
		    //float Year = 15.0f * Float.valueOf(data[0])+2015.0f;
		    double Time = (0.5 * Double.valueOf(data[1])+ 0.5) * 24.0;
		    float Lon = 90.0f * Float.valueOf(data[2]);
		    float Lat = 180.f * Float.valueOf(data[3]);
		    //float KP = 5.0f * Float.valueOf(data[4]) + 5.0f;
		    //float Solar = 175.0f * Float.valueOf(data[5])+175.0f;
		    float TEC = 750.0f * Float.valueOf(data[6])+750.0f;
		    if(TEC < 0.0f)
		    	TEC = 0.0f;
		    if(Math.abs(Time - time) <= 1.0e-5)
		    {
		    	if(Time <= 0.0f)
		    		ZeroCheck = true;
		    	
		    	Data += (Lon+"      "+Lat+"      "+TEC+"\n");
		    	
		    }
		    else {
		    	if(ZeroCheck) {
		    		break;
		    	}

		    }
		    	
		    row = csvReader.readLine();
		}
		csvReader.close();
		return Data;
	}
}
