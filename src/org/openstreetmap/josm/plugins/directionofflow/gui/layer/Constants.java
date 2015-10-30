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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Beata
 * @version $Revision: 47 $
 */
final class Constants {

    private Constants() {}

    /* composite constants */
    static final Composite NORMAL_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F);
    static final Composite CLUSTER_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80F);

    /* colors used to represent a cluster/road segment in the normal state */
    static final Color NORMAL_COLOR = new Color(238, 118, 0);
    static final Color SELECTED_COLOR = new Color(220, 20, 60);

    static final double CLUSTER_RADIUS = 50;

    static final Stroke SEGMENT_STROKE = new BasicStroke(5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    static final Stroke SELECTED_SEGMENT_STROKE = new BasicStroke(10F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    static final double PHI = Math.toRadians(40);
    static final double ARROW_LENGTH = 10;
    static final double SEL_ARROW_LENGTH = 17;

    public static final Map<RenderingHints.Key, Object> RENDERING_MAP = createRenderingMap();

    private static Map<RenderingHints.Key, Object> createRenderingMap() {
        final Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return map;
    }
}