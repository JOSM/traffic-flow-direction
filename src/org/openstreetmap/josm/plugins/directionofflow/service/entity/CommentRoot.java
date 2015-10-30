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

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;


/**
 * Defines the body of the comment operation.
 *
 * @author Beata
 * @version $Revision: 11 $
 */
public class CommentRoot {

    private final String username;
    private final String text;
    private final Status status;
    private final List<RoadSegment> roadSegments;


    /**
     * Builds a new object based on the given arguments.
     *
     * @param comment
     * @param roadSegments
     */
    public CommentRoot(final Comment comment, final List<RoadSegment> roadSegments) {
        this.username = comment.getUsername();
        this.text = comment.getText();
        this.status = comment.getStatus();
        this.roadSegments = new ArrayList<>();
        for (final RoadSegment roadSegment : roadSegments) {
            this.roadSegments.add(
                    new RoadSegment(roadSegment.getWayId(), roadSegment.getFromNodeId(), roadSegment.getToNodeId()));
        }
    }


    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }
}