package org.dexton.otpfetcher;

import java.util.Scanner;

import org.apache.commons.codec.binary.Base32;
import org.dexton.otpfetcher.TokenCalculator.HashAlgorithm;

public class OTPFetcher {

	public static void main(String[] args) {
		
		String secretString = null;
		int period = 30;
		int digits = 6;
		HashAlgorithm alg = HashAlgorithm.SHA1;
		
		try (Scanner scanner = new Scanner(System.in)) {
			while (secretString == null) {
				System.out.println("Enter a secret (can be base32):");
				secretString = scanner.nextLine();
			}

			System.out.println("Enter a period (none for default of 30):");

			String line = scanner.nextLine();
			
			if (!line.isEmpty()) {
				try {
					period = Integer.parseInt(line);
				} catch (NumberFormatException e) {
					// do nothing
				}
			}
			
			System.out.println("Enter the digit count (none for default of 6):");

			line = scanner.nextLine();
			
			if (!line.isEmpty()) {
				try {
					period = Integer.parseInt(line);
				} catch (NumberFormatException e) {
					// do nothing
				}
			}
			
			System.out.println("Enter the algorithm (none for default of SHA1):");

			switch (scanner.nextLine()) {
			case "SHA1":
				break;
			case "SHA256":
				alg = HashAlgorithm.SHA256;
				break;
			case "SHA512":
				alg = HashAlgorithm.SHA512;
				break;
			}
		}
		
		byte[] secretBytes = new Base32().decode(secretString.toUpperCase());
		
		String token = TokenCalculator.TOTP_RFC6238(secretBytes, period, digits, alg);
		
		System.out.println(token);
	}
	
}
