import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	
	/*
	 *@param name node number
	 *@param num 5msec data
	 *@param time random send data time 
	 *@param node random send data node
	 *@param status 0 is unused, 1 is receiving
	 *@param suc now sending data time
	 */
	private int name;
	private final int num = 5;
	private String fileName;
	public int time;
	public int node;
	int status;
	public int suc = 0;
	FileWriter fw;
	
	//Node 객체
	Node(int name){
		this.name = name;
        
        this.time = (int)(Math.random() * 1000);
        this.node = (int)(Math.random() * 4) + 1;
        while(this.node == name) {
        	this.node = (int)(Math.random() * 4) + 1;
        }
	}
	
	public void run() {
		
		this.fileName = "C://Test//Node"+name+".txt";
        
        try{
            
            File file = new File(fileName);
            fw = new FileWriter(file, false);
             
            fw.write(SystemClock.print() +" Node"+name+" Start\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
		
		}
	
	/* 
	 * file write area -> only call / no parameter
	 */
	public void request() {
		try{

            fw.write(SystemClock.print()+ " Data Send Request To Node" + node + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void reject() { //status == 0
		try{
            fw.write(SystemClock.print()+ " Data Send Request Reject from Link\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void waiting(int node, int time) { //status == 1
		try{
            fw.write(SystemClock.print()+ " Now Data Receive from Node"+node+
            		"Waiting Time : "+time + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void accept() {
		try{
			fw.write(SystemClock.print()+ " Data Send Request Accept from Link\n");
	        fw.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            }
	}
	
	public void success() {
		try{

            fw.write(SystemClock.print()+ " Data Send Finished To Node" +node + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void start_receive(int node) {
		try{

            fw.write(SystemClock.print()+ " Data Receive Start from Node" + node + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	public void finish_receive(int node) {
		try{

            fw.write(SystemClock.print()+ " Data Receive Finished from Node" + node + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void end() {
		try{

            fw.write(SystemClock.print()+ " Node" + name + " Finished\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	/*
	 * data get or set area
	 */
	
	public int get_status() {
		return status;
	}
	
	public void set_status(int status) {
		this.status = status;
	}
	
	public int trans() { //get_time method	
		return time;
	}
	
	public void backoff(int clock) { 
		/*
		 *@param clock back-off time
		 */
		try{

            fw.write(SystemClock.print()+ " Exponential Back-off Time : " +clock+ " msec\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
		this.time += clock;
	}
	
	public void data() {
		/*
		 * One call, One +1msec
		 * suc = 0 is data send time 5msec
		 */
		suc+=1;
		if(suc == 5) {
			success();
			suc = 0;
		}
	}
	
	public void new_data(){ //After finish send data
		this.time = (int)(Math.random() * 1000) + SystemClock.get(); //데이터 보낼 시간
		this.node = (int)(Math.random() * 4) + 1; //보낼 노드
	    while(this.node == name) {
	    	this.node = (int)(Math.random() * 4) + 1; //보낼 노드
	    	}
	}
	
	
}
