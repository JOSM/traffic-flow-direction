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

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.gui.GuiBuilder;


/**
 *
 * @author Beata
 * @version $Revision: 36 $
 */
class CommentsPanel extends BasicPanel<List<Comment>> {

    private static final long serialVersionUID = 4711808162627604965L;


    CommentsPanel() {
        setName(getGuiCnf().getPnlHistoryTitle());
    }

    @Override
    void createComponents(final List<Comment> comments) {
        setLayout(new BorderLayout());
        final String txt = Formatter.formatComments(comments);
        final JTextPane txtPane = GuiBuilder.buildTextPane(txt, null);
        final JScrollPane cmp =
                GuiBuilder.buildScrollPane(getGuiCnf().getPnlHistoryTitle(), txtPane, getBackground(), null);
        add(cmp, BorderLayout.CENTER);

    }

}