package org.wr.concurrent.reducer;

import org.apache.commons.lang3.tuple.Pair;
import org.wr.concurrent.Reducer;

public class StraightForwardReducer<T> implements Reducer<T> {

    @Override
    public T reduce(Pair<String, Object>... toReduce) {
        if(toReduce.length != 1) {
            throw new IllegalArgumentException("have to be only one in parameter");
        }
        return (T)toReduce[0].getValue();
    }
}
