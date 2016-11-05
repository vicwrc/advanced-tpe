package org.wr.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.base.Preconditions;

public class TaskBuilder {

    private Map<String, TaskWithDependencies> tasks = new HashMap<>();

    private TaskBuilder() {}

    public static TaskBuilder create() {
        return new TaskBuilder();
    }

    public Map<String, TaskWithDependencies> getTasks() {
        return Collections.unmodifiableMap(tasks);
    }

    public TaskBuilder add(String name, Function func) {
        tasks.put(name, TaskWithDependencies.create(name, func));
        return this;
    }

    public TaskBuilder remove(String name) {
        // check dependencies here
        tasks.remove(name);
        return this;
    }

    public TaskBuilder addDependency(String parent, String child) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);
        TaskWithDependencies parentTask = tasks.get(parent);
        Preconditions.checkNotNull(parentTask);

        childTask.getToWait().add(parent);

        return this;
    }

    public TaskBuilder reduceIn(String child, Reducer reducer) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);

        childTask.setReducer(reducer);

        return this;
    }

    public TaskBuilder removeDependency(String parent, String child) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);

        childTask.getToWait().remove(parent);

        return this;
    }

    public List<TaskWithDependencies> build() {
        verifyTasks();
        return new LinkedList<>(tasks.values());
    }

    private void verifyTasks() {
        // todo : add verification logic here
    }
}
