# My Java projects
I am learning Java on JetBrains Academy. This is the repository for my projects from this site. It is free course to learn how to code on Java and be Java developer. You can check it here https://hi.hyperskill.org/
## 1. TicTacToe
### Desciption will be later
## 2. Simple Chat Bot
### Desciption will be later
## 3. Coffee Machine
### Desciption will be later
## 4. Encryption-Decryption
### Desciption
The program is designed to do encryption/decyption messages and texts using simple algorithms.

**Shift** algorithm - shifts each letter by the specified number according to its order in the alphabet in circle. It encodes only English letters (from 'a' to 'z' as the first circle and from 'A' to 'Z' as the second circle i.e. after 'z' comes 'a' and after 'Z" comes 'A').

**Unicode** algorithm - shifts each character by the specified number according to the standart Unicode table.
### Usage
The program works with command-line arguments.The program parses 6 arguments: **-mode** (**enc** - encryption, **dec** - decryption), **-key** (integer), **-data** (text or ciphertext within quotes), **-in** (file that contains text), **-out** (file to store the result), **-alg** (**shift** or **unicode**). For example:

`java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode` reads data from the file "road_to_treasure.txt", encrypts it with unicode algorithm with key = 5 and writes the result into the file "protected.txt".

`java Main -key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec` decrypts the given data with shift algorithm with key = 5 and writes the result to the standart output.

All the arguments are guaranteed to be passed to the program. If for some reason it turns out to be wrong:
1. If there is no **-mode**, the program works in enc mode.
2. If there is no **-key**, the program considers that key = 0.
3. If there is no **-data**, and there is no -in the program assumes that the data is an empty string.
4. If there is no **-out** argument, the program prints data to the standard output.
5. If there are both **-data** and **-in** arguments, program prefers -data over -in.
6. If there is no **-alg**, the program considers shift algorithm
## 5. Numerical System Converter
### Desciption will be later
