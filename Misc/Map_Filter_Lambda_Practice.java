import java.util.*;
import java.util.stream.*;

public class Map_Filter_Lambda_Practice {
    public static void main(String[] args) {
        List<Integer> in = Arrays.asList(5,10,15,20,25,30);    
        List<Integer> resultMult = in.stream().map(n->{return n;}).collect(Collectors.toList());resultMult.forEach(n->{
        	System.out.print((n * 2) +", ");
        });
        System.out.println(); 
        
        List<Integer> resultSqua = in.stream().map(n->{return n;}).collect(Collectors.toList());resultSqua.forEach(n->{
        	System.out.print((n * n) +", ")
        ;});
        System.out.println(); 

        List<String> in1 = Arrays.asList("one", "fish", "two", "fish", "red", "fish", "blue", "fish");
        List<String> resultStr = in1.stream().map(n->{return n;}).collect(Collectors.toList());resultStr.forEach(n->{
        	System.out.print(n.substring(0, 1).toUpperCase() + n.substring(1) + ", ");
        });
        System.out.println();
        
        in.stream().filter(n -> n % 2 == 0).forEach(System.out::print);
        System.out.println();
        
        in1.stream().filter(n-> n.contains("fish") == false).forEach(n->System.out.print(n + ", "));
        
        List<String> resultStr1 = in1.stream().map(n->{return n;}).collect(Collectors.toList());resultStr1.forEach(n-> {
        	if(n != "fish") {
        		System.out.print(n + ", ");
        	}
        });
        System.out.println();

        int sum = in.stream().reduce(0, (x, y) -> x + y);
        System.out.println("sum: " + sum);
    }
}
