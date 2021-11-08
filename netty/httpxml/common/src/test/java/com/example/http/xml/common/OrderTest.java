package com.example.http.xml.common;

import com.example.http.xml.common.pojo.Address;
import com.example.http.xml.common.pojo.Customer;
import com.example.http.xml.common.pojo.Order;
import com.example.http.xml.common.pojo.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TestOrder {

    private static Logger log = LoggerFactory.getLogger(TestOrder.class);

    private String encode2Xml(Order order) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(out);
        encoder.writeObject(order);
        return out.toString();
    }

    private Order decode2Order(String xmlBody) {
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlBody.getBytes());
        XMLDecoder decoder = new XMLDecoder(stream);
        return (Order)decoder.readObject();
    }

    public static void main(String[] args) {
        TestOrder test = new TestOrder();
        Order order = new Order();
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("南京市");
        address.setCountry("中国");
        address.setPostCode("123321");
        address.setState("江苏省");
        address.setStreet1("龙眠大道");
        order.setBillTo(address);
        Customer customer = new Customer();
        customer.setFirstName("李");
        customer.setLastName("林峰");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        order.setShipTo(address);

        String body = test.encode2Xml(order);
        log.info("encode2Xml body: " + body);
        Order orderNew = test.decode2Order(body);
        log.info("decode2Order order: " + orderNew);
    }
}