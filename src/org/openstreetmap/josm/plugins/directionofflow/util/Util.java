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
package org.openstreetmap.josm.plugins.directionofflow.util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;


/**
 * Helper class, contains utility methods.
 *
 * @author Beata
 * @version $Revision: 51 $
 */
public final class Util {

    /** maximum snap distances used when selecting a segment */
    private static final double SEG_DIST = 4.0;

    private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 18;
    private static final int TILE_SIZE = 1024;

    private Util() {}

    /**
     * Returns the zoom level based on the given bounds.
     *
     * @param bounds the map bounds
     * @return an integer
     */
    public static int zoom(final Bounds bounds) {
        return ((int) Math.min(MAX_ZOOM, Math.max(MIN_ZOOM,
                Math.round(Math.floor(Math.log(TILE_SIZE / bounds.asRect().height) / Math.log(2))))));
    }

    /**
     * Returns the segment nearby the given point. If there is no road segment near the method returns null.
     *
     * @param roadSegments a list of {@code RoadSegment}s
     * @param point the location where the user clicked
     * @return a {@code RoadSegment}
     */
    public static RoadSegment nearbyRoadSegment(final List<RoadSegment> roadSegments, final Point point) {
        RoadSegment result = null;
        double minDistance = Double.MAX_VALUE;
        for (final RoadSegment roadSegment : roadSegments) {
            final double distance = minDistance(roadSegment.getPoints(), point);
            if (distance <= minDistance) {
                minDistance = distance;
                result = roadSegment;
            }
        }
        if (minDistance > SEG_DIST) {
            result = null;
        }
        return result;
    }

    private static double minDistance(final List<LatLon> points, final Point point) {
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < points.size() - 1; i++) {
            final Point start = Main.map.mapView.getPoint(points.get(i));
            final Point end = Main.map.mapView.getPoint(points.get(i + 1));
            final double distance = distance(point, start, end);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    private static double distance(final Point2D point, final Point2D start, final Point2D end) {
        final double xD = end.getX() - start.getX();
        final double yD = end.getY() - start.getY();
        final double u =
                ((point.getX() - start.getX()) * xD + (point.getY() - start.getY()) * yD) / (xD * xD + yD * yD);
        final Point2D nearestPoint =
                u < 0 ? start : ((u > 1) ? end : new Point2D.Double(start.getX() + u * xD, start.getY() + u * yD));
        return nearestPoint.distance(point);
    }

}