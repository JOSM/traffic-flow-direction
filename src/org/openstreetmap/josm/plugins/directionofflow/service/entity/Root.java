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
package org.openstreetmap.josm.plugins.directionofflow.service.entity;

import java.util.List;
import org.openstreetmap.josm.plugins.directionofflow.entity.Cluster;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;


/**
 * Represents the root of the response content returned by the service component.
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class Root {

    private final RootStatus status;
    private List<Cluster> clusters;
    private List<RoadSegment> roadSegments;
    private List<Comment> comments;


    /**
     * Builds a new object, representing the response of the 'search' method.
     *
     * @param status the service response status
     * @param clusters a list of {@code Cluster}s
     * @param roadSegments a list of {@code RoadSegment}s
     */
    public Root(final RootStatus status, final List<Cluster> clusters, final List<RoadSegment> roadSegments) {
        this.status = status;
        this.clusters = clusters;
        this.roadSegments = roadSegments;
    }

    /**
     * Builds a new object, representing the response of the 'retrieveComment' method.
     *
     * @param status the service response status
     * @param comments the list of {@code Comment}s
     */
    public Root(final RootStatus status, final List<Comment> comments) {
        this.status = status;
        this.comments = comments;
    }


    public RootStatus getStatus() {
        return status;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}