package com.example.http_xml;

import lombok.Data;

@Data
public class Order {
    private long orderNumber;
    private Customer customer;
    private Shipping shipping;
    private Address shipTo;
    private Float total;
}
