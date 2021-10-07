import java.io.IOException;

import IONEX.Preprocess.IonosphereManager;

public class createData {
	public static void main(String[] args) {
	
		IonosphereManager ionosphereManager = new IonosphereManager();
		
		String FileName = args[0];
		int Year = Integer.valueOf(args[1]);
		int Day = Integer.valueOf(args[2]);
		
		IonosphereManager.File_Name = FileName;
		
			try {
				ionosphereManager.WriteNormalizedIonosphere(Year, Year, Day,Day, "Ionosphere/IONEX", 
																			     "Ionosphere/KP_F107");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
}
