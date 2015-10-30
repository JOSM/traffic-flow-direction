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


/**
 * Holds service method and argument names.
 *
 * @author Beata
 * @version $Revision: 9 $
 */
final class Constants {

    private Constants() {}

    static final String SEARCH = "search";
    static final String NORTH = "north";

    static final String SOUTH = "south";
    static final String EAST = "east";
    static final String WEST = "west";
    static final String ZOOM = "zoom";
    static final String STATUS = "status";
    static final String CONFIDENCE_LEVEL = "confidenceLevel";

    static final String COMMENT = "comment";

    static final String RETRIEVE_COMMENTS = "retrieveComments";
    static final String WAY_ID = "wayId";
    static final String FROM_NODE_ID = "fromNodeId";
    static final String TO_NODE_ID = "toNodeId";

    static final String FORMAT = "format";
    static final String FORMAT_VAL = "json";

    static final String CLIENT = "client";
    static final String CLIENT_VAL = "JOSM";
}