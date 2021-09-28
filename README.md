# Java

## Maven

+ compile
  + mvn clean compile test-compile
    + compile the project’s code
+ package
  + mvn clean package
    + create a library package (such as a JAR file)
  + mvn clean package DskipTests
  + mvn clean package -Dmaven.test.skip=true
+ install
  + mvn clean install
    + install the library in the local Maven dependency repository
+ run
  + mvn spring-boot:run
  + java -jar &lt;jar-file&gt;

## 参考文献

+ Maven权威指南
+ Spring Boot实战
