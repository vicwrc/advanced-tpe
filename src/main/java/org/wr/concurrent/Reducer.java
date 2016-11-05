package org.wr.concurrent;

import org.apache.commons.lang3.tuple.Pair;

public interface Reducer<T> {

    T reduce(Pair<String, Object>... toReduce);



}
