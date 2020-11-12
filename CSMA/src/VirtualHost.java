class VirtualHost{
	
	public static void main(String args[]) {
		
		new SystemClock();
		Node node[] = new Node[5];
		
		String time = SystemClock.print();
		node[0] = new Node(1, time);
		node[1] = new Node(2, time);
		node[2] = new Node(3, time);
		node[3] = new Node(4, time);
		node[4] = new Node(5, time);
		
		node[0].start();
		node[1].start();
		node[2].start();
		node[3].start();
		node[4].start();
		
		for(int i = 0 ; i <= 1000*60 ; i++) {
			SystemClock.set();
			System.out.println(i);
		}
	}
}