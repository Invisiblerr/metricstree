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

package org.b333vv.metric.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.DumbServiceImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.b333vv.metric.ui.log.MetricsConsole;
import org.b333vv.metric.ui.tool.ClassMetricsValuesEvolutionPanel;
import org.b333vv.metric.ui.tool.ClassMetricsPanel;
import org.b333vv.metric.ui.tool.ProjectMetricsPanel;
import org.b333vv.metric.ui.tree.MetricsTreeFilter;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

public class MetricsUtils {

    private static final Logger LOG = Logger.getInstance(MetricsUtils.class);
    private static Project project;
    private static MetricsTreeFilter classMetricsTreeFilter = new MetricsTreeFilter();
    private static MetricsTreeFilter projectMetricsTreeFilter = new MetricsTreeFilter();
    private static ClassMetricsPanel classMetricsPanel;
    private static ClassMetricsValuesEvolutionPanel classMetricsValuesEvolutionPanel;
    private static ProjectMetricsPanel projectMetricsPanel;
    private static boolean autoScrollable = true;
    private static boolean projectMetricsCalculationPerforming;
    private static boolean classMetricsValuesEvolutionCalculationPerforming;

    private MetricsUtils() {
        // Utility class
    }

    public static synchronized MetricsConsole getConsole() {
        return get(project, MetricsConsole.class);
    }

    public static boolean isProjectMetricsCalculationPerforming() {
        return projectMetricsCalculationPerforming;
    }

    public static void setProjectMetricsCalculationPerforming(boolean projectMetricsCalculationPerforming) {
        MetricsUtils.projectMetricsCalculationPerforming = projectMetricsCalculationPerforming;
    }

    public static void setClassMetricsValuesEvolutionCalculationPerforming(boolean classMetricsValuesEvolutionCalculationPerforming) {
        MetricsUtils.classMetricsValuesEvolutionCalculationPerforming = classMetricsValuesEvolutionCalculationPerforming;
    }

    public static <T> T get(ComponentManager container, Class<T> clazz) {
        T t = container.getComponent(clazz);
        if (t == null) {
            LOG.error("Could not find class in the container: " + clazz.getName());
            throw new IllegalArgumentException("Class not found: " + clazz.getName());
        }
        return t;
    }

    public static <T> T get(Class<T> clazz) {
        return get(ApplicationManager.getApplication(), clazz);
    }

    @CheckForNull
    public static VirtualFile getSelectedFile(Project project) {
        if (project.isDisposed()) {
            return null;
        }
        FileEditorManager editorManager = FileEditorManager.getInstance(project);
        Editor editor = editorManager.getSelectedTextEditor();
        if (editor != null) {
            Document doc = editor.getDocument();
            FileDocumentManager docManager = FileDocumentManager.getInstance();
            return docManager.getFile(doc);
        }
        return null;
    }

    public static Project getProject() {
        return project;
    }

    public static void setProject(Project value) {
        project = value;
    }

    public static DumbService getDumbService() {
        return DumbServiceImpl.getInstance(project);
    }

    public static MetricsTreeFilter getClassMetricsTreeFilter() {
        return classMetricsTreeFilter;
    }

    public static MetricsTreeFilter getProjectMetricsTreeFilter() {
        return projectMetricsTreeFilter;
    }

    public static ClassMetricsPanel getClassMetricsPanel() {
        return classMetricsPanel;
    }

    public static ClassMetricsValuesEvolutionPanel getClassMetricsValuesEvolutionPanel() {
        return classMetricsValuesEvolutionPanel;
    }

    public static void setClassMetricsPanel(ClassMetricsPanel value) {
        classMetricsPanel = value;
    }

    public static void setClassMetricsValuesEvolutionPanel(ClassMetricsValuesEvolutionPanel value) { classMetricsValuesEvolutionPanel = value; }

    public static void refreshMetricsTree() {
        classMetricsPanel.refresh();
    }

    public static void setProjectMetricsPanel(ProjectMetricsPanel value) {
        projectMetricsPanel = value;
    }

    public static ProjectMetricsPanel getProjectMetricsPanel() {
        return projectMetricsPanel;
    }

    public static void calculateProjectMetrics() {
        projectMetricsPanel.calculateMetrics();
    }

    public static void calculateClassMetricsEvolution() {
        classMetricsValuesEvolutionPanel.calculateMetricsEvolution();
    }

    public static boolean isAutoScrollable() {
        return autoScrollable;
    }

    public static void setAutoScrollable(boolean autoScrollable) {
        MetricsUtils.autoScrollable = autoScrollable;
    }

    public static boolean isClassMetricsTreeExists() {
        return classMetricsPanel != null && classMetricsPanel.isMetricsTreeExists();
    }

    public static boolean isProjectMetricsTreeExists() {
        return projectMetricsPanel != null && projectMetricsPanel.isMetricsTreeExists();
    }

    public static boolean isMetricsEvolutionCalculationPossible() {
        return classMetricsValuesEvolutionPanel != null
                && classMetricsValuesEvolutionPanel.isUnderGit()
                && !classMetricsValuesEvolutionCalculationPerforming;
    }

    @Nullable
    private static VirtualFile getVirtualFile(PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }
        final PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            return null;
        }
        return containingFile.getVirtualFile();
    }

    public static boolean isElementInSelectedFile(Project project,
                                                  PsiElement psiElement) {
        final VirtualFile elementFile = getVirtualFile(psiElement);
        if (elementFile == null) {
            return false;
        }
        final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        final VirtualFile[] currentEditedFiles = fileEditorManager.getSelectedFiles();

        for (final VirtualFile file : currentEditedFiles) {
            if (elementFile.equals(file)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Editor getEditorIfSelected(Project project, PsiElement psiElement) {
        final VirtualFile elementFile = getVirtualFile(psiElement);
        if (elementFile == null) {
            return null;
        }
        final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        final FileEditor fileEditor = fileEditorManager.getSelectedEditor(elementFile);
        Editor editor = null;
        if (fileEditor instanceof TextEditor) {
            editor = ((TextEditor) fileEditor).getEditor();
        }
        return editor;
    }
}
