import java.lang.Thread;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


 
class Link extends Thread{
	
	/*
	 *@param idle true => Link is idle | false => Link is busy
	 *@param now_nodenum now sending data node
	 *@param node no use node[0] -> indexing
	 */
	FileWriter linkfile;
	public static boolean idle = true;
	private int now_nodenum = 0;
	Node node[] = new Node[5];
	BackoffTimer timer = new BackoffTimer();
	
	
	public Link() {
		
		new SystemClock();
		String time = SystemClock.print();
		
		for(int i = 1; i<5; i++)
		{
			node[i] = new Node(i);

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
		/*
		 * SystemClock ~1min works
		 * or Send node existing works
		 */
		while(SystemClock.get() < 1000*60 | now_nodenum != 0) {
			SystemClock.set(); //+1msec
			/*
			 * now_nodenum != 0 -> sending node existing
			 *@param node[].data() sending time +1msec (5msec == 0suc)
			 */
			if(now_nodenum != 0) {
				node[now_nodenum].data();
				if(node[now_nodenum].suc == 0) { //5msec after
					
					String finish = SystemClock.print() + " Node"+ now_nodenum+ 
							" Data Send Finished To Node"+node[now_nodenum].node+"\n";
					try {
						linkfile.write(finish);
						linkfile.flush();
						node[node[now_nodenum].node].set_status(0);
						node[node[now_nodenum].node].finish_receive(now_nodenum);
						node[now_nodenum].new_data();
					}catch(Exception e) {
						e.printStackTrace();
						}
					idle = true;
					now_nodenum = 0;
				}
			}
			
			/*
			 * 4 node all check
			 */
			for(int j = 1; j<5; j++) {

				if(this.node[j].trans() == SystemClock.get()) {
					/*
					 * work regardless of accept or reject
					 */
					String sendReq = SystemClock.print() + " Node"+ j + 
							" Data Send Request To Node" + node[j].node+ "\n";
					try {
						
						node[j].request();
						linkfile.write(sendReq);
						linkfile.flush();
						
					} catch (IOException e) {
						e.printStackTrace();
						}
					
					if(idle == true){ //accept
						try { 
							String accept = SystemClock.print() + " Accept: Node"+ j+ 
									" Data Send Request To Node"+node[j].node +"\n";
								
								linkfile.write(accept);
								idle = false; //link busy
								node[j].accept();
								//Thread.sleep(5);
								now_nodenum = j;
								
								node[node[j].node].set_status(1); // receiving node status 1
								node[node[j].node].start_receive(now_nodenum);
								
								}catch(Exception e) {
								e.printStackTrace();
								}
						}
					else { //reject
						String reject = SystemClock.print() + " Reject: Node" +j +
								" Data Send Request To Node" + node[j].node+ "\n";
								
						try {
								linkfile.write(reject);
								linkfile.flush();
								
							} catch (IOException e) {
								e.printStackTrace();
							}
						
						node[j].reject();
						
						if(node[j].get_status() == 1 ) { //waiting
							/*
							 *@param temp now_sending node's remain time
							 */
							int temp = 5 - node[now_nodenum].suc;
							node[j].time += temp;
							node[j].waiting(now_nodenum, temp);
							//break;
						}
						else { //back-off time
							int back = (int)(Math.random() * 10) + 1;
							int backofftime = timer.backoffTime(back);
							if(backofftime == 0) { //minimum
								backofftime = 1;
								}
							node[j].backoff(backofftime);
						}
					}
				}
			}
		}
		if(SystemClock.get() >= 1000*60)
		{
			try {
				linkfile.write(SystemClock.print()+" System Clock Finished\n");
				linkfile.write(SystemClock.print()+" Link Finished\n");
				linkfile.flush();
			}catch(IOException e) {
					e.printStackTrace();
			}
			for(int i = 1; i<5; i++)
			{
				node[i].end();
			}
		}
	}
}