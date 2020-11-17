import java.lang.Thread;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link extends Thread{
	
	FileWriter linkfile;
	public static boolean idle = true;
	//static public boolean status = false;
	
	private int now_nodenum = 0; /*@보내려는 node 이름 받음*/
	
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
		while(SystemClock.get() < 1000*60) {
			SystemClock.set();
			//try {linkfile.write(SystemClock.print()+"\n");}catch(Exception e) {e.printStackTrace();}
			/*데이터를 보내는 중이면 0이 아닌 node의 숫자가 now_nodenum임
			 * data()는 데이터 전송 시간을 1초씩 늘려줌 (5초가 되면 0으로 바뀌기 때문에 0과 비교)
			 */
			if(now_nodenum != 0) {
				node[now_nodenum].data();
				if(node[now_nodenum].suc == 0) {
					String finish = SystemClock.print() + " Node"+ now_nodenum+ 
							" Data Send Finished To Node"+node[now_nodenum].node+"\n";
					//start가 끝나면
					try {
						linkfile.write(finish);
						linkfile.flush();
						node[node[now_nodenum].node].set_status(0);
						node[node[now_nodenum].node].finish_receive(now_nodenum);
						node[now_nodenum].interrupt(); //Thread 동작 종료
					}catch(Exception e) {
						e.printStackTrace();
						}
					idle = true;
					now_nodenum = 0;
				}
			}
			if(SystemClock.get() >= 1000*60)//01:00:000 이상일 때 보내기
			{
				try {
					linkfile.write(SystemClock.print()+" System Clock Finished\n");
					linkfile.write(SystemClock.print()+" Link Finished\n");
					linkfile.flush();
				}catch(IOException e) {
						e.printStackTrace();
				}
			}
			for(int j = 1; j<5; j++) {
				if(this.node[j].trans() == SystemClock.get()) { /* node가 보낼 시간 == System Clock => 전송요청 메세지
					/*@ String sendReq : Link.txt 입력용 String*/
					String sendReq = SystemClock.print() + " Node"+ j + 
							" Data Send Request To Node" + node[j].node+ "\n";
					try {
					node[j].request();
					
						linkfile.write(sendReq);
						linkfile.flush();
						
					} catch (IOException e) {
						e.printStackTrace(); 
					}if(idle == true){// Link: idle
					 
						try { 
							//Accept 출력
							String accept = SystemClock.print() + " Accept: Node"+ j+ 
									" Data Send Request To Node"+node[j].node +"\n";
							//끝났을 때 출력
							/*file에 쓰고*/
							
								
								linkfile.write(accept);
								idle = false;
								node[j].start(); //accept하고 실행해줌
								Thread.sleep(5);
								now_nodenum = j;
								
								node[node[j].node].set_status(1); // 받는 노드의 status 1로 변경
								node[node[j].node].start_receive(now_nodenum);
								
								//break;
								
								}catch(Exception e) {
								e.printStackTrace();
								}
						}
							
						else {
							String reject = SystemClock.print() + " Reject: Node" +j +
									" Data Send Request To Node" + node[j].node+ "\n";
									
							try {
									linkfile.write(reject);
									
								} catch (IOException e) {
									e.printStackTrace(); 
								}
							
							if(node[j].get_status() == 1 ) {// 받는 중
								/* node가 data를 전송중인 숫자를 suc으로 설정
								 * suc == 0 이면 데이터 전송 받지 않는 중
								 */
								int temp = node[j].suc; //temp는 추가 해 줄 time
								node[j].time += temp;
								break;
							}
							else {//보내는 중
								
								int back = (int)(Math.random() * 10) + 1;
								int backofftime = timer.backoffTime(back);
								if(backofftime == 0) {
									backofftime = 1;
								}
								node[j].backoff(backofftime);
								//break;
								}
							}
						}//catch(Exception e) {
							//e.printStackTrace();
						}
				}
			}
		//}
	}
//}