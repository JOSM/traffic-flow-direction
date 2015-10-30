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
package org.openstreetmap.josm.plugins.directionofflow.gui.details;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.directionofflow.observer.CommentObserver;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.IconConfig;
import org.openstreetmap.josm.tools.Shortcut;


/**
 * Defines the right side dialog window.
 *
 * @author Beata
 * @version $Revision: 51 $
 */
public class DirectionOfFlowDetailsDialog extends ToggleDialog {

    private static final long serialVersionUID = 6272681385708084893L;

    /** the preferred dimension of the panel components */
    private static final Dimension DIM = new Dimension(150, 100);


    private static final int DLG_HEIGHT = 50;
    private static Shortcut shortcut = Shortcut.registerShortcut(GuiConfig.getInstance().getPluginName(),
            GuiConfig.getInstance().getPluginTlt(), KeyEvent.VK_F3, Shortcut.CTRL);

    private final InfoPanel pnlInfo;
    private final CommentsPanel pnlComments;
    private final ButtonPanel pnlBtn;


    /**
     * Builds a new direction of flow details dialog window.
     */
    public DirectionOfFlowDetailsDialog() {
        super(GuiConfig.getInstance().getPluginName(), IconConfig.getInstance().getShortcutIconName(),
                GuiConfig.getInstance().getPluginTlt(), shortcut, DLG_HEIGHT);

        /* build components */
        pnlInfo = new InfoPanel();
        final JScrollPane cmpInfo =
                GuiBuilder.buildScrollPane(GuiConfig.getInstance().getPnlInfoTitle(), pnlInfo, getBackground(), DIM);
        pnlComments = new CommentsPanel();
        final JTabbedPane pnlDetails = GuiBuilder.buildTabbedPane(cmpInfo, pnlComments, new FeedbackPanel());
        pnlBtn = new ButtonPanel();
        final JPanel pnlMain = GuiBuilder.buildBorderLayoutPanel(pnlDetails, pnlBtn);
        add(pnlMain);
    }

    /**
     * Registers the given observer to the corresponding components.
     *
     * @param commentObserver a {@code CommentObserver}
     */
    public void registerCommentObserver(final CommentObserver commentObserver) {
        pnlBtn.registerCommentObserver(commentObserver);
    }

    /**
     * Updates the UI with the given road segment.
     *
     * @param roadSegment a {@code RoadSegment}.
     */
    public void updateUI(final RoadSegment roadSegment) {
        synchronized (this) {
            pnlInfo.updateData(roadSegment);
            pnlBtn.setRoadSegment(roadSegment);
            repaint();
        }
    }

    /**
     * Updates the UI with the given road segment and comment list.
     *
     * @param roadSegment a {@code RoadSegment}
     * @param comments a list of {@code Comment}s
     */
    public void updateUI(final RoadSegment roadSegment, final List<Comment> comments) {
        synchronized (this) {
            pnlInfo.updateData(roadSegment);
            pnlComments.updateData(comments);
            pnlBtn.setRoadSegment(roadSegment);
            repaint();
        }
    }
}