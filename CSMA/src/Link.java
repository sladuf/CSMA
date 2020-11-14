import java.util.Random;
import java.lang.Thread;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link implements Runnable{
	
	FileWriter linkfile;
	static public boolean idle = true;
	static public boolean status = false;
	
	private int now_nodenum; /*@보내려는 node 이름 받음*/
	
	Node node[] = new Node[5];
	
	
	public Link() {
		
		new SystemClock();
		
		String time = SystemClock.print();
		for(int i = 0; i<5; i++)
		{
			node[i] = new Node(i+1,time);
			/*@ node[4]는 now_connecting으로 사용*/
		}

		
		try { 
			File link = new File("C:\\Test\\Link.txt");
			linkfile = new FileWriter("C\\Test\\Link.txt");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		
		
		for(int i = 0 ; i <= 1000*60 ; i++) {
			SystemClock.set();
	
			if(SystemClock.get() == 0) {
				try {
					linkfile.write(SystemClock.print()+" Link start\n");
					linkfile.write(SystemClock.print()+" System Clock Start\n");
				}catch(IOException e) {
						e.printStackTrace();
				}
			}
			for(int j = 0; j<5; j++) {
				if(this.node[j].trans() == SystemClock.get()) { /* node가 보낼 시간 == System Clock => 전송요청 메세지
					/*@ String sendReq : Link.txt 입력용 String*/
					String sendReq = SystemClock.print() + "Node"+ j + 
							"Data Send Request To Node" + node[j].node+ "\n";
					
					try {
						linkfile.write(sendReq);
					} catch (IOException e) {
						e.printStackTrace(); 
					}
				}
				now_nodenum = j;
			}
			
			try {
				if(idle == true) { // Link: idle
					//Accept 출력
					String accept = SystemClock.print() + "Accept: Node"+ now_nodenum+ 
							"Data Send Request To Node"+node[now_nodenum].node +"\n";
					//끝났을 때 출력
					String finish = SystemClock.print() + "Node"+ now_nodenum+ 
							"Data Send Finished To Node"+node[now_nodenum].node+"\n";
					/*file에 쓰고*/
					try {
						linkfile.write(accept);
						
					} catch (IOException e) {
						e.printStackTrace(); 
					}
					try {
						
						node[node[now_nodenum].node].success(); //성공했다고 파일에 쓰기
						node[now_nodenum].start(); //accept하고 실행해줌
						idle = false;
						node[node[now_nodenum].node].set_status(1); // 받는 노드의 status 1로 변경
						
						linkfile.write(finish);
						set_idle();
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					
					
					}
				else {
					String reject = SystemClock.print() + "Reject: Node" +now_nodenum +
							"Data Send Request To Node" + node[now_nodenum].node+ "\n";
							
					try {
							linkfile.write(reject);
							
						} catch (IOException e) {
							e.printStackTrace(); 
						}
					
					if(node[node[now_nodenum].node].get_status() == 1 ) {// 받는 중
						//스레드 끝난 뒤 보내기
						
					}
					else {//보내는 중
						
						//BackoffTimer
					}
				}
		
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
}
	}
	
	class CSMACD {
		public void main(String args[]) {
			
			Link link = new Link();
		}
	}
}