package org.wr.concurrent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.wr.concurrent.reducer.StraightForwardReducer;

public class TaskWithDependencies<T, R> {

    private final Function<T, R> func;
    private final String name;
    private Set<TaskWithDependencies> toWait = new HashSet<>();
    private Reducer<T> reducer = new StraightForwardReducer<>();

    public TaskWithDependencies(Function<T, R> func, String name) {
        this.func = func;
        this.name = name;
    }

    public static <K, L> TaskWithDependencies<K, L> create(String name, Function<K, L> func) {
        return new TaskWithDependencies<>(func, name);
    }

    public Set<TaskWithDependencies> getToWait() {
        return toWait;
    }

    public String getName() {
        return name;
    }

    public Function<T, R> getFunc() {
        return func;
    }

    public Reducer getReducer() {
        return reducer;
    }

    public void setReducer(Reducer reducer) {
        this.reducer = reducer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskWithDependencies<?, ?> that = (TaskWithDependencies<?, ?>) o;

        if (!func.equals(that.func)) {
            return false;
        }
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = func.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TaskWithDependencies{" +
                "func=" + func +
                ", name='" + name + '\'' +
                ", toWait=" + toWait +
                '}';
    }
}
