package com.example.parse_xml_string;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

@SpringBootApplication
public class ParseXmlStringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ParseXmlStringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String xmlString = "<book id=\"bk101\">" +
				"<author>Gambardella, Matthew</author>" +
				"<title>XML Developer's Guide</title>" +
				"<genre>Computer</genre>" +
				"<price>44.95</price>" +
				"<publish_date>2000-10-01</publish_date>" +
				"<description>An in-depth look at creating applications with XML.</description>" +
				"</book>";
		byte[] bytes = xmlString.getBytes();
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document  = builder.parse(stream);

		Element book = document.getDocumentElement();
		System.out.println("book id: " + book.getAttribute("id"));
		if (book.getAttribute("id").equals("bk101")) {
			NodeList nodeList = book.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.println("\t" + nodeList.item(i).getNodeName() + ": " + nodeList.item(i).getTextContent());
			}
		}
	}
}
