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
package org.openstreetmap.josm.plugins.directionofflow.gui.details.filter;

import static org.openstreetmap.josm.plugins.directionofflow.gui.GuiBuilder.BOLD_12;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.ConfidenceLevel;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;
import org.openstreetmap.josm.plugins.directionofflow.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.directionofflow.util.pref.PreferenceManager;


/**
 * Displays the possible data filters.
 *
 * @author Beata
 * @version $Revision: 11 $
 */
class FilterPanel extends JPanel {

    private static final long serialVersionUID = -5334658963317204974L;

    /* UI components */
    private JRadioButton rbStatusOpen;
    private JRadioButton rbStatusSolved;
    private JRadioButton rbStatusInvalid;
    private ButtonGroup btnGroupStatus;
    private JCheckBox cbbConfidenceC1;
    private JCheckBox cbbConfidenceC2;
    private JCheckBox cbbConfidenceC3;


    /**
     * Builds a new filter panel.
     */
    FilterPanel() {
        super(new GridBagLayout());

        final SearchFilter filter = PreferenceManager.getInstance().loadSearchFilter();
        addStatusFilter(filter.getStatus());
        addConfidenceFilter(filter.getConfidenceLevels());
    }

    private void addStatusFilter(final Status status) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getDlgFilterStatusLbl(), BOLD_12, null),
                Constraints.LBL_STATUS);
        rbStatusOpen =
                GuiBuilder.buildRadioButton(Status.OPEN.name().toLowerCase(), Status.OPEN.toString(), getBackground());
        rbStatusSolved = GuiBuilder.buildRadioButton(Status.SOLVED.name().toLowerCase(), Status.SOLVED.toString(),
                getBackground());
        rbStatusInvalid = GuiBuilder.buildRadioButton(Status.INVALID.name().toLowerCase(), Status.INVALID.toString(),
                getBackground());
        btnGroupStatus = GuiBuilder.buildButtonGroup(rbStatusOpen, rbStatusSolved, rbStatusInvalid);
        selectStatus(status);
        add(rbStatusOpen, Constraints.RB_OPEN);
        add(rbStatusSolved, Constraints.RB_SOLVED);
        add(rbStatusInvalid, Constraints.RB_INVALID);
    }

    private void addConfidenceFilter(final List<ConfidenceLevel> confidenceLevels) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getDlgFilterConfidenceLbl(), BOLD_12, null),
                Constraints.LBL_CONFIDENCE);
        cbbConfidenceC1 = GuiBuilder.buildCheckBox(ConfidenceLevel.C1.shortDisplayName(), ConfidenceLevel.C1.name(),
                getBackground());
        cbbConfidenceC2 = GuiBuilder.buildCheckBox(ConfidenceLevel.C2.shortDisplayName(), ConfidenceLevel.C2.name(),
                getBackground());
        cbbConfidenceC3 = GuiBuilder.buildCheckBox(ConfidenceLevel.C3.shortDisplayName(), ConfidenceLevel.C3.name(),
                getBackground());
        selectConfidence(confidenceLevels);
        add(cbbConfidenceC1, Constraints.CBB_C1);
        add(cbbConfidenceC2, Constraints.CBB_C2);
        add(cbbConfidenceC3, Constraints.CBB_C3);
    }

    /**
     * Returns the selected filters.
     *
     * @return a {@code SearchFilter} object
     */
    SearchFilter selectedFilters() {
        Status status = null;
        if (btnGroupStatus.getSelection() != null) {
            status = Status.valueOf(btnGroupStatus.getSelection().getActionCommand());
        }
        final List<ConfidenceLevel> confidenceLevels = selectedConfidenceLevels();
        return status == null && confidenceLevels.isEmpty() ? null : new SearchFilter(status, confidenceLevels);
    }

    /**
     * Resets the search filters to the default ones.
     */
    void resetFilters() {
        selectStatus(SearchFilter.DEFAULT.getStatus());
        selectConfidence(SearchFilter.DEFAULT.getConfidenceLevels());
    }

    private List<ConfidenceLevel> selectedConfidenceLevels() {
        final List<ConfidenceLevel> types = new ArrayList<>();
        if (cbbConfidenceC1.isSelected()) {
            types.add(ConfidenceLevel.C1);
        }
        if (cbbConfidenceC2.isSelected()) {
            types.add(ConfidenceLevel.C2);
        }
        if (cbbConfidenceC3.isSelected()) {
            types.add(ConfidenceLevel.C3);
        }
        return types;
    }

    private void selectStatus(final Status status) {
        if (status != null) {
            switch (status) {
                case OPEN:
                    rbStatusOpen.setSelected(true);
                    break;
                case SOLVED:
                    rbStatusSolved.setSelected(true);
                    break;
                default:
                    rbStatusInvalid.setSelected(true);
                    break;
            }
        } else {
            btnGroupStatus.clearSelection();
        }
    }

    private void selectConfidence(final List<ConfidenceLevel> confidenceLevels) {
        if (confidenceLevels != null) {
            boolean selected = confidenceLevels.contains(ConfidenceLevel.C1);
            cbbConfidenceC1.setSelected(selected);
            selected = confidenceLevels.contains(ConfidenceLevel.C2);
            cbbConfidenceC2.setSelected(selected);
            selected = confidenceLevels.contains(ConfidenceLevel.C3);
            cbbConfidenceC3.setSelected(selected);
        } else {
            cbbConfidenceC1.setSelected(false);
            cbbConfidenceC2.setSelected(false);
            cbbConfidenceC3.setSelected(false);
        }
    }
}