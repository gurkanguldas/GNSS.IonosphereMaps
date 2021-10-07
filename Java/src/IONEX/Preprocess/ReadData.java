package IONEX.Preprocess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {
	private String START_TEC = "START OF TEC MAP";
	private String START_RMS = "START OF RMS MAP";
	private String END_FİLE  = "END OF FILE";
	public ArrayList<TEC> TEC(String URL) throws IOException
	{
		ArrayList<TEC> TecMap = new ArrayList<TEC>();
		BufferedReader bufferReader = new BufferedReader(new FileReader(URL));
		String Read = bufferReader.readLine();
		
		while(Read != null)
		{
			if(Read.contains(START_TEC))
			{
				Read = bufferReader.readLine();
				//System.out.println(Read);
				double dayOfYear = (ConvertTime.DOY(Double.valueOf(Read.substring(0,6)), 
												    Double.valueOf(Read.substring(6,12)),
												    Double.valueOf(Read.substring(12,18))));
				
				double timeOfDay = (ConvertTime.TOD(Double.valueOf(Read.substring(18,24)),
						                            Double.valueOf(Read.substring(24,30)),
						                            Double.valueOf(Read.substring(30,36))));

				for(double i = 87.5 ; i >= -87.5 ; i-=2.5) {
					Read = bufferReader.readLine();

					Read = bufferReader.readLine();
					for(double j = -180.0 , k = 0; j<= 180.0 ; j+=5.0,k+=5) {
						
						Read = k==80 ? bufferReader.readLine() : Read;
						k = k==80 ? 0 : k;
						TecMap.add(new TEC(i, j, timeOfDay, dayOfYear, Double.valueOf(Read.substring((int)k, (int)k+5))));
						
					}
				}
			}	
			else if(Read.contains(START_RMS))
				break;
			Read = bufferReader.readLine();
		
		}
		return TecMap;
	}

	public ArrayList<RMS> RMS(String URL) throws IOException
	{
		ArrayList<RMS> TecMap = new ArrayList<RMS>();
		BufferedReader bufferReader = new BufferedReader(new FileReader(URL));
		String Read = bufferReader.readLine();
		
		while(Read != null)
		{
			if(Read.contains(START_RMS))
			{
				Read = bufferReader.readLine();
				
				double dayOfYear = (ConvertTime.DOY(Double.valueOf(Read.substring(0,6)), 
												    Double.valueOf(Read.substring(6,12)),
												    Double.valueOf(Read.substring(12,18))));
				
				double timeOfDay = (ConvertTime.TOD(Double.valueOf(Read.substring(18,24)),
						                            Double.valueOf(Read.substring(24,30)),
						                            Double.valueOf(Read.substring(30,36))));

				for(double i = 87.5 ; i >= -87.5 ; i-=2.5) {
					Read = bufferReader.readLine();

					Read = bufferReader.readLine();
					for(double j = -180.0 , k = 0; j<= 180.0 ; j+=5.0,k+=5) {
						
						Read = k==80 ? bufferReader.readLine() : Read;
						k = k==80 ? 0 : k;
						TecMap.add(new RMS(i, j, timeOfDay, dayOfYear, Double.valueOf(Read.substring((int)k, (int)k+5))));
						
					}
				}
			}	
			else if(Read.contains(END_FİLE))
				break;
			Read = bufferReader.readLine();
		
		}
		return TecMap;
		
	}
	public ArrayList<KP_F107> KP_SolarFlux(String URL) throws IOException
	{

		ArrayList<KP_F107> kp_F107 = new ArrayList<KP_F107>();
		BufferedReader bufferReader = new BufferedReader(new FileReader(URL));
		String Read = bufferReader.readLine();
		while(Read != null)
		{
			if(Read.contains("#"))
				Read = bufferReader.readLine();
			else
			{
				double dayOfYear = (ConvertTime.DOY(Double.valueOf(Read.substring(0,4)), 
													Double.valueOf(Read.substring(4,7)),
													Double.valueOf(Read.substring(7,10))));
				double solarFlux = Double.valueOf(Read.substring(147,156));
				
				for(int i = 0 ; i < 8 ; i++)
				{
					kp_F107.add(new KP_F107(dayOfYear, 
									ConvertTime.TOD(i*3,0,0), 
									Double.valueOf(Read.substring(32 + i * 7 , 32 + (i + 1) * 7)), 
									solarFlux));
				}
				Read = bufferReader.readLine();
			}
		}
		return kp_F107;
	}
}
