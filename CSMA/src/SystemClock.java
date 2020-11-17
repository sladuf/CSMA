
public class SystemClock {

	static int min;
	static int sec;
	static int msec;
	
	public SystemClock() {
		this.min = 0;
		this.sec = 0;
		this.msec = 0;
	}
	
	public static String print() {
		String s_min = String.format("%02d", min);
		String s_sec = String.format("%02d", sec);
		String s_msec = String.format("%03d", msec);
		return s_min + ":" + s_sec + ":" + s_msec;
	}
	
	public static int get() {
		int temp = msec;
		temp += sec * 1000;
		temp += min * 60000;
		return temp;
	} 
	 
	public static void set() {
		msec += 1;
		if (msec == 1000) {
			msec = msec % 1000;
			sec += 1;
		} 
		if (sec == 60) {
			sec =sec % 60;
			min = 1;
		}
			
	}
}
