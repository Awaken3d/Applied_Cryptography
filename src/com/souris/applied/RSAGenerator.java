package com.souris.applied;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSAGenerator {
	int n = 0;
	// notPrime used to check if an example of a non prime number has been
	// printed
	// simpleEntity used to print for Alice and not for Tremt
	// haveNonPrime used to signify whether a non prime output trace has been saved
	boolean notPrime = false, simpleEntity = true, haveNonPrime = false;
	Random r = new Random();
	List<Integer> aNumbers = new ArrayList<Integer>();
	List<String> output = new ArrayList<>();
	// all the primality tests are saved in aOutput and then kept is successOutput if number is primal or failedOutput
	List<String> aOutput = new ArrayList<>();
	List<String> successOutput = new ArrayList<>();
	List<String> failedOutput = new ArrayList<>();

	public RSAGenerator(boolean entity) {
		this.simpleEntity = entity;
	}

	public int getPrime() {
		n = 0;
		getN();
		while (!primeTester()) {
			n = 0;
			getN();
		}
		if (simpleEntity && !CommonOperations.havePrimeA) {
			if (!haveNonPrime && failedOutput.size() == 0) {
				isPrime(78, 73);
			}
			System.out.println("\nline:161");
			for (String i : failedOutput) {
				System.out.println(i);
			}
			System.out.println("\nline:169");
			for (String i : successOutput) {
				System.out.println(i);
			}
		}
		CommonOperations.havePrimeA = true;
		return this.n;
	}

	// Create number randomly
	public void getN() {
		output.clear();
		n ^= (1 << 6);
		n ^= 1;
		int slide = 1;
		for (int i = 0; i < 5; i++) {
			slide <<= 1;
			int randomNum = r.nextInt(Integer.MAX_VALUE);
			int digit = randomNum & 1;
			output.add("b_" + (5 - i) + "|" + CommonOperations.bitString(randomNum) + "|" + digit);

			if (digit == 1) {
				n ^= slide;
			}
		}
		output.add("Number|" + this.n + "|" + CommonOperations.bitString(this.n));
		// checks in order to decide whether results should be printed or not
		if (!CommonOperations.printedPrime && simpleEntity) {
			System.out.println("line:143");
			for (int i = 0; i < output.size(); i++) {
				System.out.println(output.get(i));
			}
			CommonOperations.printedPrime = true;
		}

	}

	// 20 checks for primality, aNumbers used to check for unique a
	public boolean primeTester() {
		int repeat = n < 20 ? n : 20;
		for (int i = 0; i < repeat; i++) {

			int a = r.nextInt() % n;
			a = a < 0 ? (a * (-1)) : a;
			while (aNumbers.contains(a) || a == 0) {
				a = r.nextInt() % n;
				a = a < 0 ? (a * (-1)) : a;
			}
			aNumbers.add(a);
			if (!isPrime(n, a)) {
				haveNonPrime = true;
				return false;
			}
		}
		aNumbers.clear();
		return true;
	}

	// primality tester
	public boolean isPrime(int x, int a) {
		int y = 1;
		int k = x;
		x = x - 1;
		String out1 = "";
		if (this.simpleEntity) {
			aOutput.add("n = " + k + ", a = " + a);
			out1 = "i |xi |z    |y   |y";
			aOutput.add(out1);
		}
		int length = Integer.toBinaryString(x).length();
		for (int i = 0; i < length; i++) {
			int z = y;
			y = (y * y) % k;
			out1 = String.format("%-2d|%-3d|%-5d|%-4d", (length - i - 1), (x >> ((length - 1 - i)) & 1), z, y);

			if ((y == 1) && (z != 1) && (z != x)) {
				out1 = out1 + String.format("|    ");
				aOutput.add(out1);
				for(int j = i + 1; j < length;j++) {
					out1 = String.format("%-2d|%-3d|     |    |    ", (length - j - 1), (x >> ((length - 1 - j)) & 1));
					aOutput.add(out1);

				}
				out1 = k + " is not a prime because " + a + "^" + a + " mod " + k + " = 1 and " + z + " != 1 and " + z + " != " + k + " - 1" ;
				aOutput.add(out1);
				if (!CommonOperations.haveNonPrimeA) {
					failedOutput = new ArrayList<String>(aOutput);
					CommonOperations.haveNonPrimeA = true;
				}
				aOutput.clear();
				haveNonPrime = true;
				return false;
			}
			int checker = (x >> (length - 1 - i));
			if ((checker & 1) == 1) {
				y = (y * a) % k;
			}
			out1 = out1 + String.format("|%-4d", y);

			if (this.simpleEntity) {
				aOutput.add(out1);
				out1 = "";
			}

		}

		if (y != 1) {
			if (this.simpleEntity) {
				out1 = k + " is not a prime because " + a + "^" + a + " mod " + k + " != 1";
				aOutput.add(out1);
				if (!CommonOperations.haveNonPrimeA) {
					failedOutput = new ArrayList<String>(aOutput);
					CommonOperations.haveNonPrimeA = true;
				}
				aOutput.clear();
				haveNonPrime = true;
			}
			return false;
		}
		if (this.simpleEntity) {
			out1 = k + " is perhaps a prime";
			aOutput.add(out1);
			successOutput = new ArrayList<String>(aOutput);
			aOutput.clear();
		}
		return true;

	}
}
