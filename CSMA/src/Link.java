import java.lang.Thread;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link implements Runnable{
	
	FileWriter linkfile;
	static public boolean idle = true;
	//static public boolean status = false;
	
	private int now_nodenum; /*@�������� node �̸� ����*/
	
	Node node[] = new Node[5];
	
	
	public Link() {
		
		new SystemClock();
		
		String time = SystemClock.print();
		for(int i = 0; i<5; i++)
		{
			node[i] = new Node(i+1,time);
			/*@ node[4]�� ���� �������ִ� ��� ��������� ���*/
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
	
			if(SystemClock.get() == 0) {//00:00:000�� ������
				try {
					linkfile.write(SystemClock.print()+" Link start\n");
					linkfile.write(SystemClock.print()+" System Clock Start\n");
				}catch(IOException e) {
						e.printStackTrace();
				}
			}
			else if(SystemClock.print().equals("01:00:000"))//01:00:000�� ������
			{
				try {
					linkfile.write(SystemClock.print()+" System Clock Finished\n");
					linkfile.write(SystemClock.print()+" Link Finished\n");
				}catch(IOException e) {
						e.printStackTrace();
				}
			}
			for(int j = 0; j<5; j++) {
				if(this.node[j].trans() == SystemClock.get()) { /* node�� ���� �ð� == System Clock => ���ۿ�û �޼���
					/*@ String sendReq : Link.txt �Է¿� String*/
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
					//Accept ���
					String accept = SystemClock.print() + "Accept: Node"+ now_nodenum+ 
							"Data Send Request To Node"+node[now_nodenum].node +"\n";
					//������ �� ���
					String finish = SystemClock.print() + "Node"+ now_nodenum+ 
							"Data Send Finished To Node"+node[now_nodenum].node+"\n";
					/*file�� ����*/
					try {
						linkfile.write(accept);
						
					} catch (IOException e) {
						e.printStackTrace(); 
					}
					try {
						
						node[now_nodenum].start(); //accept�ϰ� ��������
						idle = false;
						node[node[now_nodenum].node].set_status(1); // �޴� ����� status 1�� ����
						
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
					
					if(node[node[now_nodenum].node].get_status() == 1 ) {// �޴� ��
						//������ ���� �� ������
						//������ ��� ����
						
					}
					else {//������ ��
						
						int back = (int)(Math.random() * 10) + 1;
						BackoffTimer timer = new BackoffTimer();
						Thread.sleep(timer.backoffTime(back));
						//Thread sleep ��� trans�� �ٲٴ½����� �ؾ��ϳ�?
						//BackoffTimer
					}
				}
		
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	class CSMACD {
		public void main(String args[]) {
			
			Link link = new Link();
		}
	}
}