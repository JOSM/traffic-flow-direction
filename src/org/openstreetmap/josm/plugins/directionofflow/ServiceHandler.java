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
package org.openstreetmap.josm.plugins.directionofflow;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.directionofflow.argument.BoundingBox;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.DataSet;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.service.DirectionOfFlowService;
import org.openstreetmap.josm.plugins.directionofflow.service.DirectionOfFlowServiceException;
import org.openstreetmap.josm.plugins.directionofflow.util.pref.PreferenceManager;


/**
 * Executes the service operations corresponding to user actions. If an operation fails, a corresponding message is
 * displayed to the user.
 *
 * @author Beata
 * @version $Revision: 11 $
 */
public class ServiceHandler {

    private final DirectionOfFlowService service = new DirectionOfFlowService();

    private static final ServiceHandler INSTANCE = new ServiceHandler();


    public static ServiceHandler getInstance() {
        return INSTANCE;
    }

    /**
     *
     * @param bbox
     * @param filter
     * @param zoom
     * @return
     */
    public DataSet search(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        DataSet result = new DataSet();
        try {
            result = service.search(bbox, filter, zoom);
        } catch (final DirectionOfFlowServiceException e) {
            handleException(e, true);
        }
        return result;
    }

    /**
     *
     * @param wayId
     * @param fromNodeId
     * @param toNodeId
     * @return
     */
    public List<Comment> retrieveComments(final RoadSegment roadSegment) {
        List<Comment> result = new ArrayList<>();
        try {
            result = service.retrieveComments(roadSegment.getWayId(), roadSegment.getFromNodeId(),
                    roadSegment.getToNodeId());
        } catch (final DirectionOfFlowServiceException e) {
            handleException(e, false);
        }
        return result;
    }

    /**
     *
     * @param comment
     * @param roadSegments
     */
    public void comment(final Comment comment, final List<RoadSegment> roadSegments) {
        try {
            service.comment(comment, roadSegments);
        } catch (final DirectionOfFlowServiceException e) {
            handleException(e, false);
        }
    }

    private void handleException(final Exception e, final boolean suppress) {
        if (suppress) {
            if (!PreferenceManager.getInstance().loadErrorSuppressFlag()) {
                PreferenceManager.getInstance().saveErrorSuppressFlag(suppress);
                JOptionPane.showMessageDialog(Main.parent, e.getMessage(), "Operation failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Main.parent, e.getMessage(), "Operation failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}