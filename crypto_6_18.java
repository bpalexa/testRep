//Brice Alexander
//Cryptography problem 6.18
//GGH cryptosystem, lattice, Babai

import java.text.DecimalFormat;
import java.util.Arrays;

public class crypto_6_18 {
	
	//computes hadamard ratio for the 2x2 matrix v, returns double
	public static double hadamard(double[][] v, double det){
		double ratio = 0;
		double denom = 0;
		double v1, v2;
		
		v1 = v[0][0]*v[0][0] + v[0][1]*v[0][1];
		v1 = Math.sqrt(v1);
		v2 = v[1][0]*v[1][0] + v[1][1]*v[1][1];
		v2 = Math.sqrt(v2);
		
		denom = v1 * v2;
		
		if (denom != 0)
			ratio = det / denom;
		if (ratio < 0)
			ratio *= -1;
		
		ratio = Math.pow(ratio, .5);
		
		return ratio;
	}//end hadamard

	public static void main(String[] args) {
		
		//Problem 6.18
		
		//TESTING
		/* double[][] v = {{137, 312}, {215, -187}};
		double[][] vInverse = {{187.0/92699.0, 312.0/92699.0}, {215.0/92699.0, -137.0/92699.0}};
		double[] e = {53172, 81743};
		double[][] w = {{1975, 438}, {7548, 1627}};
		double[][] wInverse = {{-1627.0/92699.0, 438.0/92699.0}, {7548.0/92699.0, -1975.0/92699.0}};
		*/
		
		double[][] v = {{4, 13}, {-57, -45}};
		double[][] w = {{25453, 9091}, {-16096, -5749}};
        double[]   e = {155340, 55483};
        double[][] vInverse = {{-15.0/187.0, -13.0/561.0}, {19.0/187.0, 4.0/561.0}};
        double[][] wInverse = {{5749.0/561.0, 9091.0/561.0}, {-16096.0/561.0, -25453.0/561.0}};
        
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(6);
		
		//////////////////////////Part a
		double det = Math.abs( v[0][0]*v[1][1] - v[1][0]*v[0][1] );
		double pubHadamard = hadamard(w, det);
		double prvHadamard = hadamard(v, det);
		
		System.out.println("A)\nDeterminant of lattice = " + det);
		System.out.println("\nPrivate Hadamard ratio = " + df.format( prvHadamard ));
		System.out.println("\nPublic  Hadamard ratio = " + df.format( pubHadamard ));
		
		///////////////////////////Part b
		double[] latticeVect = new double[2];
		double[] message = new double[2];
		double[] coeff = new double[2];
		long[] roundedCoeff = new long[2];
		double[] r = new double[2];
		
		coeff[0] = e[0] * vInverse[0][0] + e[1] * vInverse[1][0];
		coeff[1] = e[0] * vInverse[0][1] + e[1] * vInverse[1][1];
		
		System.out.println("\nB) USING PRIVATE KEY"
				+ "\nCoefficients = [" + df.format( coeff[0] ) + ", " + df.format( coeff[1] ) + "]");
		roundedCoeff[0] = Math.round(coeff[0]);
		roundedCoeff[1] = Math.round(coeff[1]);
		System.out.println("Rounded Coefficients = " + Arrays.toString(roundedCoeff));
		
		latticeVect[0] = (roundedCoeff[0] * v[0][0] + roundedCoeff[1] * v[1][0]);
		latticeVect[1] = (roundedCoeff[0] * v[0][1] + roundedCoeff[1] * v[1][1]);
		System.out.println("Lattice Vector = " + Arrays.toString(latticeVect));
		
		message[0] = latticeVect[0] * wInverse[0][0] + latticeVect[1] * wInverse[1][0];
		message[1] = latticeVect[0] * wInverse[0][1] + latticeVect[1] * wInverse[1][1];
		System.out.println("\nMessage = " + Arrays.toString(message));
		
		r[0] = e[0] - latticeVect[0];
		r[1] = e[1] - latticeVect[1];
		System.out.println("\nPerturbation R = " + Arrays.toString(r));
		
		/////////////////////////Part C
		coeff[0] = e[0] * wInverse[0][0] + e[1] * wInverse[1][0];
		coeff[1] = e[0] * wInverse[0][1] + e[1] * wInverse[1][1];
		
		System.out.println("\nC) USING PUBLIC KEY"
				+ "\nCoefficients = [" + df.format( coeff[0] ) + ", " + df.format( coeff[1] ) + "]");
		roundedCoeff[0] = Math.round(coeff[0]);
		roundedCoeff[1] = Math.round(coeff[1]);
		System.out.println("Rounded Coefficients = " + Arrays.toString(roundedCoeff));
		
		latticeVect[0] = (roundedCoeff[0] * w[0][0] + roundedCoeff[1] * w[1][0]);
		latticeVect[1] = (roundedCoeff[0] * w[0][1] + roundedCoeff[1] * w[1][1]);
		System.out.println("Lattice Vector = " + Arrays.toString(latticeVect));
		
		message[0] = latticeVect[0] * wInverse[0][0] + latticeVect[1] * wInverse[1][0];
		message[1] = latticeVect[0] * wInverse[0][1] + latticeVect[1] * wInverse[1][1];
		System.out.println("\nMessage = " + Arrays.toString(message));
		
	}//end main
}//end crypto_6_18
