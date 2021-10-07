import java.io.IOException;

import IONEX.Preprocess.ConvertTime;
import IONEX.Preprocess.IonosphereManager;

public class Program {
public static void main(String[] args) {
	IonosphereManager ionosphereManager = new IonosphereManager();
	
	String FileName = "TEC2000-2008.csv";
	int Year = 2000;
	int Day = 1;
	
	IonosphereManager.File_Name = FileName;
	
		try {
			ionosphereManager.WriteIonosphere(Year, Year+8, Day,365, "Ionosphere/IONEX", 
																	 "Ionosphere/KP_F107");
			
		//	ionosphereManager.WriteKP_F107(Year, Year+20, "/media/gurkan/Personel/DeepLearning/Ionosphere/KP_F107");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
