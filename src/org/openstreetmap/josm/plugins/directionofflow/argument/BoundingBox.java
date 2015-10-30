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

import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.gui.MapView;


/**
 * Defines the attributes of a bounding box.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
public class BoundingBox {

    /* longitude interval limits */
    private static final double MIN_LON = -180.0;
    private static final double MAX_LON = 180.0;

    /* latitude interval limits */
    private static final double MIN_LAT = -90.0;
    private static final double MAX_LAT = 90.0;

    private final double north;
    private final double south;
    private final double east;
    private final double west;


    /**
     * Builds a new bounding box based on the given map view.
     *
     * @param mapView the current {@code MapView}
     */
    public BoundingBox(final MapView mapView) {
        final Bounds bounds =
                new Bounds(mapView.getLatLon(0, mapView.getHeight()), mapView.getLatLon(mapView.getWidth(), 0));
        this.north = bounds.getMax().lat() > MAX_LAT ? MAX_LAT : bounds.getMax().lat();
        this.south = bounds.getMin().lat() < MIN_LAT ? MIN_LAT : bounds.getMin().lat();
        this.east = bounds.getMax().lon() > MAX_LON ? MAX_LON : bounds.getMax().lon();
        this.west = bounds.getMin().lon() < MIN_LON ? MIN_LON : bounds.getMin().lon();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        final int bit = 32;
        int result = 1;
        long temp = Double.doubleToLongBits(east);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(north);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(south);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(west);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof BoundingBox) {
            final BoundingBox other = (BoundingBox) obj;
            if (equals(east, other.getEast()) && equals(north, other.getNorth()) && equals(south, other.getSouth())
                    && equals(west, other.getWest())) {
                result = true;
            }
        }
        return result;
    }

    private boolean equals(final double obj1, final double obj2) {
        return Double.doubleToLongBits(obj1) == Double.doubleToLongBits(obj2);
    }


    /**
     * Returns the eastern corner of the bounding box
     *
     * @return a double value
     */
    public double getEast() {
        return east;
    }

    /**
     * Returns the northern corner of the bounding box
     *
     * @return a double value
     */
    public double getNorth() {
        return north;
    }

    /**
     * Returns the southern corner of the bounding box
     *
     * @return a double value
     */
    public double getSouth() {
        return south;
    }

    /**
     * Returns the western corner of the bounding box
     *
     * @return a double value
     */
    public double getWest() {
        return west;
    }
}