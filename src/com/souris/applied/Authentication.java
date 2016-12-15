package com.souris.applied;

public class Authentication {
	int number = 0, epsilon = 0;
	// extracts n and e and encodes decoded certificate in order to compare it with hashed version on screen
	public void Authenticator(String u, String hash, String v, String certificate) {
		extractN(certificate);
		extractE(certificate);
		int m = CommonOperations.fastExponentiation(v, epsilon, number);
		System.out.println("\nline:281");
		System.out.println("u = " + Integer.parseInt(u, 2) + ", h(u) = " + Integer.parseInt(hash, 2) + ", v = "
				+ Integer.parseInt(v, 2) + ", Ev = " + m);
		System.out.println("\nline:285");
		for (String i : CommonOperations.printExpo) {
			System.out.println(i);
		}
	}
	// Extract n from certificate
	public void extractN(String certificate) {
		String n = "";
		for (int i = 48; i < 80; i++) {
			n += certificate.charAt(i);
		}
		this.number = Integer.parseInt(n, 2);
	}
	// extract e from certificate
	public void extractE(String certificate) {
		String n = "";
		for (int i = 80; i < certificate.length(); i++) {
			n += certificate.charAt(i);
		}
		this.epsilon = Integer.parseInt(n, 2);
	}
}
