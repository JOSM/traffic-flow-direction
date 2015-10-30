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
package org.openstreetmap.josm.plugins.directionofflow.service;

import java.util.List;
import org.openstreetmap.josm.plugins.directionofflow.argument.BoundingBox;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.ConfidenceLevel;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.Config;
import org.openstreetmap.josm.plugins.directionofflow.util.http.HttpUtil;


/**
 * Utility class, builds HTTP queries.
 *
 * @author Beata
 * @version $Revision: 11 $
 */
final class HttpQueryBuilder {

    private static final char QUESTIONM = '?';
    private static final char EQ = '=';
    private static final char AND = '&';

    private final StringBuilder query;

    /**
     * Builds an empty query builder.
     */
    HttpQueryBuilder() {
        query = new StringBuilder();
    }

    /**
     * Builds a new {@code HttpQueryBuilder} for building the search method request.
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter a {@code SearchFilter} specifies the filters to be applied
     * @param zoom specifies the current zoom level
     */
    HttpQueryBuilder(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        this();

        addFormatFilter();
        addBoundingBoxFilter(bbox);
        addZoomFilter(zoom);
        addClientFilter(zoom);

        if (filter != null) {
            addStatusFilter(filter.getStatus());
            addConfidenceLevelsFilter(filter.getConfidenceLevels());
        }
    }

    /**
     * Builds a new {@code HttpQueryBuilder} for building the retrieveComments request.
     *
     * @param tileX the tile's X identifier
     * @param tileY the tile's Y identifier
     */
    HttpQueryBuilder(final Long wayId, final Long fromNodeId, final Long toNodeId) {
        this();

        addFormatFilter();
        addRoadSegmentId(wayId, fromNodeId, toNodeId);
    }

    /**
     * Builds a new HTTP query for the specifies method with the currently set fields.
     *
     * @param method specifies a MissingGeometryService method
     * @return a {@code String} object
     */
    String build(final String method) {
        final StringBuilder url = new StringBuilder(Config.getInstance().getServiceUrl());
        url.append(method).append(QUESTIONM).append(query);
        return url.toString();
    }

    private void addBoundingBoxFilter(final BoundingBox bbox) {
        query.append(AND).append(Constants.NORTH.toString()).append(EQ).append(bbox.getNorth());
        query.append(AND).append(Constants.SOUTH.toString()).append(EQ).append(bbox.getSouth());
        query.append(AND).append(Constants.EAST.toString()).append(EQ).append(bbox.getEast());
        query.append(AND).append(Constants.WEST.toString()).append(EQ).append(bbox.getWest());
    }

    private void addFormatFilter() {
        query.append(Constants.FORMAT.toString()).append(EQ).append(Constants.FORMAT_VAL.toString());
    }

    private void addConfidenceLevelsFilter(final List<ConfidenceLevel> confidenceLevels) {
        if (confidenceLevels != null) {
            query.append(AND).append(Constants.CONFIDENCE_LEVEL).append(EQ);
            query.append(HttpUtil.utf8Encode(confidenceLevels));
        }
    }

    private void addStatusFilter(final Status status) {
        if (status != null) {
            query.append(AND).append(Constants.STATUS.toString()).append(EQ).append(HttpUtil.utf8Encode(status.name()));
        }
    }

    private void addRoadSegmentId(final Long wayId, final Long fromNodeId, final Long toNodeId) {
        query.append(AND).append(Constants.WAY_ID).append(EQ).append(wayId);
        query.append(AND).append(Constants.FROM_NODE_ID).append(EQ).append(fromNodeId);
        query.append(AND).append(Constants.TO_NODE_ID).append(EQ).append(toNodeId);
    }

    private void addZoomFilter(final int zoom) {
        query.append(AND).append(Constants.ZOOM.toString()).append(EQ).append(zoom);
    }

    private void addClientFilter(final int zoom) {
        if (zoom <= Config.getInstance().getMaxClusterZoom()) {
            query.append(AND).append(Constants.CLIENT.toString()).append(EQ).append(Constants.CLIENT_VAL.toString());
        }
    }
}