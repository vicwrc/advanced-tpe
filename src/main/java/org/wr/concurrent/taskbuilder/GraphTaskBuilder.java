package org.wr.concurrent.taskbuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.wr.concurrent.Reducer;
import org.wr.concurrent.TaskBuilder;
import org.wr.concurrent.TaskQueue;
import org.wr.concurrent.TaskWithDependencies;

import com.google.common.base.Preconditions;

public class GraphTaskBuilder  implements TaskBuilder {

    private Map<String, TaskWithDependencies> tasks = new HashMap<>();

    private GraphTaskBuilder() {}

    public static GraphTaskBuilder create() {
        return new GraphTaskBuilder();
    }

    public Map<String, TaskWithDependencies> getTasks() {
        return Collections.unmodifiableMap(tasks);
    }

    public GraphTaskBuilder add(String name, Function func) {
        tasks.put(name, TaskWithDependencies.create(name, func));
        return this;
    }

    public GraphTaskBuilder remove(String name) {
        // check dependencies here
        tasks.remove(name);
        return this;
    }

    public GraphTaskBuilder addDependency(String parent, String child) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);
        TaskWithDependencies parentTask = tasks.get(parent);
        Preconditions.checkNotNull(parentTask);

        childTask.getToWait().add(parent);

        return this;
    }

    public GraphTaskBuilder reduceIn(String child, Reducer reducer) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);

        childTask.setReducer(reducer);

        return this;
    }

    public GraphTaskBuilder removeDependency(String parent, String child) {
        TaskWithDependencies childTask = tasks.get(child);
        Preconditions.checkNotNull(childTask);

        childTask.getToWait().remove(parent);

        return this;
    }

    @Override
    public TaskQueue build() {
        verifyTasks();
        return new TaskQueue(tasks);
    }

    private void verifyTasks() {
        // todo : add verification logic here
    }
}
