keytool -keystore clientkeystore.jks -genkey -alias client
Enter keystore password: blabla
Re-enter new password: blabla
What is your first and last name?
  [Unknown]:  Pierluigi Vernetto
What is the name of your organizational unit?
  [Unknown]:  Vernetto
What is the name of your organization?
  [Unknown]:  VernettoOrg
What is the name of your City or Locality?
  [Unknown]:  Zurich
What is the name of your State or Province?
  [Unknown]:  ZU
What is the two-letter country code for this unit?
  [Unknown]:  CH
Is CN=Pierluigi Vernetto, OU=Vernetto, O=VernettoOrg, L=Zurich, ST=ZU, C=CH correct?
  [no]:  yes

Enter key password for <client>
        (RETURN if same as keystore password):

		
Download keystore explorer from http://www.keystore-explorer.org/downloads.html

Specify the keystore of certificates using the javax.net.ssl.keyStore system property

java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 MyServer


All JVM security properties https://access.redhat.com/documentation/en-US/Fuse_MQ_Enterprise/7.1/html/Security_Guide/files/SSL-SysProps.html
or better http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefGuide.html#Customization



