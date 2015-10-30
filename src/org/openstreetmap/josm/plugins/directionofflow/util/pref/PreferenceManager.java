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
package org.openstreetmap.josm.plugins.directionofflow.util.pref;

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.ConfidenceLevel;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;


/**
 * Utility class, manages save and load (put & get) operations of the preference variables. The preference variables are
 * saved into a global preference file. Preference variables are static variables which can be accessed from any plugin
 * class. Values saved in this global file, can be accessed also after a JOSM restart.
 *
 * @author Beata
 * @version $Revision: 51 $
 */
public final class PreferenceManager {

    private static final PreferenceManager INSTANCE = new PreferenceManager();

    private PreferenceManager() {}

    public static PreferenceManager getInstance() {
        return INSTANCE;
    }

    /**
     * Loads the 'error suppress' flag.
     *
     * @return a boolean value
     */
    public boolean loadErrorSuppressFlag() {
        return Main.pref.getBoolean(Keys.ERROR_SUPPRESS);
    }

    /**
     * Saves the error suppress flag to the preference file.
     *
     * @param flag a boolean value
     */
    public void saveErrorSuppressFlag(final boolean flag) {
        Main.pref.put(Keys.ERROR_SUPPRESS, flag);
    }

    /**
     * Loads the tip suppress flag from the preference file.
     *
     * @return a boolean value
     */
    public boolean loadTipSuppressFlag() {
        return Main.pref.getBoolean(Keys.TIP_SUPPRESS);
    }

    /**
     * Saves the tip suppress flag to the preference file.
     *
     * @param flag a boolean
     */
    public void saveTipSuppressFlag(final boolean flag) {
        Main.pref.put(Keys.TIP_SUPPRESS, flag);
    }

    /**
     * Loads the search filters from the preference file.
     *
     * @return a {@code SearchFilter} object
     */
    public SearchFilter loadSearchFilter() {
        final String statusStr = Main.pref.get(Keys.STATUS);
        final Status status = statusStr != null && !statusStr.isEmpty() ? Status.valueOf(statusStr) : null;

        final List<ConfidenceLevelEntry> entries =
                Main.pref.getListOfStructs(Keys.CONFIDENCE_LEVEL, ConfidenceLevelEntry.class);
        List<ConfidenceLevel> confidenceLevels = null;
        if (entries != null && !entries.isEmpty()) {
            confidenceLevels = new ArrayList<>();
            for (final ConfidenceLevelEntry entry : entries) {
                confidenceLevels.add(ConfidenceLevel.valueOf(entry.getName()));
            }
        }
        return status == null && confidenceLevels == null ? SearchFilter.DEFAULT
                : new SearchFilter(status, confidenceLevels);
    }

    /**
     * Saves the search filter.
     *
     * @param filter a {@code SearchFilter} object
     */
    public void saveSearchFilter(final SearchFilter filter) {
        if (filter != null) {
            final String status = filter.getStatus() != null ? filter.getStatus().name() : "";
            Main.pref.put(Keys.STATUS, status);

            // confidence levels
            final List<ConfidenceLevelEntry> entries = new ArrayList<ConfidenceLevelEntry>();
            if (filter.getConfidenceLevels() != null) {
                for (final ConfidenceLevel confidence : filter.getConfidenceLevels()) {
                    entries.add(new ConfidenceLevelEntry(confidence));
                }
            }
            Main.pref.putListOfStructs(Keys.CONFIDENCE_LEVEL, entries, ConfidenceLevelEntry.class);
        }
    }

    /**
     * Saves the 'filters changed' flag.
     *
     * @param changed a boolean value
     */
    public void saveFiltersChangedFlag(final boolean changed) {
        Main.pref.put(Keys.FILTERS_CHANGED, "");
        Main.pref.put(Keys.FILTERS_CHANGED, "" + changed);
    }

    /**
     * Loads the user's OSM username.
     *
     * @return a {@code String} object
     */
    public String loadOsmUsername() {
        final String username = Main.pref.get(Keys.OSM_USERNAME);
        return username == null ? "" : username;
    }

    /**
     * Saves the user's OSM username.
     *
     * @param username a {@code String} value
     */
    public void saveOsmUsername(final String username) {
        Main.pref.put(Keys.OSM_USERNAME, username);
    }
}