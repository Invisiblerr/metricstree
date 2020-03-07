package org.b333vv.metric.actions.treefilters.classtree;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import org.b333vv.metric.util.MetricsUtils;
import org.jetbrains.annotations.NotNull;

public class ShowNotSetValueMetrics extends ToggleAction {


    public ShowNotSetValueMetrics() {
        super("Show Metrics Whose Valid Values Are Not Set",
                "Show or dont show metrics whose allowed values are not set",
                AllIcons.General.BalloonWarning);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return MetricsUtils.getClassMetricsTreeFilter().isNotSetValueMetricsVisible();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        MetricsUtils.getClassMetricsTreeFilter().setNotSetValueMetricsVisible(state);
        MetricsUtils.getClassMetricsPanel().buildTreeModel();
    }
}