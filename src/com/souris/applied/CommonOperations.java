package com.souris.applied;

import java.util.ArrayList;

public class CommonOperations {
	static boolean printedPrime = false;
	static boolean haveNonPrimeA = false;
	static boolean havePrimeA = false;
	// printExpo only used for printing final encoding of decoded hash during authentication
	static ArrayList<String> printExpo = new ArrayList<String>();
	
	// fast exponentiation method based on notes
	public static int fastExponentiation(String hash, int key, int n) {
		printExpo.clear();
		int m = Integer.parseInt(hash, 2);
		int y = 1;
		printExpo.add("i |xi |y     |y  ");
		String out1 = "";
		String num = new StringBuilder(Integer.toBinaryString(key)).reverse().toString();
		for (int i = num.length() - 1; i >= 0; i--) {
			y = (y * y) % n;
			int z = y;
			if (num.charAt(i) == '1')
				y = (m * y) % n;
			out1 = String.format("%-2d|%-3c|%-5d|%-4d", i, (num.charAt(i)), z, y);
			printExpo.add(out1);
		}
		return y;
	}
	// Hashes a bit string one byte at a time
	public static String hashFunction(String toHash) {
		String part = toHash.substring(0, 8);

		for (int i = 8; i < toHash.length(); i += 8) {
			part = getXOR(part, toHash.substring(i, i + 8));
		}

		for (int i = part.length(); i < 32; i++)
			part = "0" + part;
		return part;
	}
	// XOR operation between two bit strings
	public static String getXOR(String p1, String p2) {
		String result = "";
		for (int i = 0; i < p1.length(); i++) {
			if (p1.charAt(i) == p2.charAt(i)) {
				result += "0";
			} else {
				result += "1";
			}
		}

		return result;
	}
	
	// adds necessary zeroes to make bit representation 32 bits 
	public static String bitString(int n) {
		String num = Integer.toBinaryString(n);
		String result = num;
		for (int i = num.length(); i < 32; i++) {
			result = "0" + result;
		}

		return result;
	}
}
