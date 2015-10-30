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
package org.openstreetmap.josm.plugins.directionofflow.gui.layer;


import static org.openstreetmap.josm.plugins.directionofflow.gui.layer.Constants.RENDERING_MAP;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.gui.dialogs.LayerListPopup;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.directionofflow.entity.DataSet;
import org.openstreetmap.josm.plugins.directionofflow.entity.RoadSegment;
import org.openstreetmap.josm.plugins.directionofflow.util.Util;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.Config;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.directionofflow.util.cnf.IconConfig;


/**
 * Defines the direction of flow layer.
 *
 * @author Beata
 * @version $Revision: 51 $
 */
public class DirectionOfFlowLayer extends Layer {

    private final PaintHandler paintHandler;

    private DataSet dataSet;
    private List<RoadSegment> selectedRoadSegments;


    /**
     * Builds a new direction of flow layer.
     */
    public DirectionOfFlowLayer() {
        super(GuiConfig.getInstance().getPluginName());
        this.paintHandler = new PaintHandler();
        this.selectedRoadSegments = new ArrayList<>();
    }


    @Override
    public Icon getIcon() {
        return IconConfig.getInstance().getLayerIcon();
    }

    @Override
    public Object getInfoComponent() {
        return GuiConfig.getInstance().getPluginTlt();
    }

    @Override
    public Action[] getMenuEntries() {
        final LayerListDialog layerListDialog = LayerListDialog.getInstance();
        return new Action[] { layerListDialog.createActivateLayerAction(this),
                layerListDialog.createShowHideLayerAction(), layerListDialog.createDeleteLayerAction(),
                SeparatorLayerAction.INSTANCE, new LayerListPopup.InfoAction(this) };
    }

    @Override
    public String getToolTipText() {
        return GuiConfig.getInstance().getPluginTlt();
    }

    @Override
    public boolean isMergable(final Layer layer) {
        return false;
    }

    @Override
    public void paint(final Graphics2D graphics, final MapView mapView, final Bounds bounds) {
        mapView.setDoubleBuffered(true);
        graphics.setRenderingHints(RENDERING_MAP);
        if (dataSet != null) {
            final int zoom = Util.zoom(bounds);
            if (zoom > Config.getInstance().getMaxClusterZoom()) {
                // display segments
                if (dataSet.getRoadSegments() != null && !dataSet.getRoadSegments().isEmpty()) {
                    paintHandler.drawRoadSegments(graphics, mapView, dataSet.getRoadSegments(), selectedRoadSegments);
                }
            } else {
                // display clusters
                if (dataSet.getClusters() != null && !dataSet.getClusters().isEmpty()) {
                    paintHandler.drawClusters(graphics, mapView, zoom, dataSet.getClusters());
                }
            }
        }
    }

    @Override
    public void mergeFrom(final Layer layer) {
        // this operation is not supported
    }

    @Override
    public void visitBoundingBox(final BoundingXYVisitor arg0) {
        // this operation is not supported
    }

    /**
     * Sets the layer's data set. Previously road segments will be unselected if the new data set does not contains
     * these elements.
     *
     * @param dataSeta {@code DataSet} containing the road segments/clusters from the current view
     */
    public void setDataSet(final DataSet dataSet) {
        this.dataSet = dataSet;
        if (!selectedRoadSegments.isEmpty() && !this.dataSet.getRoadSegments().isEmpty()) {
            final List<RoadSegment> newList = new ArrayList<>();
            for (final RoadSegment segment : this.selectedRoadSegments) {
                if (this.dataSet.getRoadSegments().contains(segment)) {
                    final int idx = this.dataSet.getRoadSegments().indexOf(segment);
                    final RoadSegment newSegent = this.dataSet.getRoadSegments().get(idx);
                    newList.add(newSegent);
                }
            }
            this.selectedRoadSegments = newList;
        }
    }

    /**
     * Updates the selected road segments.
     *
     * @param roadSegment a {@code RoadSegment} representing the selected object
     */
    public void updateSelectedRoadSegment(final RoadSegment roadSegment) {
        if (roadSegment == null) {
            selectedRoadSegments.clear();
        } else {
            final int idx = selectedRoadSegments.indexOf(roadSegment);
            if (idx > -1) {
                selectedRoadSegments.remove(roadSegment);
                selectedRoadSegments.add(idx, roadSegment);
            } else {
                selectedRoadSegments.add(roadSegment);
            }
        }
    }

    /**
     * Returns the road segment near the given point. The method returns null if there is no nearby road segment.
     * 
     * @param point a {@code Point} represents the location where the user clicked
     * @param multiSelect specifies if multiple elements are selected or not
     * @return a {@code RoadSegment} object
     */
    public RoadSegment nearbyRoadSegment(final Point point, final boolean multiSelect) {
        final RoadSegment roadSegment =
                dataSet != null ? Util.nearbyRoadSegment(dataSet.getRoadSegments(), point) : null;
        if (!multiSelect) {
            selectedRoadSegments.clear();
        }
        return roadSegment;
    }

    /**
     * Returns the last selected road segment. If no road segment
     *
     * @return
     */
    public RoadSegment lastSelectedRoadSegment() {
        return selectedRoadSegments.isEmpty() ? null : selectedRoadSegments.get(selectedRoadSegments.size() - 1);
    }

    /**
     * Returns the selected road segments. If no road segment(s) is selected the method returns an empty list.
     *
     * @return a list of {@code RoadSegment}s
     */
    public List<RoadSegment> getSelectedRoadSegments() {
        return selectedRoadSegments;
    }
}