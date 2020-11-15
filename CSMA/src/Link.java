import java.lang.Thread;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link extends Thread{
	
	FileWriter linkfile;
	public static boolean idle = true;
	//static public boolean status = false;
	
	private int now_nodenum; /*@보내려는 node 이름 받음*/
	
	Node node[] = new Node[5];
	BackoffTimer timer = new BackoffTimer();
	
	
	public Link() {
		
		new SystemClock();
		
		String time = SystemClock.print();
		for(int i = 1; i<5; i++)
		{
			node[i] = new Node(i,time);
			/*@ node[0] 현재 보내고있는 노드 저장용으로 사용
			 * index == node 번호 일치함
			 */
		}

		try { 
			File link = new File("C://Test//Link.txt");
			linkfile = new FileWriter(link, false);
			
			linkfile.write(time+" Link start\n");
			linkfile.write(time+" System Clock Start\n");
			linkfile.flush();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		
		for(int i = 0 ; i <= 1000*60 ; i++) {
			SystemClock.set();
	
			if(SystemClock.print().equals("01:00:000"))//01:00:000에 보내기
			{
				try {
					linkfile.write(SystemClock.print()+" System Clock Finished\n");
					linkfile.write(SystemClock.print()+" Link Finished\n");
				}catch(IOException e) {
						e.printStackTrace();
				}
			}
			for(int j = 1; j<5; j++) {
				if(this.node[j].trans() == SystemClock.get()) { /* node가 보낼 시간 == System Clock => 전송요청 메세지
					/*@ String sendReq : Link.txt 입력용 String*/
					String sendReq = SystemClock.print() + "Node"+ j + 
							"Data Send Request To Node" + node[j].node+ "\n";
					node[j].request();
					
					try {
						linkfile.write(sendReq);
					} catch (IOException e) {
						e.printStackTrace(); 
					}
				}
				now_nodenum = j;
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
							
							node[now_nodenum].start(); //accept하고 실행해줌
							idle = false;
							node[node[now_nodenum].node].set_status(1); // 받는 노드의 status 1로 변경
							
							//start가 끝나면
							linkfile.write(finish);
							idle = true;
							node[node[now_nodenum].node].set_status(0);
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
						
						if(node[now_nodenum].get_status() == 1 ) {// 받는 중
							/* node가 data를 전송중인 숫자를 suc으로 설정
							 * suc == 0 이면 데이터 전송 받지 않는 중
							 */
							int temp = node[now_nodenum].suc; //temp는 추가 해 줄 time
							node[now_nodenum].time += temp;
						}
						else {//보내는 중
							
							int back = (int)(Math.random() * 10) + 1;
							node[now_nodenum].backoff(timer.backoffTime(back));
							}
						}
					}catch(Exception e) {
						e.printStackTrace();
						}
				}
			}	
		}
	}