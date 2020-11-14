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
	
	private int now_nodenum;
	
	Node node[] = new Node[5];
	
	
	public Link() {
		
		new SystemClock();
		
		String time = SystemClock.print();
		for(int i = 0; i<5; i++)
		{
			node[i] = new Node(i+1,time);
			/*@ node[4]�� now_connecting���� ���*/
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
			for(int j = 0; j<4; j++) {
				if(this.node[j].time == SystemClock.get()) { /* node�� ���� �ð� == System Clock => ���ۿ�û �޼���
					/*@ String sendReq : Link.txt �Է¿� String*/
					String sendReq = SystemClock.print() + "Node"+ j + 
							"Data Send Request To Node" + node[j].node+ "\n";
					
					try {
						linkfile.write(sendReq);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				node[5] = node[j];
			}
			
			try {
				if(idle == true) { // Link: idle
					
					String Accept = SystemClock.print() + "Accept: "+ node;
					idle = false;
						
					}
					
				}
				else {
					if(status == true ) {//�޴� ��
						
					}
					else {
						
					}
				}
		
			}
			catch(InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}
	public void set_idle() {
		/*@ node���� set idle����?*/
		idle = true;
	}
	
	class CSMACD {
		public void main(String args[]) {
			
			Link link = new Link();
		}
	}
}