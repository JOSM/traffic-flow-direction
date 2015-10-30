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
 * Utility class, holds GUI texts.
 *
 * @author Beata
 * @version $Revision: 36 $
 */
public final class GuiConfig extends BaseConfig {

    private static final String CONFIG_FILE = "directionofflow_gui.properties";
    private static final GuiConfig INSTANCE = new GuiConfig();

    private final String pluginName;
    private final String pluginTlt;

    /* button labels */
    private final String btnOkLbl;
    private final String btnCancelLbl;
    private final String btnResetLbl;

    /* button tooltips */
    private final String btnFilterTlt;
    private final String btnLocationTlt;
    private final String btnCommentTlt;
    private final String btnSolveTlt;
    private final String btnReopenTlt;
    private final String btnInvalidTlt;

    /* edit dialog texts */
    private final String dlgCommentTitle;
    private final String dlgSolveTitle;
    private final String dlgReopenTitle;
    private final String dlgInvalidTitle;

    /* filter dialog texts */
    private final String dlgFilterTitle;
    private final String dlgFilterStatusLbl;
    private final String dlgFilterConfidenceLbl;

    /* info panel texts */
    private final String pnlInfoTitle;
    private final String lblTrips;
    private final String lblId;
    private final String lblType;
    private final String lblProcent;
    private final String lblStatus;
    private final String lblConfidence;

    /* history panel title */
    private final String pnlHistoryTitle;

    /* feedback panel title */
    private final String pnlFeedbackTitle;
    private final String pnlFeedbackTxt;

    /* commonly used labels */
    private final String lblC1;
    private final String lblC2;
    private final String lblC3;
    private final String lblOneway;
    private final String lblCopy;

    /* error texts */
    private final String errorTitle;
    private final String txtFeedbackUrl;

    /* warning texts */
    private final String warningTitle;
    private final String txtInvalidComment;
    private final String txtMissingUsername;

    /* tip info texts */
    private final String dlgTipTitle;
    private final String dlgTipText;

    private GuiConfig() {
        super(CONFIG_FILE);

        pluginName = readProperty("plugin.name");
        pluginTlt = readProperty("plugin.tlt");

        btnOkLbl = readProperty("btn.ok.lbl");
        btnCancelLbl = readProperty("btn.cancel.lbl");
        btnResetLbl = readProperty("btn.reset.lbl");

        btnFilterTlt = readProperty("btn.filtet.tlt");
        btnLocationTlt = readProperty("btn.location.tlt");
        btnCommentTlt = readProperty("btn.comment.tlt");
        btnSolveTlt = readProperty("btn.solve.tlt");
        btnReopenTlt = readProperty("btn.reopen.tlt");
        btnInvalidTlt = readProperty("btn.invalid.tlt");

        dlgCommentTitle = readProperty("edit.dialog.comment.title");
        dlgSolveTitle = readProperty("edit.dialog.solve.title");
        dlgReopenTitle = readProperty("edit.dialog.reopen.title");
        dlgInvalidTitle = readProperty("edit.dialog.invalid.title");

        dlgFilterTitle = readProperty("dialog.filter.title");
        dlgFilterStatusLbl = readProperty("dialog.filter.status.lbl");
        dlgFilterConfidenceLbl = readProperty("dialog.filter.confidence.lbl");

        pnlInfoTitle = readProperty("details.info.title");
        lblTrips = readProperty("details.info.trips.lbl");
        lblId = readProperty("details.info.id.lbl");
        lblType = readProperty("details.info.type.lbl");
        lblProcent = readProperty("details.info.procent.lbl");
        lblStatus = readProperty("status.lbl");
        lblConfidence = readProperty("confidence.lbl");

        pnlHistoryTitle = readProperty("details.history.title");
        pnlFeedbackTitle = readProperty("details.feedback.title");
        pnlFeedbackTxt = readProperty("details.feedback.txt");

        errorTitle = readProperty("error.title");
        txtFeedbackUrl = readProperty("error.feedback");

        lblC1 = readProperty("c1.lbl");
        lblC2 = readProperty("c2.lbl");
        lblC3 = readProperty("c3.lbl");
        lblOneway = readProperty("oneway.lbl");
        lblCopy = readProperty("copy.lbl");

        warningTitle = readProperty("warning.title");
        txtInvalidComment = readProperty("warning.invalid.comment");
        txtMissingUsername = readProperty("warning.missing.username");
        dlgTipTitle = readProperty("dialog.tip.title");
        dlgTipText = readProperty("dialog.tip.text");
    }


    public static GuiConfig getInstance() {
        return INSTANCE;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginTlt() {
        return pluginTlt;
    }


    public String getBtnOkLbl() {
        return btnOkLbl;
    }


    public String getBtnCancelLbl() {
        return btnCancelLbl;
    }


    public String getBtnResetLbl() {
        return btnResetLbl;
    }


    public String getBtnFilterTlt() {
        return btnFilterTlt;
    }

    public String getBtnLocationTlt() {
        return btnLocationTlt;
    }

    public String getBtnCommentTlt() {
        return btnCommentTlt;
    }

    public String getBtnSolveTlt() {
        return btnSolveTlt;
    }

    public String getBtnReopenTlt() {
        return btnReopenTlt;
    }

    public String getBtnInvalidTlt() {
        return btnInvalidTlt;
    }

    public String getDlgFilterTitle() {
        return dlgFilterTitle;
    }

    public String getDlgFilterStatusLbl() {
        return dlgFilterStatusLbl;
    }

    public String getDlgFilterConfidenceLbl() {
        return dlgFilterConfidenceLbl;
    }

    public String getDlgCommentTitle() {
        return dlgCommentTitle;
    }

    public String getDlgSolveTitle() {
        return dlgSolveTitle;
    }

    public String getDlgReopenTitle() {
        return dlgReopenTitle;
    }

    public String getDlgInvalidTitle() {
        return dlgInvalidTitle;
    }

    public String getPnlInfoTitle() {
        return pnlInfoTitle;
    }

    public String getLblTrips() {
        return lblTrips;
    }

    public String getLblId() {
        return lblId;
    }

    public String getLblType() {
        return lblType;
    }

    public String getLblProcent() {
        return lblProcent;
    }

    public String getPnlHistoryTitle() {
        return pnlHistoryTitle;
    }

    public String getPnlFeedbackTitle() {
        return pnlFeedbackTitle;
    }

    public String getPnlFeedbackTxt() {
        return pnlFeedbackTxt;
    }

    public String getTxtFeedbackUrl() {
        return txtFeedbackUrl;
    }


    public String getLblStatus() {
        return lblStatus;
    }

    public String getLblConfidence() {
        return lblConfidence;
    }

    public String getLblC1() {
        return lblC1;
    }

    public String getLblC2() {
        return lblC2;
    }

    public String getLblC3() {
        return lblC3;
    }

    public String getLblOneway() {
        return lblOneway;
    }

    public String getLblCopy() {
        return lblCopy;
    }

    public String getWarningTitle() {
        return warningTitle;
    }

    public String getTxtInvalidComment() {
        return txtInvalidComment;
    }

    public String getTxtMissingUsername() {
        return txtMissingUsername;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getDlgTipTitle() {
        return dlgTipTitle;
    }

    public String getDlgTipText() {
        return dlgTipText;
    }
}