import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	//println() -> 나중에 텍스트파일에 넣는 함수로 다 교체할꺼임
	
	private int name;
	private final int num = 5; //5msec data
	private String fileName;
	public int time;
	public int node;
	int status;
	public int suc = 0;
	FileWriter fw;
	
	//Node 객체
	Node(int name, String clock){
		this.name = name;
		this.fileName = "C://Test//Node"+name+".txt";
         
        try{
            
            File file = new File(fileName);
            fw = new FileWriter(file, false);
             
            fw.write(clock+" Node"+name+" Start\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
        
        this.time = (int)(Math.random() * 1000*30); //데이터 보낼 시간
		this.node = (int)(Math.random() * 4) + 1; //보낼 노드
	}
	
	public void request() {
		try{

            fw.write(SystemClock.print()+ " Data Send Request To Node" + node + "\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	public void reject() {
		try{

            fw.write(SystemClock.print()+ " Data Send Request Reject from Link\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	public void backoff(int clock) {
		try{

            fw.write(SystemClock.print()+ " Exponential Back-off Time : " +clock+ " msec\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
		time += clock;
	}
	public int trans() {
		/* @param trans() : data 전송할 시간 return
		 * @param time : data 전송할 시간(객체 생성시 자동 초기화)
		 */
		return time;
	}
		
	
	public void run() {
		/* @param run() : Link에서 Accept를 받고 실행하는 method
		 * 
		 */
		try{
			fw.write(SystemClock.print()+ " Data Send Request Accept from Link\n");
	        fw.flush();
	        data();
	        }catch(Exception e){
	            e.printStackTrace();
	            }
		}
	
	public void data() {
		suc+=1;
		if(suc == 5) {
			success();
			suc = 0;
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
	
	public void set_status(int status) {
		/* 
		 * Link에서 Accept할 때, Node2 -> Node3이면 Node3.set_status(1) 을 해줘야함
		 * Link에서 
		 */
		this.status = status;
	}
		
	public int get_status() {
		return status;
	}
}