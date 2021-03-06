/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.balyfix.streaming.session;

import it.balyfix.streaming.function.SerializableFunction;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;

import java.util.Collection;
import java.util.Collections;


public class SessionWindowAssigner<T> extends WindowAssigner<T, GlobalWindow>
{

    private static final long serialVersionUID = -1267068136238160725L;

    private final SerializableFunction<T, Boolean> isSessionStart;
	private final SerializableFunction<T, Boolean> isSessionEnd;
	private final boolean keepSessionElements;

	public SessionWindowAssigner(
		SerializableFunction<T, Boolean> isSessionStart,
		SerializableFunction<T, Boolean> isSessionEnd,
		boolean keepSessionElements) {
		this.isSessionStart = isSessionStart;
		this.isSessionEnd = isSessionEnd;
		this.keepSessionElements = keepSessionElements;
	}


    @Override
    public Collection<GlobalWindow> assignWindows(T element, long timestamp, WindowAssignerContext context)
    {
        if (keepSessionElements) {
            return Collections.singletonList(GlobalWindow.get());
        } else {
            if (isSessionStart.apply(element) || isSessionEnd.apply(element)) {
                return Collections.singletonList(GlobalWindow.get());
            } else {
                return Collections.EMPTY_LIST;
            }
        }
    }

    @Override
	public Trigger<T, GlobalWindow> getDefaultTrigger(StreamExecutionEnvironment streamExecutionEnvironment) {
		return new NeverTrigger<T>();
	}

	@Override
	public TypeSerializer<GlobalWindow> getWindowSerializer(ExecutionConfig executionConfig) {
		return new GlobalWindow.Serializer();
	}

    @Override
    public boolean isEventTime()
    {
        return false;
    }

    private static class NeverTrigger<T> extends Trigger<T, GlobalWindow>
    {

        private static final long serialVersionUID = -929185329580217460L;

        @Override
		public TriggerResult onElement(T t, long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
			return TriggerResult.CONTINUE;
		}

		@Override
		public TriggerResult onProcessingTime(long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
			return TriggerResult.CONTINUE;
		}

		@Override
		public TriggerResult onEventTime(long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
			return TriggerResult.CONTINUE;
		}

        @Override
        public void clear(GlobalWindow window, TriggerContext ctx) throws Exception
        {
            throw new IllegalArgumentException("Not implemented yet");
        }
    }
}
