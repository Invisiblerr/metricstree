package org.b333vv.metric.actions.treefilters.classtree;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import org.b333vv.metric.util.MetricsIcons;
import org.b333vv.metric.util.MetricsUtils;
import org.jetbrains.annotations.NotNull;

public class ShowClassMetrics extends ToggleAction {

    public ShowClassMetrics() {
        super("Show Class Metrics", "Show or dont show class metrics", MetricsIcons.CLASS_METRIC);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return MetricsUtils.getClassMetricsTreeFilter().isClassMetricsVisible();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        MetricsUtils.getClassMetricsTreeFilter().setClassMetricsVisible(state);
        MetricsUtils.getClassMetricsPanel().buildTreeModel();
    }
}