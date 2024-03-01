package problems;

import java.lang.Math;
public class Problems {

	public static void main(String[] args) {
		int x = sum();
		System.out.println(mult(5));
		
		
	}
	
	public static int mult(int n) {
		int a1 = 2;
		if (n == 0) {
			return 1;
		}
		return a1 * mult(n - 1);
	}
	
	
	public static int sum2() {
		int i, j, s=0;
		int n = 6;
		 for(i=0; i < n; i++) {
		    int i2 = i*i;
		    for(j=0; j < i; j++) {  
		       s += i2;
		    }
		  }
		return s;
	}
	
	public static int sum() {
		int i, j, s=0;
		int n = 6;
		  for(i=0; i < n; i++) {
		     for(j=0; j < i; j++) { 
		        s += i;
		     }
		  }

		return s;
	}
	
	public static int newSum() {
		int s = 0;
		int n = 6;
		for (int i = 0; i < n; i++) {
			s += i * i;
		}
		return s;

	}
	
	public static int seriesSum() {
		int s = 0;
		int n = 5;
		s = ((n - 1) * (2 * n - 1) * n) / 6;
		return s;
	}

}
