import java.util.Random;
import java.lang.Thread;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;


//interface ChannelConstants{ int FREE = 0;  //Indicates Channel is free
//int INUSE = 1; //Indicates Channel is being used
//}

 
class Link implements Runnable{
	
	FileWriter linkfile;
	private Vector<Node> nodes;
	static public boolean idle = true;
	static public boolean status = false;
	
	
	//static int ChannelStatus;
	
	public Link() {
		
		//nodes = new Vector <Node>(4);
		new SystemClock();
		
		Node node[] = new Node[5];
		
		String time = SystemClock.print();
		for(int i = 0;i<5;i++)
		{
			node[i] = new Node(i+1,time);
		}
		node[0] = new Node(1, time);
		node[1] = new Node(2, time);
		node[2] = new Node(3, time);
		node[3] = new Node(4, time);
		node[4] = new Node(5, time);
		
		try { 
			File link = new File("C:\\Test\\Link.txt");
			linkfile = new FileWriter("C\\Test\\Link.txt");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		
		String sendRequest = SystemClock.print() + "Node"+ node[send].name +
				"Data Send Request To Node" + node[receive].name;
		for(int i = 0 ; i <= 1000*60 ; i++) {
			SystemClock.set();
	
			if(SystemClock.get() == 0) {
				linkfile.write(SystemClock.print()+" Link start\n");
				linkfile.write(SYstemClock.print()+" System Clock Start");
				}
			if(node[send]_signal == true) { //node's send signal
				String sendRequest = SystemClock.print() + "Node"+ node[send].name + "Data Send Request To Node" + node[receive].name;
				linkfile.write(sendRequest);
			}
			try {
				if(idle == true) {
					
					idle = false;
				}
				else {
					if(status == true ) {//¹Þ´Â Áß
						
					}
					else {
						//BackoffTimer timer = new BackoffTimer();
						//Thread.sleep();
					}
				}
		
			}
			catch(InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}
	
	class CSMACD {
		public void main(String args[]) {
			/*
			Node node1 = new Node("Node1");
			Node node2 = new Node("Node2");
			Node node3 = new Node("Node3");
			Node node4 = new Node("Node4");
			*/
			
			//new SystemClock;
			//String time = SystemClock.print();
			Link link = new Link();
		}
	}
}