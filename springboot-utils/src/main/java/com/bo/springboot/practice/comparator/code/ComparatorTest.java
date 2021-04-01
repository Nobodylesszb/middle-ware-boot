package com.bo.springboot.practice.comparator.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @auther: bo
 * @Date: 2021/4/1 22:31
 * @version: v1
 * @description: ComparatorTest
 */
public class ComparatorTest {

    public static void main(String[] args) {

        Employee e1 = new Employee("John", 25, 3000, 9922001);
        Employee e2 = new Employee("Ace", 22, 2000, 5924001);
        Employee e3 = new Employee("Keith", 35, 4000, 3924401);

        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        //上下等价
        employees.sort((n1, n2) -> n1.getAge() - n2.getAge());
        employees.sort(Comparator.comparingInt(Employee::getAge));
        employees.forEach(System.out::println);
        employees.sort(Comparator.comparing(Employee::getName));

        /**
         * Comparator.comparing 方法的使用
         *
         * comparing 方法接收一个 Function 函数式接口 ，通过一个 lambda 表达式传入
         *
         */
        employees.sort(Comparator.comparing(e -> e.getName()));

        /**
         * 该方法引用 Employee::getName 可以代替 lambda表达式
         * 和comparing 方法一不同的是 该方法多了一个参数 keyComparator ，keyComparator 是创建一个自定义的比较器。
         */
        employees.sort(Comparator.comparing(Employee::getName));

        employees.sort(Comparator.comparing(
                Employee::getName, (s1, s2) -> {
                    return s2.compareTo(s1);
                }));

        employees.sort(Comparator.comparing(
                Employee::getName, Comparator.reverseOrder()));

        employees.add(null);  //插入一个null元素
        employees.sort(Comparator.nullsFirst(Comparator.comparing(Employee::getName)));
        employees.forEach(System.out::println);


        employees.sort(Comparator.nullsLast(Comparator.comparing(Employee::getName)));
        employees.forEach(System.out::println);


//        使用 Comparator.thenComparing 排序
//        首先使用 name 排序，紧接着在使用ege 排序，来看下使用效果

        employees.sort(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName));
        employees.forEach(System.out::println);
    }
}
