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

package it.balyfix.streaming.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;


public class AlertEvent<IN> implements Serializable {
    private static final long serialVersionUID = -2637972695918178118L;


    @Getter
    @Setter
	private long timestamp;

    @Getter
    @Setter
	private boolean processed;
	@Getter
    private IN value;

	public AlertEvent(long timestamp, IN value) {
		this.timestamp = timestamp;
		this.processed = false;
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AlertEvent) {
			AlertEvent other = (AlertEvent) obj;

			return other.canEqual(this)	&& timestamp == other.timestamp;
		} else {
			return false;
		}
	}


	public boolean canEqual(AlertEvent event) {
		return event instanceof AlertEvent;
	}

	public void markProcessed() {
		this.processed = true;
	}

	public static class AlertEventComparator implements Comparator<AlertEvent>, Serializable {

        private static final long serialVersionUID = 8006179483692450921L;

        @Override
		public int compare(AlertEvent o1, AlertEvent o2) {
			if (o1.getTimestamp() > o2.getTimestamp()) {
				return -1;
			} else if (o1.getTimestamp() < o2.getTimestamp()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
