/*
 *  Copyright 2015 Telenav, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.openstreetmap.josm.plugins.directionofflow.argument;

import java.util.List;
import org.openstreetmap.josm.plugins.directionofflow.entity.ConfidenceLevel;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;


/**
 * Defines the search filters.
 *
 * @author Beata
 * @version $Revision: 11 $
 */
public class SearchFilter {

    private final Status status;
    private final List<ConfidenceLevel> confidenceLevels;

    /** default search filter */
    public static final SearchFilter DEFAULT = new SearchFilter(Status.OPEN, null);


    /**
     * Builds a new object with the given arguments.
     *
     * @param status the road segment/cluster status
     * @param confidenceLevel the list of confidence levels
     */
    public SearchFilter(final Status status, final List<ConfidenceLevel> confidenceLevels) {
        this.status = status;
        this.confidenceLevels = confidenceLevels;
    }


    public Status getStatus() {
        return status;
    }

    public List<ConfidenceLevel> getConfidenceLevels() {
        return confidenceLevels;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((confidenceLevels == null) ? 0 : confidenceLevels.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof SearchFilter) {
            final SearchFilter other = (SearchFilter) obj;
            result = (status == null && other.getStatus() == null)
                    || (status != null && status.equals(other.getStatus()));
            result = result && ((confidenceLevels == null && other.getConfidenceLevels() == null)
                    || (confidenceLevels != null && confidenceLevels.equals(other.getConfidenceLevels())));

        }
        return result;
    }
}