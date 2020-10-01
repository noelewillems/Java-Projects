/*
 * This is designed to test your priority queue heap 
 * before you write the rest of your box packet
 */
public class PriQueTestor {

	public static void main(String[] args) {
		PriQue<String> myQue;
		myQue= new ParallelArrayHeap<String>(1000);
		
		// (weight, data)
		myQue.insert(15, "15");
		myQue.insert(10, "10");
		myQue.insert(5, "5");
		myQue.insert(12, "12");
		myQue.insert(10, "10");

		myQue.print();
		System.out.println(myQue.isEmpty());

		while(!myQue.isEmpty()) {
			System.out.println(myQue.remove());
		}
	//	myQue.print();
	}
}