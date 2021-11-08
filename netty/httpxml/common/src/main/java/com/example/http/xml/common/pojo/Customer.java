package com.example.http_xml;

import lombok.Data;
import java.util.List;

@Data
public class Customer {
    private long customerNumber;

    private String firstName;
    private String lastName;

    private List<String> middleNames;
}
