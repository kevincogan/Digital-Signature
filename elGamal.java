import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.SecureRandom;

import java.math.BigInteger;

interface Assignment2Interface {
	/* Method generateY returns the public key y and is generated from the given generator, secretKey  and modulus */
	BigInteger generateY(BigInteger generator, BigInteger secretKey, BigInteger modulus);

  // /* Method generateR generates the first part of the ElGamal signature from the given generator, random value k and modulus */
	BigInteger generateR(BigInteger generator, BigInteger k, BigInteger modulus);

  /* Method generateS generates the second part of the ElGamal signature from the given plaintext, secretKey, first signature part r, random value k and modulus */
  BigInteger generateS(byte[] plaintext, BigInteger secretKey, BigInteger r, BigInteger k, BigInteger modulus) throws Exception;


  /* Method calculateGCD returns the GCD of the given val1 and val2 */
  BigInteger[] calculateGCD(BigInteger val1, BigInteger val2);

  /* Method calculateInverse returns the modular inverse of the given val using the given modulus */

  BigInteger calculateInverse(BigInteger val, BigInteger modulus);

}

class DigitalSignarure implements Assignment2Interface {
	public BigInteger generateY(BigInteger generator, BigInteger secretKey, BigInteger modulus) {

		BigInteger pubicKey = generator.modPow(secretKey, modulus);

		return pubicKey;
	}

	public BigInteger generateR(BigInteger generator, BigInteger k, BigInteger modulus) {

		BigInteger genR = generator.modPow(k, modulus); //.............................................................Review order...

		return genR;
	}

	public BigInteger generateS(byte[] plaintext, BigInteger secretKey, BigInteger r, BigInteger k, BigInteger modulus) throws Exception{
		//Hashing the concatenated key 200 times.
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    for (int i = 0; i < 200; i++) {
      plaintext = digest.digest(plaintext);
		}

		BigInteger newPlaintext = new BigInteger(plaintext); // .........................problem..

		BigInteger hashingSection = modulus.subtract(secretKey.multiply(r));

		BigInteger primeMinusOne = modulus.subtract(BigInteger.ONE);

		BigInteger[] val = calculateGCD(k, primeMinusOne); //.................................investigate...

		BigInteger multInverse = calculateInverse(val[1], primeMinusOne);

		BigInteger combineSection = hashingSection.multiply(multInverse);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		return combineSection.mod(primeMinusOne);
	}

	public BigInteger[] calculateGCD(BigInteger val1, BigInteger val2){
		if (val2 == BigInteger.ZERO) {
      return new BigInteger[] {val1, BigInteger.ONE, BigInteger.ZERO};
    }
		else {
      BigInteger[] arr = calculateGCD(val2, val1.mod(val2));

      return new BigInteger[] {arr[0], arr[1]};
    }

	}

	public BigInteger calculateInverse(BigInteger val, BigInteger modulus){

		return val.mod(modulus);
	}
}


public class elGamal {
  public static void main(String[] args) throws Exception {
		DigitalSignarure digitalSignature = new DigitalSignarure();

    String primeModulus = "b59dd79568817b4b9f6789822d22594f376e6a9abc0241846de426e5dd8f6eddef00b465f38f509b2b18351064704fe75f012fa346c5e2c442d7c99eac79b2bc8a202c98327b96816cb8042698ed3734643c4c05164e739cb72fba24f6156b6f47a7300ef778c378ea301e1141a6b25d48f1924268c62ee8dd3134745cdf7323";
    String generator = "44ec9d52c8f9189e49cd7c70253c2eb3154dd4f08467a64a0267c9defe4119f2e373388cfa350a4e66e432d638ccdc58eb703e31d4c84e50398f9f91677e88641a2d2f6157e2f4ec538088dcf5940b053c622e53bab0b4e84b1465f5738f549664bd7430961d3e5a2e7bceb62418db747386a58ff267a9939833beefb7a6fd68";

		//This converts the primeModulus and generator to a BigInteger.
    BigInteger primeModulusBigInt = new BigInteger(primeModulus, 16);
    BigInteger generatorBigInt = new BigInteger(generator, 16);


    // Create private keys.
    BigInteger secretKey = genRandomPrime(primeModulusBigInt);


    // Create public.
    BigInteger publicKey = digitalSignature.generateY(generatorBigInt, secretKey, primeModulusBigInt);


		// generateR :
		BigInteger s = BigInteger.ZERO;

		while (s == BigInteger.ZERO){

			//Calculate K.
	    BigInteger primeMinusOne = primeModulusBigInt.subtract(BigInteger.ONE);
	    BigInteger k = generateK(primeMinusOne);
			System.out.println("k = " + k.toString(16) + "\n");

			BigInteger r = digitalSignature.generateR(generatorBigInt, k, primeModulusBigInt);
			System.out.println("r = " + r.toString(16) + "\n");

	    // Convert to file contents to a byte array.
	    String fileName = args[0];
	    byte[] plaintext = Files.readAllBytes(new File("./" + fileName).toPath());

//////////////////////////////////////////////////////////////////////////////////////////////////
			s = digitalSignature.generateS(plaintext, secretKey, r, k, primeModulusBigInt);
	    System.out.println("s = " + s.toString(16) + "\n");
		}
  }

	public static BigInteger genRandomPrime(BigInteger primeModulusBigInt) {
		SecureRandom random = new SecureRandom();
		// by setting 'int certainty' to 1 ensures that the number will always be a prime number ( https://stackoverflow.com/questions/53243130/why-does-java-biginteger-say-probably-prime-and-not-certainly-prime ).
		BigInteger randomPrime = new BigInteger(primeModulusBigInt.bitLength(), 1, random);

		return randomPrime;
	}

	public static BigInteger generateK(BigInteger primeMinusOne) {
		BigInteger valueK = genRandomPrime(primeMinusOne);
		BigInteger resultGCD = simpleGCD(valueK, primeMinusOne);

		if (valueK.compareTo(primeMinusOne) == 0 && valueK.compareTo(primeMinusOne) == -1 && resultGCD.equals(BigInteger.ONE)) {
			return generateK(primeMinusOne);
		}
		return valueK;
	}

	// https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/the-euclidean-algorithm
  // used this for building my GCD algorithm
  public static BigInteger simpleGCD(BigInteger a, BigInteger b) { //........................................................Good...
		BigInteger r = BigInteger.ZERO;
    BigInteger largeNum = a;
    BigInteger smallNum = b;

    if (a.compareTo(b) == -1){
      largeNum = b;
      smallNum = a;
    }

    while ((largeNum.mod(smallNum)) == BigInteger.ZERO) {
      r = largeNum.mod(smallNum);
      largeNum = smallNum;
      smallNum = r;
    }
    return r;
  }
}
