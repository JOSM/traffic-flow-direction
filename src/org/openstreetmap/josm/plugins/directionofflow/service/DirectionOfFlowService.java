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

import java.net.HttpURLConnection;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.directionofflow.argument.BoundingBox;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.DataSet;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.service.entity.CommentRoot;
import org.openstreetmap.josm.plugins.directionofflow.service.entity.Root;
import org.openstreetmap.josm.plugins.directionofflow.util.http.HttpConnector;
import org.openstreetmap.josm.plugins.directionofflow.util.http.HttpConnectorException;
import org.openstreetmap.josm.plugins.directionofflow.util.http.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


/**
 * Executes the DirectionOfFlowService operations.
 *
 * @author Beata
 * @version $Revision: 47 $
 */
public class DirectionOfFlowService {

    private final Gson gson;

    /**
     * Builds a new service object.
     */
    public DirectionOfFlowService() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LatLon.class, new LatLonDeserializer());
        this.gson = builder.create();
    }

    /**
     * Searches for data in the given bounding box and filters. Depending on the zoom level either returns a list of
     * clusters or a list of segments.
     *
     * @param bbox a {@code BoundingBox} defines the searching area
     * @param filter a {@code SearchFilter} defines the filters to be applied
     * @param zoom the current zoom level
     * @return a {@code DataSet} containing a list of clusters/road segment
     * @throws DirectionOfFlowServiceException if the operation fails
     */
    public DataSet search(final BoundingBox bbox, final SearchFilter filter, final int zoom)
            throws DirectionOfFlowServiceException {
        final String url = new HttpQueryBuilder(bbox, filter, zoom).build(Constants.SEARCH);
        System.out.println(url);
        final Root root = executeGet(url);
        verifyResponseStatus(root);
        return new DataSet(root.getClusters(), root.getRoadSegments());
    }

    /**
     * Retrieves the comments of the given road segment, ordered descending by timestamp.
     *
     * @param wayId the identifier of the OSM way which contains this segment
     * @param fromNodeId the identifier of the OSM node which marks the start of this road segment
     * @param toNodeId the identifier of the OSM node which marks the end of this road segment
     * @return a list of {@code Comment}s
     * @throws DirectionOfFlowServiceException if the operation fails
     */
    public List<Comment> retrieveComments(final Long wayId, final Long fromNodeId, final Long toNodeId)
            throws DirectionOfFlowServiceException {
        final String url = new HttpQueryBuilder(wayId, fromNodeId, toNodeId).build(Constants.RETRIEVE_COMMENTS);
        final Root root = executeGet(url);
        verifyResponseStatus(root);
        return root.getComments();
    }

    /**
     * Adds a comment to a given road segments. If the comment has a status, then the status of the road segments is
     * changed.
     *
     * @param comment a {@code Comment} to be added
     * @param roadSegments a list of {@code RoadSegment}s
     * @throws DirectionOfFlowServiceException if the operation fails
     */
    public void comment(final Comment comment, final List<RoadSegment> roadSegments)
            throws DirectionOfFlowServiceException {
        final String url = new HttpQueryBuilder().build(Constants.COMMENT.toString());
        final CommentRoot commentRoot = new CommentRoot(comment, roadSegments);
        final String content = gson.toJson(commentRoot, CommentRoot.class);
        final Root root = executePost(url, content);
        verifyResponseStatus(root);
    }


    private Root executeGet(final String url) throws DirectionOfFlowServiceException {
        String response = null;
        try {
            response = new HttpConnector(url, HttpMethod.GET).read();
        } catch (final HttpConnectorException e) {
            throw new DirectionOfFlowServiceException(e);
        }
        return buildRoot(response);
    }

    private Root executePost(final String url, final String content) throws DirectionOfFlowServiceException {
        String response = null;
        try {
            final HttpConnector connector = new HttpConnector(url, HttpMethod.POST);
            connector.write(content);
            response = connector.read();
        } catch (final HttpConnectorException e) {
            throw new DirectionOfFlowServiceException(e);
        }
        return buildRoot(response);
    }

    private Root buildRoot(final String response) throws DirectionOfFlowServiceException {
        Root root = null;
        if (response != null) {
            try {
                root = gson.fromJson(response, Root.class);
            } catch (final JsonSyntaxException e) {
                throw new DirectionOfFlowServiceException(e);
            }
        }
        return root;
    }

    private void verifyResponseStatus(final Root root) throws DirectionOfFlowServiceException {
        if (root.getStatus() != null && root.getStatus().getHttpCode() != HttpURLConnection.HTTP_OK) {
            throw new DirectionOfFlowServiceException(root.getStatus().getApiMessage());
        }
    }
}