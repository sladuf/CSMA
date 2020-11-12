import java.io.File;
import java.io.FileWriter;

class Node extends Thread{
	//println() -> 나중에 텍스트파일에 넣는 함수로 다 교체할꺼임
	
	private int name;
	private final int num = 5; //5msec data
	private String fileName;
	
	//Node 객체
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
		int time = (int)(Math.random() * 100); //데이터 보낼 시간
		int node = (int)(Math.random() * 5) + 1; //보낼 노드
		try{
            
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file, true);
            
            fw.write(SystemClock.print() +" " + time);
            fw.flush();
             
        }catch(Exception e){
            e.printStackTrace();
        }
			
			if (SystemClock.get() == time) {
				/* 데이터 요청 -> 보낼 시간과 SystemClock이 같으면 요청한다 */
				try{
		            
		            File file = new File(fileName);
		            FileWriter fw = new FileWriter(file, true);
		            
		            fw.write(SystemClock.print()+" Data Send Request To Node"+node);
		            fw.flush();
		             
		        }catch(Exception e){
		            e.printStackTrace();
		        }
			}
			
			/* Link에 요청 -> True or False
			 * if == True 이면, Accept text 띄우고 data 5msec 전송
			 * SystemClock += 5 msec
			 * if == False 이면, BackOffTimer 실행하고 다시 반복
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