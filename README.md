# ElGamal Digital Signature Implementation

## Overview

This repository provides an implementation of the **ElGamal Digital Signature Scheme** using Java. The ElGamal digital signature is a cryptographic algorithm that ensures data authenticity and integrity by generating and verifying digital signatures. The implementation demonstrates the key steps, including key generation, signature creation, and verification.

## Features

1. **Public and Private Key Generation**: Generates secure public and private keys using a large prime modulus and a generator.
2. **Signature Generation**:
   - Computes the components `r` and `s` of the ElGamal signature.
   - Utilizes SHA-256 hashing for secure message hashing.
3. **Helper Methods**:
   - GCD computation for modular arithmetic.
   - Modular inverse calculation.
   - Secure random number generation for cryptographic purposes.
4. **File Hashing**: Reads input data from a file and generates the corresponding signature.

## Prerequisites

- **Java Development Kit (JDK)** 8 or later.
- A text file as input for signature generation.

## How It Works

1. **Key Generation**:
   - A large prime modulus and generator are defined.
   - A private key (`secretKey`) is generated randomly.
   - The public key (`y`) is computed using modular exponentiation.

2. **Signature Generation**:
   - A random value `k` is chosen, ensuring it is coprime with the modulus minus one.
   - The first part of the signature, `r`, is computed using modular exponentiation.
   - The second part, `s`, is calculated using the hash of the message, `secretKey`, `r`, and `k`.

3. **File Input**:
   - The program reads plaintext data from a specified file.
   - The SHA-256 hash of the message is computed iteratively for security.

4. **Output**:
   - The generated signature (`r` and `s`) is displayed in hexadecimal format.

## Installation and Usage

### 1. Clone the Repository
```bash
git clone https://github.com/kevincogan/elgamal-digital-signature.git
cd elgamal-digital-signature
```

### 2. Compile the Code
```bash
javac elGamal.java
```

### 3. Run the Program
To generate a signature for a given file:
```bash
java elGamal <file_name>
```
Replace `<file_name>` with the path to your input file.

### Example
#### Input
A file named `message.txt` containing the text:
```
Hello, this is a test message.
```

#### Output
The program outputs the `k`, `r`, and `s` values in hexadecimal format:
```
k = 4d2...

r = 5a1...

s = 6f4...
```

## Key Methods

### Key Generation
- **`generateY`**: Computes the public key `y` using modular exponentiation.

### Signature Computation
- **`generateR`**: Computes the first component `r` of the signature.
- **`generateS`**: Computes the second component `s` of the signature.

### Helper Methods
- **`calculateGCD`**: Finds the greatest common divisor (GCD) using the Euclidean algorithm.
- **`calculateInverse`**: Computes the modular inverse of a value.
- **`genRandomPrime`**: Generates a random prime number for cryptographic use.

## Contribution

Contributions to this project are welcome! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or fix.
3. Commit your changes and push them to your fork.
4. Open a pull request describing your changes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

---

Dive into the world of cryptography with this ElGamal digital signature implementation. Happy coding!
