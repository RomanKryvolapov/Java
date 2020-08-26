
<h1><center>RSA/AES Encryption With Self-Destruct</center></h1>
<h4><center>Open in Google Play: <a href="https://play.google.com/store/apps/details?id=com.romankryvolapov.aescryptowithself_destruct" target="_blank">https://play.google.com/store/apps/details?id=com.romankryvolapov.aescryptowithself_destruct</a></center></h4>

<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/AESCryptoWithSelfDestruct%20%20-%202020%20-%20Android/Screenshot_1.png" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/AESCryptoWithSelfDestruct%20%20-%202020%20-%20Android/Screenshot_2.png" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/AESCryptoWithSelfDestruct%20%20-%202020%20-%20Android/Screenshot_3.png" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/AESCryptoWithSelfDestruct%20%20-%202020%20-%20Android/Screenshot_4.png" width="23%" />


<h4>This program is designed to encrypt and decrypt messages using RSA and AES algorithms.</h4>

<p>With RSA, a message can be encrypted with a public key, but it can only be decrypted with a private key. This means that you can send your public key to another person, he can encrypt a message with them, but it can only be decrypted using your private key. In this case, one public key corresponds to one private key, and a public-private key pair is generated simultaneously.</p>

<p>When using the AES algorithm, only a private key is generated; it serves both for encryption and decryption. There is a limitation on the maximum message length for the RSA algorithm, so if you want to encrypt a long message, you can either increase the RSA key size, or generate an AES key, encrypt the message with this key, and then encrypt the AES key itself using the recipients public RSA key, when In this case, only the recipient will be able to decrypt the AES encrypted key with the help of his private key and, with the help of it, decrypt the message. I did not include other algorithms supported by Android in the program, as they are outdated.</p>

<p>You can add keys that you want to use to the program database. These keys will be encrypted as well, but I do not guarantee that if someone breaks into your phone that that person cannot decrypt them. For this purpose, the program has 3 buttons at the top, when you press them, the program key database will be erased and the application will close.</p>

<p>When you open the program, it will ask you to enter a password. Be careful, because if you enter the password incorrectly once, the program will erase all data and remember the entered password as a new one. When you click on the field with the key, a window will open in which it will be fully visible. When you click on the field of the original message, a window will open in which you can edit it. Key generation and encryption and decryption operations can be time consuming.</p>

<p>If you have any questions, you can write to me at [roman.kryvolapov@gmail.com](mailto:roman.kryvolapov@gmail.com)</p>

<br>

<h4>Эта программа предназначена для зашифровки и расшифровки сообщений с использованием алгоритмов RSA и AES.</h4>

<p>При использовании алгоритма RSA сообщение может быть зашифровано публичным ключем, но расшифровать его можно только с помощью приватного ключа. Это означает, что вы можете отправить другому человеку свой публичный ключ, он может им зашифровать сообщение, но расшифровать его можно только с помошью вашего приватного ключа. При этом одному публичному ключу соответствует один приватный ключ, пара пубюличный-приватный ключ генерируется одновременно.</p>

<p>При использовании алгоритма AES генерируется только приватный ключ, он служит как для зашифровки, так и для расшифровки. Для алгоритма RSA существует ограничение по максимальной длинне сообщения, поэтому если вы хотите зашифровать длинное сообщение, вы можете либо увеличить размер RSA ключа, либо сгенерировать AES ключ, зашифровать этим ключом сообщение, а затем зашифровать сам AES ключ с помощью публичного ключа RSA получателя, при этом только получатель с помощью своего приватного ключа сможет расшифровать зашифрованный вами AES ключ и уже с помошью него расшифровать сообщение. Я не стал включать в программу другие алгоритмы, поддерживаемые Android, так как они устарели.</p>

<p>Вы можете добавлять ключи, которые хотите использовать в базу программы. Эти ключи будут также зашифрованы, но я не гарантирую, что если кто либо взломает ваш телефон, что этот человек не сможет их расшифровать. Для этой цели в программе есть 3 кнопки сверху, при нажатии них база данных ключей программы сотрется и приложение закроется.</p>

<p>При открытии программы, она попросит вас ввести пароль Будьте внимательны, так как в случае, если вы введете пароль один раз не правильно, программа сотрет все данные и запомнит введенный пароль как новый При нажатии на поле с ключем откроется окно, в котором он будет виден полностью. При нажатии на поле исходного сообщения, откроется окно, в котором вы можете его отредактировать. Операции генерации ключей и зашифровки и расшифровки могут отнемать какое то время.</p>

<p>Если у вас есть какие либо вопросы, вы можете написать мне на почту [roman.kryvolapov@gmail.com](mailto:roman.kryvolapov@gmail.com)</p>
