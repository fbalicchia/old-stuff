package it.balyfix;


import it.balyfix.streaming.JSONObjectAssignerWithPeriodicWatermarks;
import it.balyfix.streaming.function.AlertWindowOperator2;
import it.balyfix.streaming.function.JSONObjectAlertFunction;
import it.balyfix.streaming.function.TimestampExtractorFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class AlertGenerationTest
{

	@Test
	public void testAlertGeneration() throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		List<String> inputData = new ArrayList<>();

		inputData.add("{\"timestamp\": \"1987-09-30 12:56:12.123456\", \"event_type\": \"info\" }");
		inputData.add("{\"timestamp\": \"1987-09-30 12:56:13.123456\", \"event_type\": \"info\" }");
		inputData.add("{\"timestamp\": \"1987-09-30 12:56:14.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:15.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:16.123456\", \"event_type\": \"info\" }");
		inputData.add("{\"timestamp\": \"1987-09-30 12:56:17.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:18.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:19.123456\", \"event_type\": \"error\" }");

        inputData.add("{\"timestamp\": \"1987-09-30 12:56:40.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:41.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:42.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:43.123456\", \"event_type\": \"error\" }");
        inputData.add("{\"timestamp\": \"1987-09-30 12:56:44.123456\", \"event_type\": \"error\" }");


		DataStream<String> stringInput = env.fromCollection(inputData);

		String timestampPattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";

		TimestampExtractorFunction timestampExtractor = new TimestampExtractorFunction("timestamp", timestampPattern);

        DataStream<JSONObject> input = stringInput.map(new MapFunction<String, JSONObject>() {
            private static final long serialVersionUID = 8864508455786310175L;

            @Override
            public JSONObject map(String s) throws Exception {
                return new JSONObject(s);
            }
        }).assignTimestampsAndWatermarks(new JSONObjectAssignerWithPeriodicWatermarks(timestampExtractor, 0));



		TypeInformation<JSONObject> outTypeInformation = TypeExtractor.getForClass(JSONObject.class);

		Function<Long, JSONObject> alertFunction = new JSONObjectAlertFunction(
			"alert",
			"testAlert",
			"timestamp",
			timestampPattern);

		DataStream<JSONObject> result = input.transform(
			"AlertWindow",
			outTypeInformation,
			new AlertWindowOperator2(5, 5000, alertFunction)
		).setParallelism(1);

        result.print();
		env.execute();
	}

}
