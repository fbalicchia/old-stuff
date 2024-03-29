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

package it.balyfix.streaming.predicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderObjectPredicateMatchRegex extends OrderObjectPredicateMatch<String>
{

	private final Pattern pattern;

	public OrderObjectPredicateMatchRegex(String key, Pattern pattern) {
		super(key);
		this.pattern = pattern;
	}

	@Override
	public boolean match(String element) {
		if (element == null) {
			return false;
		} else {
			Matcher matcher = pattern.matcher(element);
			return matcher.matches();
		}
	}
}
