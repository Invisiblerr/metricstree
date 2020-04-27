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

package org.b333vv.metric.exec;

import com.intellij.util.messages.Topic;
import org.b333vv.metric.ui.tree.builder.ProjectMetricTreeBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultTreeModel;

public interface MetricsEventListener {
    Topic<MetricsEventListener> TOPIC = new Topic<>("MetricsEventListener", MetricsEventListener.class);

    default void projectMetricsCalculated(ProjectMetricTreeBuilder projectMetricTreeBuilder, @NotNull DefaultTreeModel defaultTreeModel) {
    }

    default void classMetricsValuesEvolutionCalculated(@NotNull DefaultTreeModel defaultTreeModel) {
    }

    default void clearProjectMetricsTree() {
    }

    default void clearClassMetricsValuesEvolutionTree() {
    }

    default void buildClassMetricsTree() {
    }

    default void buildProjectMetricsTree() {
    }

    default void showClassMetricsTree(boolean showClassMetricsTree) {
    }

    default void refreshClassMetricsTree() {
    }

    default void cancelMetricsValuesEvolutionCalculation() {
    }
}
