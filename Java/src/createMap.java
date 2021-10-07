import java.io.IOException;

import IONEX.Preprocess.IonosphereManager;

public class createMap {
	
	public static void main(String[] args) 
	{
		IonosphereManager ionosphereManager = new IonosphereManager();
		String FileName = args[0];
		float Time = Float.valueOf(args[1]);
		int start = Integer.valueOf(args[2]);
		IonosphereManager.File_Name = FileName;
		
		try {
			ionosphereManager.PlotMap(Time, FileName,"Reals");
			ionosphereManager.PlotMapGif(FileName,"Real",start);
			
			FileName = "Output.csv";
			
			ionosphereManager.PlotMap(Time, FileName,"Output");
			ionosphereManager.PlotMapGif(FileName,"Output",start);
		} catch (IOException e) {
			System.out.println("Dosya bulunmadı");
		}
	}
}
