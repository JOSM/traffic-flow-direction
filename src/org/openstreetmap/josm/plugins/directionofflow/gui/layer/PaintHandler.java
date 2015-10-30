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
package org.openstreetmap.josm.plugins.directionofflow.gui.layer;

import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.ARROW_LENGTH;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.CLUSTER_COMPOSITE;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.NORMAL_COLOR;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.NORMAL_COMPOSITE;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.PHI;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.SEGMENT_STROKE;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.SELECTED_COLOR;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.SELECTED_SEGMENT_STROKE;
import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.SEL_ARROW_LENGTH;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.SortedMap;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.directionofflow.entity.Cluster;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;


/**
 *
 * @author Beata
 * @version $Revision: 11 $
 */
class PaintHandler {

    /**
     *
     * @param graphics
     * @param mapView
     * @param roadSegments
     */
    void drawRoadSegments(final Graphics2D graphics, final MapView mapView,
            final List<RoadSegment> roadSegments, final List<RoadSegment> selectedRoadSigns) {
        for (final RoadSegment roadSegment : roadSegments) {
            if (!selectedRoadSigns.contains(roadSegment)) {
                drawRoadSegment(graphics, mapView, roadSegment, false);
            }
        }
        for (final RoadSegment roadSegment : selectedRoadSigns) {
            drawRoadSegment(graphics, mapView, roadSegment, true);
        }
    }

    private void drawRoadSegment(final Graphics2D graphics, final MapView mapView, final RoadSegment segment,
            final boolean selected) {
        // draw segment
        if (selected) {
            graphics.setStroke(SELECTED_SEGMENT_STROKE);
            graphics.setColor(SELECTED_COLOR);
        } else {
            graphics.setStroke(SEGMENT_STROKE);
            graphics.setColor(NORMAL_COLOR);
        }
        final GeneralPath path = PaintUtil.buildPath(graphics, mapView, segment);
        graphics.draw(path);

        // draw arrow
        final Point tip = mapView.getPoint(segment.getPoints().get(segment.getPoints().size() - 1));
        final Point tail = mapView.getPoint(segment.getPoints().get(segment.getPoints().size() - 2));
        final double theta = Math.atan2((tip.getY() - tail.getY()), (tip.getX() - tail.getX()));
        double rho = theta + PHI;
        final double arrowLength = selected ? SEL_ARROW_LENGTH : ARROW_LENGTH;
        for (int j = 0; j < 2; j++) {
            graphics.draw(new Line2D.Double(tip.getX(), tip.getY(), tip.getX() - arrowLength * Math.cos(rho),
                    tip.getY() - arrowLength * Math.sin(rho)));
            rho = theta - PHI;
        }
    }

    /**
     *
     * @param graphics
     * @param mapView
     * @param zoom
     * @param clusters
     */
    void drawClusters(final Graphics2D graphics, final MapView mapView, final int zoom,
            final List<Cluster> clusters) {
        final SortedMap<Integer, Double> clusterRadiusMap = PaintUtil.generateClusterRadiusMap(zoom, clusters);
        graphics.setComposite(CLUSTER_COMPOSITE);
        for (final Cluster cluster : clusters) {
            final Double radius = PaintUtil.clusterRadius(clusterRadiusMap, cluster.getSize());
            graphics.draw(
                    PaintUtil.buildCircle(graphics, mapView.getPoint2D(cluster.getPoint()), NORMAL_COLOR, radius));
        }
        graphics.setComposite(NORMAL_COMPOSITE);
    }
}