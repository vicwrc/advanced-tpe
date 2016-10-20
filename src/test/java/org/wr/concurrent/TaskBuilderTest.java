package org.wr.concurrent;

import static org.junit.Assert.*;

import java.util.List;
import java.util.function.Function;

import org.junit.Test;

public class TaskBuilderTest {

    public static Function<String, Integer> func1 = String::length;
    public static Function<String, Integer> func2 = s -> s.indexOf("qwe");
    public static Function<String, Integer> func3 = s -> s.indexOf("asd");

    public static Function<Integer, String> func4 = Object::toString;


    @Test
    public void constructTasks() throws Exception {
        List<TaskWithDependencies> tasks = TaskBuilder
                .create()
                .add("func1", func1)
                .add("func2", func2)
                .add("func3", func3)
                .add("func4", func4)
                .addDependency("func1", "func4")
                .addDependency("func2", "func4")
                .addDependency("func3", "func4")
                .build();

        System.out.println(tasks);
    }
}