package org.wr.concurrent;

import org.wr.concurrent.taskbuilder.ChainTaskBuilder;
import org.wr.concurrent.taskbuilder.GraphTaskBuilder;
import org.wr.concurrent.taskbuilder.TreeTaskBuilder;

public interface TaskBuilder {

    TaskQueue build();

    static GraphTaskBuilder graph() {
        return GraphTaskBuilder.create();
    }

    static TreeTaskBuilder tree() {
        return new TreeTaskBuilder();
    }

    static ChainTaskBuilder chain() {
        return new ChainTaskBuilder();
    }

}
