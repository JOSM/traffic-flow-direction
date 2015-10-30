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
package org.openstreetmap.josm.plugins.directionofflow.service.entity;


/**
 * Defines the attributes of a HTTP response status.
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class RootStatus {

    private final Integer apiCode;
    private final String apiMessage;
    private final Integer httpCode;
    private final String httpMessage;


    /**
     * Builds a new {@code Status} object with the given arguments.
     *
     * @param apiCode represents the FCDSearchService API code
     * @param apiMessage represents the FCDSearchService API message
     * @param httpCode represents the HTTP code
     * @param httpMessage represents the HTTP message associated with the HTTP code
     */
    public RootStatus(final Integer apiCode, final String apiMessage, final Integer httpCode, final String httpMessage) {
        this.apiCode = apiCode;
        this.apiMessage = apiMessage;
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
    }


    public Integer getApiCode() {
        return apiCode;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }
}