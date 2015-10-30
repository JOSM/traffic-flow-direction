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
 * Custom exception used by the {@code DirectionOfFlowService}.
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class DirectionOfFlowServiceException extends Exception {

    private static final long serialVersionUID = -5663139183652378024L;

    /**
     * Builds a new object with the given argument.
     *
     * @param msg the message of the exception
     */
    public DirectionOfFlowServiceException(final String msg) {
        super(msg);
    }

    /**
     * Builds a new object with the given argument.
     *
     * @param cause the cause of the exception
     */
    public DirectionOfFlowServiceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Builds a new object with the given argument.
     *
     * @param msg the message of the exception
     * @param cause the cause of the exception
     */
    public DirectionOfFlowServiceException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}