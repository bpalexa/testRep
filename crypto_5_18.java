//Brice Alexander
//Crypto HW7, lenstra's elliptic curve factorization

import java.math.BigInteger;

public class crypto_5_18 {
	
	//factorization using lenstra's elliptic curve method
	//E: y^2 = x^3 + Ax + B, point = (x, y), mod N
	public static long lenstraEcmFactor(long N, int x, int y, int A, int B){
		final int jMax = 100000;
		long factor = 0;
		int[] qPoint = {x, y};  //first time adds itself
		
		//loop multiples of p
		for (int j = 2; j < jMax; j++){
			//qPoint = jP
			qPoint = ecAdd(x, y, qPoint[0], qPoint[1], A, N);
			
			factor = qPoint[0] - x;
			if (factor < 0)
				factor += N; //add mod to make it positive
			factor = gcd(factor, N)[0];
			if (factor > 1)	return factor;
			//System.out.println("factor:" + factor);

		}//end j loop
		
		return factor;
	}
	
	//elliptic curve addition of points
	public static int[] ecAdd(int x_one, int y_one, int x_two, int y_two, int a, long n){
		
		BigInteger lambduh = new BigInteger("0");
		BigInteger xOne = BigInteger.valueOf(x_one);
		BigInteger yOne = BigInteger.valueOf(y_one);
		BigInteger xTwo = BigInteger.valueOf(x_two);
		BigInteger yTwo = BigInteger.valueOf(y_two);
		BigInteger xThree = new BigInteger("0");
		BigInteger yThree = new BigInteger("0");
		BigInteger A = BigInteger.valueOf(a);
		BigInteger N = BigInteger.valueOf(n);
		
		int[] point = new int[2];
		
		//inverses
		if (x_one == x_two && y_one == y_two * -1){
			point[0] = 0; point[1] = 0;
			return point;
		}
		//if same point
		if (x_one == x_two && y_one == y_two){
			lambduh = xOne.multiply(xOne).multiply(BigInteger.valueOf(3)).add(A);
			long denom = gcd(2*y_one, n)[1];  //inverse of 2*y
			
			//System.out.println(Arrays.toString(gcd(denom,n)));
			
			lambduh = lambduh.multiply(BigInteger.valueOf(denom)).mod(N);
		}
		//if different points
		if (x_one != x_two || y_one != y_two){
			//(yTwo - yOne) / (xTwo - xOne)
			long denom = x_two - x_one;
			if (denom < 0)
				denom += n; //add mod to make it positive
			
			//System.out.println(Arrays.toString(gcd(denom,n)));
			
			denom = gcd( denom, n )[1];
			
			lambduh = yTwo.subtract(yOne);
			lambduh = lambduh.multiply(BigInteger.valueOf(denom)).mod(N);
		}
		
		//using lambda to calc point 
		xThree = lambduh.multiply(lambduh).subtract(xOne).subtract(xTwo).mod(N);
		yThree = xOne.subtract(xThree).multiply(lambduh).subtract(yOne).mod(N);
		point[0] = xThree.intValue();
		point[1] = yThree.intValue();
		
//System.out.println("lamb = " + lambduh.toString());
//System.out.println("point = " + point[0] + " " + point[1] + "\n");
		
		return point;
	}
	
	//computes the gcd of a and b, as well as the coefficients u and v, for the equation
	// au + bv = gcd(a, b)
	//per part (e) the variable u is made positive before returning
	public static long[] gcd(long a, long b) {
		//if b is 0 returns a as gcd, u as 1 and v as zero
		if (b == 0)
			return new long[] { a, 1, 0 };
		
		//recursively calls  gcd with b and the remainder of a mod b
		long[] answer = gcd(b, a % b);
		
		//store from answer array
		long gcd = answer[0];
		long u = answer[2];
		long v = answer[1] - (a / b) * answer[2];
		
		//make u positive and adjust v down
		while (u < 0){
			u += b/gcd;
			v -= a/gcd;
		}
		
		//return 3 answers
		return new long[] { gcd, u, v };
	}//end gcd
	
	
	public static void main(String[] args) {
		
		//Problem 5.18
		long[] N = {589, 26167, 1386493, 28102844557L};
		int[][] P = { {2, 5}, {2, 12}, {1, 1}, {7, 4} };
		int[] A = {4, 4, 3, 18};
		int[] B = {9, 128, -3, -453};
		
		for (int x = 0; x < N.length; x++){
			long answer = lenstraEcmFactor(N[x], P[x][0], P[x][1], A[x], B[x]);
			System.out.println( N[x] + " factors to " + answer + " and " + N[x] / answer + "\n");
		}
		
	}//end main
}//end crypto5_18
