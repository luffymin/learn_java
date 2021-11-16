package com.example.sort;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SortApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SortApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Entity a = new Entity();
		a.setId("a");
		a.setCreateDate(new Date());

		Thread.sleep(1000);

		Entity b = new Entity();
		b.setId("b");
		b.setCreateDate(new Date());

		Thread.sleep(2000);

		Entity c = new Entity();
		c.setId("c");
		c.setCreateDate(new Date());

		List<Entity> list = new ArrayList<>();
		list.add(b);
		list.add(c);
		list.add(a);
		List<Entity> sortedList = list.stream().sorted(Comparator.comparing(Entity::getCreateDate)).collect(Collectors.toList());

		System.out.println(sortedList);
	}
}
