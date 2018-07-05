package it.balyfix.streaming;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.json.JSONObject;

import java.util.function.Function;


/**
 * Created by fbalicchia on 10/03/2018.
 */
public class JSONObjectAssignerWithPeriodicWatermarks implements AssignerWithPeriodicWatermarks<JSONObject>
{
    private static final long serialVersionUID = -5409422629675407036L;

    private final Function<JSONObject, Long> timestampExtractor;

    private final long delay;

    private long currentWatermark = Long.MIN_VALUE;


    public JSONObjectAssignerWithPeriodicWatermarks(Function<JSONObject, Long> timestampExtractor, long delay)
    {
        this.timestampExtractor = timestampExtractor;
        this.delay = delay;
    }

    @Override
    public Watermark getCurrentWatermark()
    {
        return new Watermark(currentWatermark);
    }

    @Override
    public long extractTimestamp(JSONObject element, long previousElementTimestamp)
    {
        Long extractTimeStamp = timestampExtractor.apply(element);
        currentWatermark = extractTimeStamp - delay;
        return extractTimeStamp;
    }
}
