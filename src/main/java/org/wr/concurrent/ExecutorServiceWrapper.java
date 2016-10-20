package org.wr.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceWrapper implements Executor {

    private final ExecutorService executorService;

    public ExecutorServiceWrapper(ExecutorService executorService) {
        this.executorService = executorService;
    }


    public void execute(Runnable command) {

    }




    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
            throws InterruptedException {
       return executorService.invokeAll(tasks, timeout, unit);
    }
}
