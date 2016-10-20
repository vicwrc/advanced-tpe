package org.wr.concurrent;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class ExecutorServiceWrapperTest {

    public static Function<String, Integer> func1 = String::length;
    public static Function<String, Integer> func2 = s -> s.indexOf("qwe");
    public static Function<String, Integer> func3 = s -> s.indexOf("asd");

    public static Function<Integer, String> func4 = Object::toString;




    @Test
    public void testExecuteCarcass() throws Exception {
        System.out.println(func2.toString());

    }
}