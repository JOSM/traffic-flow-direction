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

import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.CLUSTER_RADIUS;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.directionofflow.entity.Cluster;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.ClusterConfig;


/**
 * Helper class, holds utility methods.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
final class PaintUtil {

    private PaintUtil() {}

    static SortedMap<Integer, Double> generateClusterRadiusMap(final int zoom, final List<Cluster> clusters) {
        SortedMap<Integer, Double> map;
        final int max = clusters.get(clusters.size() - 1).getSize();
        final List<Double> radiusList = ClusterConfig.getInstance().getRadiusList(zoom);
        final int count = ClusterConfig.getInstance().getCount();
        if (clusters.size() > 1) {
            final int x = max / count;
            map = new TreeMap<>();
            int i;
            for (i = 1; i <= count - 1; i++) {
                map.put(i * x, radiusList.get(i - 1));
            }
            map.put(max, radiusList.get(i - 1));
        } else {
            map = new TreeMap<>();
            map.put(max, radiusList.get(count - 1));
        }
        return map;
    }

    static Double clusterRadius(final SortedMap<Integer, Double> map, final Integer value) {
        Double radius = null;
        if (map.size() > 1) {
            for (final Integer key : map.keySet()) {
                if (value <= key) {
                    radius = map.get(key);
                    break;
                }
            }
        } else {
            radius = map.get(value);
        }
        return radius != null ? radius : CLUSTER_RADIUS;
    }

    static GeneralPath buildPath(final Graphics2D g2D, final MapView mv, final RoadSegment segment) {
        final GeneralPath path = new GeneralPath();
        Point point = mv.getPoint(segment.getPoints().get(0));
        path.moveTo(point.getX(), point.getY());
        for (int i = 1; i < segment.getPoints().size(); i++) {
            point = mv.getPoint(segment.getPoints().get(i));
            path.lineTo(point.getX(), point.getY());
        }
        return path;
    }


    static Ellipse2D.Double buildCircle(final Graphics2D graphics, final Point2D point, final Color color,
            final double radius) {
        final Ellipse2D.Double circle = new Ellipse2D.Double(point.getX(), point.getY(), radius, radius);
        graphics.setColor(color);
        graphics.fill(circle);
        return circle;
    }
}