public class distanceCalc {
		public double getDistance (double lat2, double long2, double lat1, double long1) {//latlong2 = new, 1 = previous
			int R = 6371;
			double latDistance = Math.toRadians(lat2-lat1);
	        double longDistance = Math.toRadians(long2-long1);
	        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
	                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * 
	                   Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	        double distance = R * c;
			return distance;//in km
		}
	}
