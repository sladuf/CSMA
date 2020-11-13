import java.util.Random;
import java.lang.Thread;
import java.util.Vector;
import java.io.FileWriter;

//interface ChannelConstants{ int FREE = 0;  //Indicates Channel is free
//int INUSE = 1; //Indicates Channel is being used
//}


class Link implements Runnable{
	
	FileWriter linkfile;
	private Vector<Node> nodes;
	static public boolean idle = true;
	
	static int ChannelStatus;
	
	public Link() {
		linkfile = new FileWriter("C://Test//Link.txt");
		nodes = new Vector <Node>(4);
		
	}
	
	public 
	public void run() {
		if(systemClock == 0) {
				linkfile.write("00:00:000 Link start\n");
				linkfile.write("00:00: 000 System Clock Start");
				
		}
		while(true) {
			systemClock++;
			Thread.sleep(rand);
			try {
				if(/*idle == true &&*/ChannelStatus == INUSE) {
					
					 BackoffTimer timer = new BackoffTimer();
					Thread.sleep();
					linkfile.write();
				}
				else {
					if(ChannelStatus == FREE ) {
						
					}
				}
		
			}
			catch(InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}
	
	class CSMACD {
		public static void main(String args[]) {
			/*
			Node node1 = new Node("Node1");
			Node node2 = new Node("Node2");
			Node node3 = new Node("Node3");
			Node node4 = new Node("Node4");
			*/
		}
	}
}

