package it.balyfix.streaming.function;



import java.io.Serializable;
import java.util.function.BiFunction;


/**
 * Created by fbalicchia on 15/03/2018.
 */
public interface SerializableFunction2<T1,U,R> extends BiFunction<T1,U,R>, Serializable
{
}
