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
package org.openstreetmap.josm.plugins.directionofflow.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represent's the data set of the 'DirectionOfFlowLayer'.
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class DataSet {

    private final List<Cluster> clusters;
    private final List<RoadSegment> roadSegments;

    /**
     * Builds an empty data set.
     */
    public DataSet() {
        this.clusters = new ArrayList<>();
        this.roadSegments = new ArrayList<>();
    }

    /**
     * Builds a data set with the given arguments.
     *
     * @param clusters the list of {@code Cluster}s
     * @param roadSegments the list of {@code RoadSegment}s
     */
    public DataSet(final List<Cluster> clusters, final List<RoadSegment> roadSegments) {
        this.clusters = clusters == null ? new ArrayList<Cluster>() : clusters;
        if (!this.clusters.isEmpty()) {
            Collections.sort(this.clusters);
        }
        this.roadSegments = roadSegments == null ? new ArrayList<RoadSegment>() : roadSegments;
    }


    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }
}