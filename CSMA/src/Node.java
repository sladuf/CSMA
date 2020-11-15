import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	//println() -> ���߿� �ؽ�Ʈ���Ͽ� �ִ� �Լ��� �� ��ü�Ҳ���
	
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
        
        this.time = (int)(Math.random() * 100); //������ ���� �ð�
		this.node = (int)(Math.random() * 4) + 1; //���� ���
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
		for(int i = 0 ; i <= 5 ; i++) {
			suc = i;
			if(i == 5) {
				success();
				suc = 0;
			}
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
		
	public int get_status() {
		return status;
	}
	
			
			/* Link�� ��û -> True or False
			 * if == True �̸�, Accept text ���� data 5msec ����
			 * SystemClock += 5 msec
			 * if == False �̸�, BackOffTimer �����ϰ� �ٽ� �ݺ�
			System.out.println(name+ ") Data Send Request Accept from Link");
			System.out.println(name + ") Data Send Finished To Node" + node);
			//else if == False
			System.out.println(name + ") Data send Request Reject from Link");
			BackoffTimer backoff = new BackoffTimer();
			try {
	        	BackoffTimer timer = new BackoffTimer();
	        	int back = (int)(Math.random() * 10) + 1;
	            Thread.sleep(timer.backoffTime(back));
	            System.out.println(name + ") Exponential Back-off Time : " + back + " msec");
	        } catch (InterruptedException e) {
	            System.err.println("Interrupted: Interrupt exception");
	        }
	        */
	}