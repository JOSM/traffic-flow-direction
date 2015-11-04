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
package org.openstreetmap.josm.plugins.directionofflow;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Preferences.PreferenceChangeEvent;
import org.openstreetmap.josm.data.Preferences.PreferenceChangedListener;
import org.openstreetmap.josm.gui.IconToggleButton;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.MapView.LayerChangeListener;
import org.openstreetmap.josm.gui.NavigatableComponent;
import org.openstreetmap.josm.gui.NavigatableComponent.ZoomChangeListener;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.directionofflow.argument.BoundingBox;
import org.openstreetmap.josm.plugins.directionofflow.argument.SearchFilter;
import org.openstreetmap.josm.plugins.directionofflow.entity.Comment;
import org.openstreetmap.josm.plugins.directionofflow.entity.DataSet;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.entity.Status;
import org.openstreetmap.josm.plugins.directionofflow.gui.details.DirectionOfFlowDetailsDialog;
import org.openstreetmap.josm.plugins.directionofflow.gui.layer.DirectionOfFlowLayer;
import org.openstreetmap.josm.plugins.directionofflow.observer.CommentObserver;
import org.openstreetmap.josm.plugins.directionofflow.util.TipDialog;
import org.openstreetmap.josm.plugins.directionofflow.util.Util;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.Config;
import org.openstreetmap.josm.plugins.directionofflow.util.pref.Keys;
import org.openstreetmap.josm.plugins.directionofflow.util.pref.PreferenceManager;


/**
 * Defines the main functionality of the direction of flow plugin.
 *
 * @author Beata
 * @version $Revision: 47 $
 */
public class DirectionOfFlowPlugin extends Plugin
implements LayerChangeListener, ZoomChangeListener, MouseListener, PreferenceChangedListener, CommentObserver {

    private DirectionOfFlowLayer layer;
    private DirectionOfFlowDetailsDialog dialog;
    private Timer zoomTimer;


    /**
     * Builds a new direction of flow plugin. This constructor is automatically invoked by JOSM to bootstrap the plugin.
     *
     * @param pluginInfo the {@code PluginInfo} object
     */
    public DirectionOfFlowPlugin(final PluginInformation pluginInfo) {
        super(pluginInfo);
    }


    @Override
    public void mapFrameInitialized(final MapFrame oldMapFrame, final MapFrame newMapFrame) {
        if (Main.map != null) {
            if (!GraphicsEnvironment.isHeadless()) {
                dialog = new DirectionOfFlowDetailsDialog();
                newMapFrame.addToggleDialog(dialog);
                dialog.getButton().addActionListener(new ToggleButtonActionListener());
            }
            registerListeners();
            addLayer();
        }
    }


    /* LaryerChangeListener methods */
    @Override
    public void activeLayerChange(final Layer layer1, final Layer layer2) {
        // nothing to add here
    }

    @Override
    public void layerAdded(final Layer newLayer) {
        if (newLayer instanceof DirectionOfFlowLayer) {
            zoomChanged();
        }
    }

    @Override
    public void layerRemoved(final Layer currentLayer) {
        if (currentLayer instanceof DirectionOfFlowLayer) {
            MapView.removeLayerChangeListener(this);
            NavigatableComponent.removeZoomChangeListener(this);
            Main.map.mapView.removeMouseListener(this);
            Main.pref.removePreferenceChangeListener(this);
            PreferenceManager.getInstance().saveErrorSuppressFlag(false);

            // remove toggle action
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (Main.map != null) {
                        Main.map.remove(dialog);
                    }
                    dialog.getButton().setSelected(false);
                    dialog.setVisible(false);
                    dialog.updateUI(null, null);
                    dialog.destroy();
                    layer = null;
                }
            });

        }
    }


    /* ZoomChangeListener method */
    @Override
    public void zoomChanged() {
        if (layer != null && layer.isVisible()) {
            if (zoomTimer != null && zoomTimer.isRunning()) {
                // if timer is running restart it
                zoomTimer.restart();
            } else {
                zoomTimer = new Timer(Config.getInstance().getSearchDelay(), new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent event) {
                        Main.worker.execute(new DataUpdateThread());
                    }
                });
                zoomTimer.setRepeats(false);
                zoomTimer.start();
            }
        }
    }


    /* MouseListener implementation */

    @Override
    public void mouseClicked(final MouseEvent event) {
        final int zoom = Util.zoom(Main.map.mapView.getRealBounds());
        if ((zoom > Config.getInstance().getMaxClusterZoom())
                && (Main.map.mapView.getActiveLayer() == layer && layer.isVisible())
                && SwingUtilities.isLeftMouseButton(event)) {
            final boolean multiSelect = event.isShiftDown();
            final RoadSegment selectedRoadSegment = layer.lastSelectedRoadSegment();
            final RoadSegment roadSegment = layer.nearbyRoadSegment(event.getPoint(), multiSelect);
            if (roadSegment != null) {
                if (!roadSegment.equals(selectedRoadSegment)) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            retrieveComments(roadSegment);
                        }
                    });
                }
            } else if (!multiSelect) {
                // clear selection
                updateSelectedData(null, null);
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        // no logic for this action
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        // no logic for this action
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
        // no logic for this action
    }

    @Override
    public void mouseExited(final MouseEvent event) {
        // no logic for this action
    }


    /* PreferenceChangeListener method */
    @Override
    public void preferenceChanged(final PreferenceChangeEvent event) {
        if (event != null && (event.getNewValue() != null && !event.getNewValue().equals(event.getOldValue()))) {
            if (event.getKey().equals(Keys.FILTERS_CHANGED)) {
                Main.worker.execute(new DataUpdateThread());
            }
        }
    }


    /* CommentObserver method */
    @Override
    public void createComment(final Comment comment) {
        final List<RoadSegment> selectedRoadSegments = layer.getSelectedRoadSegments();
        if (!selectedRoadSegments.isEmpty()) {
            Main.worker.execute(new Runnable() {

                @Override
                public void run() {
                    ServiceHandler.getInstance().comment(comment, selectedRoadSegments);
                    // reload data
                    if (comment.getStatus() != null) {
                        Main.worker.execute(new DataUpdateThread());
                    }
                    final Status statusFilter = PreferenceManager.getInstance().loadSearchFilter().getStatus();
                    if (comment.getStatus() == null || statusFilter == null || (comment.getStatus() == statusFilter)) {
                        retrieveComments(layer.lastSelectedRoadSegment());
                    } else {
                        updateSelectedData(null, null);
                    }
                }
            });
        }
    }


    /* commonly used private methods and classes */

    private void addLayer() {
        layer = new DirectionOfFlowLayer();
        Main.main.addLayer(layer);
    }

    private void registerListeners() {
        NavigatableComponent.addZoomChangeListener(this);
        MapView.addLayerChangeListener(this);
        if (Main.isDisplayingMapView()) {
            Main.map.mapView.addMouseListener(this);
        }
        Main.pref.addPreferenceChangeListener(this);
        if (dialog != null) {
            dialog.registerCommentObserver(this);
        }
    }

    private void retrieveComments(final RoadSegment roadSegment) {
        if (roadSegment != null) {
            final List<Comment> comments = ServiceHandler.getInstance().retrieveComments(roadSegment);
            updateSelectedData(roadSegment, comments);
        }
    }

    private void updateSelectedData(final RoadSegment roadSegment, final List<Comment> comments) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                dialog.updateUI(roadSegment, comments);
                layer.updateSelectedRoadSegment(roadSegment);
                Main.map.repaint();
            }
        });
    }

    /*
     * Enables/disables the left side MissingGeometry window. Also adds the layer if was not already added.
     */
    private class ToggleButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() instanceof IconToggleButton) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        final IconToggleButton btn = (IconToggleButton) event.getSource();
                        if (btn.isSelected()) {
                            dialog.setVisible(true);
                            btn.setSelected(true);
                        } else {
                            dialog.setVisible(false);
                            btn.setSelected(false);
                            btn.setFocusable(false);
                        }
                        if (layer == null) {
                            addLayer();
                            registerListeners();
                        }
                    }
                });
            }

        }
    }

    /* updates the data from the current view */
    private class DataUpdateThread implements Runnable {

        @Override
        public void run() {
            if (Main.isDisplayingMapView()) {
                final BoundingBox bbox = new BoundingBox(Main.map.mapView);
                if (bbox != null) {
                    final int zoom = Util.zoom(Main.map.mapView.getRealBounds());
                    final SearchFilter filter = PreferenceManager.getInstance().loadSearchFilter();
                    final DataSet result = ServiceHandler.getInstance().search(bbox, filter, zoom);
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            if (!GraphicsEnvironment.isHeadless()) {
                                new TipDialog().displayDialog(zoom);
                            }
                            layer.setDataSet(result);
                            updateSelection(result);
                            Main.map.repaint();
                        }
                    });

                }
            }
        }

        private void updateSelection(final DataSet result) {
            final RoadSegment roadSegment = layer.lastSelectedRoadSegment();
            if (result != null && !GraphicsEnvironment.isHeadless()) {
                if (!result.getClusters().isEmpty()) {
                    // clear segment details
                    dialog.updateUI(null, null);
                } else if (result.getRoadSegments().contains(roadSegment)) {
                    dialog.updateUI(roadSegment);
                } else {
                    dialog.updateUI(null, null);
                }
            }
        }
    }
}
