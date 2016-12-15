package com.souris.applied;



public class Starter {

	public static void main(String[] args) {
		// Create Alice
		RSAEntity Alice = new RSAEntity(true);
		// Create Trent
		TrustedEntity Trent = new TrustedEntity();
		// Ask Trent to generate certificate
		String cert = Trent.generateCertificate("Alice", Alice.n,Alice.e);
		// Create U
		int v = Alice.createU(cert);
		Authentication a = new Authentication();
		// Do bob's job to authenticate 
		a.Authenticator(Alice.u , Alice.hash, CommonOperations.bitString(v), cert);
	}

}
