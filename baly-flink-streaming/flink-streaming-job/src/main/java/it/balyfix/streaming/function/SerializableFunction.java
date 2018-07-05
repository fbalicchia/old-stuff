package it.balyfix.streaming.function;

import java.io.Serializable;
import java.util.function.Function;


/**
 * Created by fbalicchia on 14/03/2018.
 */
public interface SerializableFunction<T,R> extends Function<T,R>, Serializable
{
}
