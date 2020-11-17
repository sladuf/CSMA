import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	
	private int name;
	private final int num = 5; //5msec data
	private String fileName;
	public int time;
	public int node;
	int status;
	public int suc = 0;
	FileWriter fw;
	
	//Node ��ü
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
        
        this.time = (int)(Math.random() * 1000*60); //������ ���� �ð�
        //this.time = (int)(Math.random() * 100); //������ ���� �ð� reject test�� 
        this.node = (int)(Math.random() * 4) + 1; //���� ���
        while(this.node == name) {
        	this.node = (int)(Math.random() * 4) + 1; //���� ���
        }
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
		this.time += clock;
	}
	public int trans() {
		/* @param trans() : data ������ �ð� return
		 * @param time : data ������ �ð�(��ü ������ �ڵ� �ʱ�ȭ)
		 */
		return time;
	}
		
	
	public void run() {
		/* @param run() : Link���� Accept�� �ް� �����ϴ� method
		 * 
		 */
		try{
			fw.write(SystemClock.print()+ " Data Send Request Accept from Link\n");
	        fw.flush();
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
		 * Link���� Accept�� ��, Node2 -> Node3�̸� Node3.set_status(1) �� �������
		 * Link���� 
		 */
		this.status = status;
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
	public int get_status() {
		return status;
	}
}
