//Brice Alexander
//Crytography HW 5, 3.33 3.35 3.41

import java.math.BigInteger;
import java.util.ArrayList;

public class Crypto_3_33_35_41 {

	// performs quadratic sieve on n, up to prime power b, from F(alpha) to F(beta)
	public static void quadSieve(int n, int b, int alpha, int beta) {
		int[] sieveArray = new int[beta - alpha + 1];
		int[] primePowers = {2,3,4,5,7,9,11,13,16,17,19,23,25,29,31,37};

		// put numbers into sieve array, T^2 - n
		int T = alpha;
		System.out.println("N = " + n + ", B = " + b + ", F("+alpha+") to F("+beta+")");
		System.out.println("Before sieve:");
		for (int x = 0; x < sieveArray.length; x++) {
			sieveArray[x] = (T * T) - n;
			T++;
			System.out.print(sieveArray[x] + " ");
		}

		//loop over each prime power up to b
		for (int y = 0; primePowers[y] <= b; y++) {
			T = alpha;
			int p = primePowers[y];
			// loop over each number in array and take out primes
			for (int x = 0; x < sieveArray.length; x++) {
				//if congruent
				if ((T * T) % p == n % p) {
					//System.out.println("\n" + T + " squared mod " + p + " == " + n
							//+ " mod " + p);
					while (sieveArray[x] % p == 0) {
						sieveArray[x] /= p;
					}//end while
				}//end while
				T++;
			}//end x
		}//end y
		
		//print sieve again
		System.out.println("\nAfter sieve:");
		for (int x = 0; x < sieveArray.length; x++)
			System.out.print(sieveArray[x] + " ");

	}// end quadSieve

	// computes the gcd of a and b, as well as the coefficients u and v, for the
	// equation au + bv = gcd(a, b)
	// the variable u (the inverse of a mod b) is made positive before returning
	public static int[] gcd(int a, int b) {
		// if b is 0 returns a as gcd, u as 1 and v as zero
		if (b == 0)
			return new int[] { a, 1, 0 };

		// recursively calls gcd with b and the remainder of a mod b
		int[] answer = gcd(b, a % b);

		// store from answer array
		int gcd = answer[0];
		int u = answer[2];
		int v = answer[1] - (a / b) * answer[2];

		// make u positive and adjust v down
		while (u < 0) {
			u += b / gcd;
			v -= a / gcd;
		}

		// return 3 answers
		return new int[] { gcd, u, v };
	}// end gcd
	
	//takes in a modulus, 'n', an exponent 'a' and a base 'g'
	//returns g^a(mod n)
	public static int modExpo(int n, int g, int a){

		    long b = 1;
		    long x = g;
		    //while exponent is > zero
		    while(a > 0){
		        if( a % 2 == 1)
		            b = (b * x) % n;
		        //square
		        x = (x * x) % n;
		        a /= 2;
		    }
		    return (int) b;
	
	}//end modExpo
	
	// takes in a (long) number, returns ArrayList of it's prime factors
	public static ArrayList<Long> primeFactors(long n) {
		ArrayList<Long> primesList = new ArrayList<Long>();
		for (int x = 2; x <= n; x++) {
			while (n % x == 0) {
				primesList.add((long) x);
				n /= x;
			}
		}
		return primesList;
	}

	
	//main method
	public static void main(String[] args) {

		//problem 3.33 part a
		//quadSieve(221, 11, 15, 30);
		System.out.println("\n---------------------Problem 3.33----------------------------------\nA)");
		quadSieve(493, 11, 23, 38);
		System.out.print("\n\nF(23) = 36 = 2^2 * 3^2");
		System.out.print("\nF(25) = 132 = 2^2 * 3 * 11");
		System.out.print("\ngcd(493, 23 - 2*3) = " +
					gcd(493, 23 - 6)[0] + ", and 493/17 = 29");
		
		//problem 3.33 part b
		System.out.println("\n\nB)");
		quadSieve(493, 16, 23, 50);
		System.out.print("\n\nF(23) = 36 = 2^2 * 3^2");
		System.out.print("\nF(25) = 132 = 2^2 * 3 * 11");
		System.out.print("\nF(31) = 468 = 2^2 * 3^2 * 13");
		System.out.print("\nF(41) = 1188 = 2^2 * 3^3 * 11");
		System.out.print("\nF(47) = 1716 = 2^2 * 3 * 11 * 13");
		System.out.print("\n\n(25*31*47)^2 = (2^3 * 3^2 * 11 * 13)^2 , but");
		System.out.print("\ngcd(493, 25*31*47 - 2^3 * 3^2 * 11 * 13) = " +
				gcd(493, (25*31*47) - 10296)[0]);
		System.out.print("\ngcd(493, 23*31*41*47 - 2^4 * 3^4 * 11 * 13) = " +
				gcd(1188623, 493)[0]);
		
		//problem 3.35
		System.out.println("\n\n---------------------Problem 3.35----------------------------------");
		System.out.println("A)\n17^3030 mod 19079 = " + modExpo(19079, 17, 3030) +
				" = 2^2 * 3^6 * 5");
		System.out.println("17^6892 mod 19079 = " + modExpo(19079, 17, 6892) +
				" = 2^11 * 3^2");
		System.out.println("17^18312 mod 19079 = " + modExpo(19079, 17, 18312) +
				" = 2^4 * 3 * 5^3");
		System.out.println("\nB)\n2(x2) + 6(x3) + 1(x5) = 3030");
		System.out.println("11(x2)+ 2(x3)         = 6892");
		System.out.println("4(x2) + 1(x3) + 3(x5) = 18312");
		System.out.println("x2, x3, x5 = 17734, 10838, 17002 (mod 19078)");
		System.out.println("\nC)\n19 * 17^-12400 (mod 19079) = 384 = 2^7 * 3");
		System.out.println("\nD)\nlog base 17 of (19) = 12400 + 7*17734 + 1*10838 = 13830");
		
		//problem 3.41 part a
		System.out.println("\n\n---------------------Problem 3.41----------------------------------");
		ArrayList temp = primeFactors(1794677960);
		System.out.println("A)\n1794677960 = "+temp.toString()+ " mod 32411");
		System.out.println("           = (-1)^3 * (1) * (1) = -1, so m = 1");
		temp = primeFactors(525734818);
		System.out.println("\n525734818 = "+temp.toString()+ " mod 32411");
		System.out.println("          = (-1) * (-1)^2 * (-1) * (1) = 1, so m = 0");
		temp = primeFactors(420526487);
		System.out.println("\n420526487 = "+temp.toString()+ " mod 32411");
		System.out.println("          = (-1) * (-1) * (-1) = -1, so m = 1");
		
		//problem 3.41 part b
		temp = primeFactors(3149);
		System.out.println("B)\n3149 = "+temp.toString());
		System.out.println("\n2322|47 = -1, m = 1");
		System.out.println("719|47 = 1, m = 0");
		System.out.println("202|47 = 1, m = 0");
		
		//problem 3.41 part c
		//m = 0, r^2 (mod n) --- m = 1, ar^2 (mod n)
		System.out.print("\nC)\nm = 1, a*r^2 mod n = ");
		BigInteger num = BigInteger.valueOf(705130839);
		num = num.modPow(BigInteger.valueOf(2), BigInteger.valueOf(781044643));
		num = num.multiply(BigInteger.valueOf(568980706));
		num = num.mod(BigInteger.valueOf(781044643));
		System.out.print(num);
		
		System.out.print("\nm = 1, a*r^2 mod n = ");
		num = BigInteger.valueOf(631364468);
		num = num.modPow(BigInteger.valueOf(2), BigInteger.valueOf(781044643));
		num = num.multiply(BigInteger.valueOf(568980706));
		num = num.mod(BigInteger.valueOf(781044643));
		System.out.print(num);
		
		System.out.print("\nm = 0, r^2 mod n = ");
		num = BigInteger.valueOf(67651321);
		num = num.modPow(BigInteger.valueOf(2), BigInteger.valueOf(781044643));
		System.out.print(num);
		
	}//end main
}//end class
