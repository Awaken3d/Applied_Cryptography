package com.souris.applied;

public class TrustedEntity extends RSAEntity {
	// cert is the whole certificate along with trent's signature
	// hashCert is just the name along with n and e, hashed
	// r is name,n,e unhashed
	String cert = "", hashCert = "", r ="";
	int s = 0;
	
	public TrustedEntity() {
		super(false);
	}

	public String generateCertificate(String name, int n, int publicKey) {
		String certificate = "";
		certificate = addName(name);
		certificate = addN(certificate, n);
		certificate = addE(certificate, publicKey);
		r = certificate;
		hashCert = CommonOperations.hashFunction(certificate);
		s = CommonOperations.fastExponentiation(hashCert, d, n);
		this.cert = certificate + Integer.toBinaryString(s);
		printInfo();
		return certificate;

	}

	public void printInfo() {
		System.out.println("\nline:243");
		System.out.println("r = " + r);
		System.out.println("h(r) = " + hashCert);
		System.out.println("s = " + CommonOperations.bitString(s));
		System.out.println("\nline:246");
		System.out.println("h(r) = " + Integer.parseInt(hashCert, 2) + " ,s = " + s);
		
	}
	// Convert name to binary and add to certificate
	public String addName(String name) {
		StringBuilder result = new StringBuilder();
		int index = 0;
		for (int i = 0; i < 6; i++) {
			String alpha = Integer.toBinaryString((int) name.charAt(index));
			if(alpha.length() < 8) {
				for(int j = 0;j<8 - alpha.length();j++) result.append("0");
			}
			index++;
			result.append(alpha);
			if (index >= name.length()) {
				for(int j = 0; j < 6 - name.length(); j++) {
					char space = ' ';
					String add = "00"+ Integer.toBinaryString((int) space);
					result.insert(0, add);
				}
				break;
			}
		}

		return result.toString();
	}
	
	// convert n to bits and add to certificate
	public String addN(String certificate, int n) {
		String ni = CommonOperations.bitString(n);
		certificate += ni;
		return certificate;
	}
	
	// add e to bits and add to certificate
	public String addE(String certificate, int e) {
		String epsilon = Integer.toBinaryString(e);
		for(int i =epsilon.length() ;i<32;i++) {
			epsilon = "0" + epsilon;
		}
		certificate += epsilon;
		return certificate;
	}

}
