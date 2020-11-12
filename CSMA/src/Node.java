import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	//println() -> ���߿� �ؽ�Ʈ���Ͽ� �ִ� �Լ��� �� ��ü�Ҳ���
	
	private int name;
	private final int num = 5; //5msec data
	private String fileName;
	
	//Node ��ü
	Node(int name, String clock){
		this.name = name;
		this.fileName = "C://Test//Node"+name+".txt";
         
        try{
            
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file, false);
             
            fw.write(clock+" Node"+name+" Start\n");
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void run() {
		int time = (int)(Math.random() * 100); //������ ���� �ð�
		int node = (int)(Math.random() * 5) + 1; //���� ���
		try{
            
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file, true);
            
            fw.write(SystemClock.print() +" " + time);
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
			
			if (SystemClock.get() == time) {
				/* ������ ��û -> ���� �ð��� SystemClock�� ������ ��û�Ѵ� */
				try{
		            
		            File file = new File(fileName);
		            FileWriter fw = new FileWriter(file, true);
		            
		            fw.write(SystemClock.print()+" Data Send Request To Node"+node);
		            fw.flush();
		             
		        }catch(Exception e){
		            e.printStackTrace();
		        }
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
	}
}