package com.bo.springboot.optional.code;

public class Person {
    private String name;
    private String gender;
    private int age;
    private Location location;

    public Person() {
    }

    public Person(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person setLocation(String country, String city) {
        this.location = new Location(country, city);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + '}';
    }

    public void greeting(Person person) {
        System.out.println("Hello " + person.getName() + "!");
    }

    public static void showIdentity(Person person) {
        System.out.println("Person: {" + "name='" + person.getName() + '\'' + ", gender='"
                + person.getGender() + '\'' + ", age=" + person.getAge() + '}');
    }
}