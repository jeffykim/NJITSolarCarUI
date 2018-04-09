import java.util.Random;
public class database_test {

	public static void main(String[] args) {
		//String location  = "E:\\Databases\\test.db";
		//String location = "";
		
		Database d1 = new Database("C:\\Users\\gdiwa\\Desktop");
		
		Random generator = new Random();
		int lapNumber = 1;
		double SoC = 100; double milesRemaining = 100;
		double milesTravled = 0 ;
		
		while(lapNumber <= 100) {
			d1.addDataByLap(lapNumber++,generator.nextDouble()*15+30, generator.nextDouble()*5+90, SoC= SoC - generator.nextDouble()*4, milesRemaining=milesRemaining-generator.nextDouble()*3);
			d1.addDataByTime(25.2, SoC, generator.nextDouble()*15+7, generator.nextDouble()*3, generator.nextDouble()*3, generator.nextDouble()*1, milesTravled+=100-milesRemaining, milesRemaining, generator.nextDouble()*13+75, (double) (int) generator.nextDouble()*15+80);
		}
		
		//d1.addDataByLap(1000, 2.0, 2.0, 2.0, 2.0);
		//d1.addDataByTime(5, 5, 5, 5, 5, 4, 6, 3, 7, 8);

		d1.closeConnection();
	}

}
