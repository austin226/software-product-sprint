// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Arrays;
import java.util.Collection;

public final class FindMeetingQuery {
    private static final Collection<TimeRange> NO_OPTIONS = Arrays.asList();

    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        // Cannot schedule a meeting longer than a single day.
        if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
            return NO_OPTIONS;
        }

        // If there is an event, split up the day into before and after.
        for (Event event : events) {
            TimeRange during = event.getWhen();
            TimeRange before = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, during.start(), false);
            TimeRange after = TimeRange.fromStartEnd(during.end(), TimeRange.END_OF_DAY, true);
            return Arrays.asList(before, after);
        }

        // No events, the whole day is free.
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }
}
