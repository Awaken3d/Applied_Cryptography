package com.souris.applied;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSAEntity {
	List<Integer> quotient;
	int p = 0, q = 0, n = 0, e = 0, d = 0;
	String hash = "", u = "";
	boolean simpleEntity = true, printedLineNumber = false;

	public RSAEntity(boolean entity) {
		simpleEntity = entity;
		RSAGenerator rsa = new RSAGenerator(simpleEntity);
		// generate two primes
		p = rsa.getPrime();
		q = rsa.getPrime();
		// make sure the primes received are not the same
		while (p == q) {
			q = rsa.getPrime();
		}
		n = p * q;
		findPublicKey();
	}

	// find e that is relatively prime
	public void findPublicKey() {
		int f = (p - 1) * (q - 1);
		int num = 3;
		int[] result = new int[3];
		while (num < n) {
			result = callEuclid(f, num);
			if (result[0] == 1)
				break;
			num++;
		}
		e = num;
		d = result[2];
		if (simpleEntity) {
			System.out.println("\nline:205");
			System.out.println("d = " + d);
		}
		printNumbers();

	}

	public void printNumbers() {
		if (simpleEntity) {
			System.out.println("\nline:209");
		} else {
			System.out.println("\nline:218");
		}
		System.out.println("p = " + p + ", q = " + q + ", n = " + n + ", e = " + e + ", d = " + d);
		System.out.println("p = " + CommonOperations.bitString(p));
		System.out.println("q = " + CommonOperations.bitString(q));
		System.out.println("n = " + CommonOperations.bitString(n));
		System.out.println("e = " + CommonOperations.bitString(e));
		System.out.println("d = " + CommonOperations.bitString(d));

	}

	public int[] callEuclid(int a, int b) {
		int[] ans = isRelativelyPrime(b, a);
		if (ans[2] < 0)
			ans[2] = a + ans[2];
		return ans;

	}

	Random r = new Random();

	public int createU(String certificate) {
		String n = extractN(certificate);
		int k = 0;
		for (int i = 0; i < n.length(); i++) {
			if (n.charAt(i) == '1') {
				k = i;
				break;
			}
		}
		k = n.length() - k;
		u = "1";

		for (int i = 0; i < (k - 2); i++) {

			int randomNum = r.nextInt(10000);
			int digit = randomNum & 1;
			if (digit == 1) {
				u += "1";
			} else {
				u += "0";
			}

		}
		for (int i = u.length(); i < 32; i++) {
			u = "0" + u;
		}
		this.hash = CommonOperations.hashFunction(u);
		System.out.println("\nline:271");
		System.out.println("k = " + k + ", u = " + Integer.parseInt(u, 2));
		System.out.println("\nline:274");
		System.out.println("u = " + u);
		int v = CommonOperations.fastExponentiation(this.hash, d, Integer.parseInt(n, 2));
		return v;
	}

	// Extract n from certificate
	public String extractN(String certificate) {
		String n = "";
		for (int i = 48; i < 80; i++) {
			n += certificate.charAt(i);
		}
		return n;

	}

	// Extended euclidean to test for relative primality
	// arraylists used to keep track of s and t
	public int[] isRelativelyPrime(int num, int modulo) {
		String out1 = "";
		if (simpleEntity) {
			System.out.println();
			if (!printedLineNumber) {
				System.out.println("line:192");
				printedLineNumber = true;
			}
			System.out.println("e = " + num);
			System.out.println("i |qi   |r    |ri+1 |ri+2   |si   |ti ");
		}
		ArrayList<Integer> s = new ArrayList<Integer>();
		ArrayList<Integer> t = new ArrayList<Integer>();
		s.add(1);
		s.add(0);
		t.add(0);
		t.add(1);
		int big = modulo;
		int small = num;
		this.quotient = new ArrayList<Integer>();
		int counter = 0;
		int r = 1;
		int si = 0, ti = 0;
		while (r > 0) {
			this.quotient.add((int) (big / small));
			r = big % small;
			switch (counter) {
			case 0:
				si = s.get(0);
				ti = t.get(0);
				break;
			case 1:
				si = s.get(1);
				ti = t.get(1);
				break;

			}

			if (counter > 1) {
				si = s.get(s.size() - 2) - (quotient.get(quotient.size() - 3) * s.get(s.size() - 1));
				ti = t.get(t.size() - 2) - (quotient.get(quotient.size() - 3) * t.get(t.size() - 1));
				s.add(si);
				t.add(ti);
			}
			counter++;
			out1 = String.format("%-2d|%-5d|%-5d|%-5d|%-7d|%-5d|%-5d", counter, quotient.get(quotient.size() - 1), big,
					small, r, si, ti);
			if (simpleEntity) {
				System.out.println(out1);
			}
			big = small;
			small = r;
		}
		counter++;
		if (counter <= 2) {
			si = s.get(s.size() - 1);
			ti = t.get(t.size() - 1);
		} else {
			si = s.get(s.size() - 2) - (quotient.get(quotient.size() - 2) * s.get(s.size() - 1));
			ti = t.get(t.size() - 2) - (quotient.get(quotient.size() - 2) * t.get(t.size() - 1));
			s.add(si);
			t.add(ti);
		}
		out1 = String.format("%-2d|     |%-5d|     |       |%-5d|%-5d", counter, big, si, ti);
		if (simpleEntity) {
			System.out.println(out1);
		}
		int[] result = new int[3];
		result[0] = big;
		result[1] = s.get(s.size() - 1);
		result[2] = t.get(t.size() - 1);
		return result;
	}

}
