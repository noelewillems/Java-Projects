import java.util.Iterator;

public class HashTableTestor {

	public static void main(String args[]){
		HashTable<String, String> ht = new HashTable<String, String>(5);
		ht.put("aaa", "apple");
		ht.put("bbb", "bacon");
		ht.put("ccc", "cheese");
		
		System.out.println(ht.get("aaa"));
		System.out.println(ht.get("bbb"));
		System.out.println(ht.get("ccc"));
		
		System.out.println("Iterate");
		Iterator<String> it = ht.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("End Iterate");
		ht.put("ddd", "ddd");
		ht.put("eee", "eee");
		ht.put("fff", "fff");

		System.out.println(ht.get("ddd"));
		System.out.println(ht.get("eee"));
		System.out.println(ht.get("fff"));
		
		System.out.println("Iterate");
		it = ht.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("End Iterate");
		
		ht.put("ggg", "ggg");
		ht.put("hhh", "hhh");
		ht.put("iii", "iii");

		System.out.println(ht.get("ggg"));
		System.out.println(ht.get("hhh"));
		System.out.println(ht.get("iii"));
		
		System.out.println("Iterate");
		it = ht.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("End Iterate");
		System.out.println("Make sure we didn't lose anything");
		System.out.println("re-get iii got="+ht.get("iii"));
                System.out.println("re-get aaa got="+ht.get("aaa"));
		HashTable<Double, Double> ht2 = new HashTable<Double, Double>(5);
		double step = 1.1;
		for(double i=step;i<10;i=i+step){
			ht2.put(i, i);
		}
		for(double i=step;i<10;i=i+step){
			System.out.println(ht2.get(i));
		}
		System.out.println("Iterate");
		Iterator<Double>it2 = ht2.iterator();
		while(it2.hasNext()) {
			System.out.println(it2.next());
		}
		System.out.println("End Iterate");
	}
}
