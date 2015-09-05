import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Kqq {
	private class node{
		char val;
		int id;
		ArrayList<node> adjacent;
		node(int iddata,char info){
			id = iddata;
			val = info;
			adjacent = new ArrayList<node>();
		}
	}
	node nodes[];
	int size;
	public void createGraph(int m,int n,String[] list ){
		nodes  = new node[m*n];
		int k=0;
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				nodes[k] = new node(i*n + j, list[i].charAt(j)); 
				k++;
			}
		}
		size = k;
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				int id = i*n + j;	
				if(i==0){
					if(j==0){
						//top left corner
						nodes[id].adjacent.add(nodes[n+1]);
						nodes[id].adjacent.add(nodes[1]);
						nodes[id].adjacent.add(nodes[n]);
					}
					else if(j==n-1){
						//top right corner
						nodes[id].adjacent.add(nodes[n-2]);
						nodes[id].adjacent.add(nodes[2*n-2]);
						nodes[id].adjacent.add(nodes[2*n-1]);
					}
					else{
						//top row
						nodes[id].adjacent.add(nodes[id+1]);
						nodes[id].adjacent.add(nodes[id-1]);
						nodes[id].adjacent.add(nodes[n+id]);
						nodes[id].adjacent.add(nodes[n+id-1]);
						nodes[id].adjacent.add(nodes[n+id+1]);
					}
				}
				else if(i==m-1){
					if(j==0){
						//bottom left corner
						nodes[id].adjacent.add(nodes[id+1]);
						nodes[id].adjacent.add(nodes[id-n]);
						nodes[id].adjacent.add(nodes[id-n+1]);
					}
					else if(j==n-1){
						//bottom right corner
						nodes[id].adjacent.add(nodes[id-1]);
						nodes[id].adjacent.add(nodes[id-n-1]);
						nodes[id].adjacent.add(nodes[id-n]);
					}
					else{
						//bottom row
						nodes[id].adjacent.add(nodes[id-1]);
						nodes[id].adjacent.add(nodes[id+1]);
						nodes[id].adjacent.add(nodes[id-n]);
						nodes[id].adjacent.add(nodes[id-1-n]);
						nodes[id].adjacent.add(nodes[id-n+1]);
					}
				}
				else if(j==0){
					//left column
					nodes[id].adjacent.add(nodes[id+1]);
					nodes[id].adjacent.add(nodes[n+id]);
					nodes[id].adjacent.add(nodes[id-n]);
					nodes[id].adjacent.add(nodes[n+id+1]);
					nodes[id].adjacent.add(nodes[id-n+1]);
				}

				else if(j==n-1){
					//right column
					nodes[id].adjacent.add(nodes[id-1]);
					nodes[id].adjacent.add(nodes[id-1-n]);
					nodes[id].adjacent.add(nodes[id-n]);
					nodes[id].adjacent.add(nodes[id+n]);
					nodes[id].adjacent.add(nodes[id-1+n]);
				}
				else{
					//mid board
					nodes[id].adjacent.add(nodes[id+1]);
					nodes[id].adjacent.add(nodes[id-1]);
					nodes[id].adjacent.add(nodes[id+n]);
					nodes[id].adjacent.add(nodes[id-n]);
					nodes[id].adjacent.add(nodes[id+1+n]);
					nodes[id].adjacent.add(nodes[id+1-n]);
					nodes[id].adjacent.add(nodes[id-1-n]);
					nodes[id].adjacent.add(nodes[id-1+n]);
				}
			}
		}
	}
	private class queue{
		node array[]; 
		int front;
		int rear;
		int size;
		public queue(int m,int n){
			array = new node[m*n+1] ;
			front = -1;
			rear = -1;
			size = 0;
		}
		public void enque(node ele){
			size+=1;
			if(rear==-1){
				rear = 0;
				front = 0;
				array[0] = new node(ele.id,ele.val);
			}
			else{
				rear+=1;
				array[rear] = new node(ele.id,ele.val);	
			}		
		}
		public node dequeue(){
			if(rear==-1){
				System.out.println("Queue underflow");
				return null;
			}
			else{
				node ret = array[front];
				front+=1;
				size-=1;
				return ret;
			}
		}
		public boolean isEmpty(){
			if(size==0){
				return true;
			}
			return false;
		}
	}
	public void fillGraph(int x,int y,int m,int n){
		HashMap<node, Integer> visited = new HashMap<node, Integer>();
		queue q = new queue(m, n);
		int ctr=1;
		int id = x*n +y;
		nodes[id].val='0';
		q.enque(nodes[id]);
		while(true){
			if(visited.containsKey(nodes[id])){
				
			}
			else{
				boolean flag = false;
				for(node s:nodes[id].adjacent){
					if(s.val=='*'){
						continue;
					}
					else{
						if(s.val==' '){
							s.val = (char)(ctr+'0');
							flag = true;
							q.enque(s);
						}	
					}		
				}	
				visited.put(nodes[id], nodes[id].id);
				if(flag){
					ctr+=1;
					flag = false;
				}
			}
			if(q.size>=1){
				id = q.dequeue().id;
			}
			else{
				break;
			}		
		}
	}
	public void tracePath(int fx,int fy,int m,int n){
		int id = fx*n + fy;
		char ch = nodes[id].val;
		if(ch==' '){
			System.out.println("Can not reach destination");
			return;
		}
		int num = (int)(ch-'0');
		nodes[id].val = '#';
		do{
			for(node s:nodes[id].adjacent){
				if(s.val==(char)(num-1+'0')){
					id = s.id;
					num = num-1;
					s.val = '#';
					break;
				}
			}
		}while(num!=0);
	}
	public void print(int m,int n){
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				int id = i*n +j;
				System.out.print(nodes[id].val);
			}
			System.out.println();
		}
	}
	public void printPath(int m,int n){
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				int id = i*n +j;
				if(nodes[id].val=='#'||nodes[id].val=='*'){
					System.out.print(nodes[id].val);
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int m = s.nextInt();
		s.nextLine();
		int n = s.nextInt();
		s.nextLine();
		Kqq object = new Kqq();
		String input[]  = new String[m];
		for(int i=0;i<m;i++){
			input[i] = s.nextLine();
		}
		object.createGraph(m,n,input);
		System.out.println("Enter x and y for start");
		int x = s.nextInt();
		int y = s.nextInt();
		object.fillGraph(x, y, m, n);
		object.print(m, n);
		System.out.println("Enter x and y for destination");
		int fx = s.nextInt();
		int fy = s.nextInt();
		object.tracePath(fx, fy, m, n);
		object.printPath(m, n);
	}
}
