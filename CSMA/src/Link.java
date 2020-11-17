import java.lang.Thread;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link extends Thread{
	
	FileWriter linkfile;
	public static boolean idle = true;
	//static public boolean status = false;
	
	private int now_nodenum = 0; /*@�������� node �̸� ����*/
	
	Node node[] = new Node[5];
	BackoffTimer timer = new BackoffTimer();
	
	
	public Link() {
		
		new SystemClock();
		
		String time = SystemClock.print();
		for(int i = 1; i<5; i++)
		{
			node[i] = new Node(i,time);
			/*@ node[0] ���� �������ִ� ��� ��������� ���
			 * index == node ��ȣ ��ġ��
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
			/*�����͸� ������ ���̸� 0�� �ƴ� node�� ���ڰ� now_nodenum��
			 * data()�� ������ ���� �ð��� 1�ʾ� �÷��� (5�ʰ� �Ǹ� 0���� �ٲ�� ������ 0�� ��)
			 */
			if(now_nodenum != 0) {
				node[now_nodenum].data();
				if(node[now_nodenum].suc == 0) {
					String finish = SystemClock.print() + " Node"+ now_nodenum+ 
							" Data Send Finished To Node"+node[now_nodenum].node+"\n";
					//start�� ������
					try {
						linkfile.write(finish);
						linkfile.flush();
						node[node[now_nodenum].node].set_status(0);
						node[node[now_nodenum].node].finish_receive(now_nodenum);
						node[now_nodenum].interrupt(); //Thread ���� ����
					}catch(Exception e) {
						e.printStackTrace();
						}
					idle = true;
					now_nodenum = 0;
				}
			}
			if(SystemClock.get() >= 1000*60)//01:00:000 �̻��� �� ������
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
				if(this.node[j].trans() == SystemClock.get()) { /* node�� ���� �ð� == System Clock => ���ۿ�û �޼���
					/*@ String sendReq : Link.txt �Է¿� String*/
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
							//Accept ���
							String accept = SystemClock.print() + " Accept: Node"+ j+ 
									" Data Send Request To Node"+node[j].node +"\n";
							//������ �� ���
							/*file�� ����*/
							
								
								linkfile.write(accept);
								idle = false;
								node[j].start(); //accept�ϰ� ��������
								Thread.sleep(5);
								now_nodenum = j;
								
								node[node[j].node].set_status(1); // �޴� ����� status 1�� ����
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
							
							if(node[j].get_status() == 1 ) {// �޴� ��
								/* node�� data�� �������� ���ڸ� suc���� ����
								 * suc == 0 �̸� ������ ���� ���� �ʴ� ��
								 */
								int temp = node[j].suc; //temp�� �߰� �� �� time
								node[j].time += temp;
								break;
							}
							else {//������ ��
								
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