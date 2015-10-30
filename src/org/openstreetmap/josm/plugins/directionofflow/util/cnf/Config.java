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
package org.openstreetmap.josm.plugins.directionofflow.util.cnf;


/**
 * Utility class holds run-time configuration properties.
 *
 * @author Beata
 * @version $Revision: 2 $
 */
public final class Config extends BaseConfig {

    private static final String CONFIG_FILE = "directionofflow_config.properties";
    private static final Config INSTANCE = new Config();
    private static final int SEARCH_DELAY = 600;
    private static final int MAX_CLUSTER_ZOOM = 14;

    private final String serviceUrl;
    private final String feedbackUrl;
    private int searchDelay;
    private int maxClusterZoom;


    private Config() {
        super(CONFIG_FILE);

        serviceUrl = readProperty("service.url");
        if (serviceUrl == null) {
            throw new ExceptionInInitializerError("Missing service url.");
        }

        feedbackUrl = readProperty("feedback.url");

        try {
            searchDelay = Integer.parseInt(readProperty("search.delay"));
        } catch (final NumberFormatException e) {
            searchDelay = SEARCH_DELAY;
        }

        try {
            maxClusterZoom = Integer.parseInt(readProperty("zoom.cluster.max"));
        } catch (final NumberFormatException e) {
            maxClusterZoom = MAX_CLUSTER_ZOOM;
        }

        try {
            maxClusterZoom = Integer.parseInt(readProperty("zoom.cluster.max"));
        } catch (final NumberFormatException e) {
            maxClusterZoom = MAX_CLUSTER_ZOOM;
        }
    }


    public static Config getInstance() {
        return INSTANCE;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public int getMaxClusterZoom() {
        return maxClusterZoom;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }
}