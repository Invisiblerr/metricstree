/*
 * Copyright 2020 b333vv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.b333vv.metric.ui.tree.node;

import com.intellij.icons.AllIcons;
import com.intellij.vcs.log.Hash;
import org.b333vv.metric.model.metric.Metric;
import org.b333vv.metric.model.metric.value.Value;
import org.b333vv.metric.ui.tree.TreeCellRenderer;
import org.b333vv.metric.util.MetricsIcons;

import javax.swing.*;

public class MetricHistoryNode extends MetricNode {

    private final String dateTime;
    private final String id;
    private final Value currentValue;
    private final Value previousValue;

    public MetricHistoryNode(String dateTime, String id, Metric metric, Value previousValue) {
        super(metric);
        this.dateTime = dateTime;
        this.id = id;
        this.currentValue = metric.getValue();
        this.previousValue = previousValue;
    }
    @Override
    public String getText() {
        String delta = "";
        if (previousValue != Value.UNDEFINED) {
            if (currentValue.isLessThan(previousValue)) {
                delta += " (-" + previousValue.minus(currentValue) + ")";
            }
            if (currentValue.isGreaterThan(previousValue)) {
                delta += " (+" + currentValue.minus(previousValue) + ")";
            }
        }
        return currentValue.toString() + delta + " [" + dateTime + ", " + id + "]";
    }

    @Override
    public Icon getIcon() {
        if (previousValue != Value.UNDEFINED) {
            if (previousValue.equals(currentValue)) {
                return MetricsIcons.EQUAL;
            } else if (currentValue.isLessThan(previousValue)) {
                return MetricsIcons.DECREASED;
            } else if (currentValue.isGreaterThan(previousValue)) {
                return MetricsIcons.INCREASED;
            }
        }
        return AllIcons.Nodes.EmptyNode;
    }
}
