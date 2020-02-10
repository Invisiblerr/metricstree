package org.jacoquev.model.builder;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiPackage;
import org.jacoquev.model.code.JavaClass;
import org.jacoquev.model.code.JavaMethod;
import org.jacoquev.model.code.JavaPackage;
import org.jacoquev.model.code.JavaProject;
import org.jacoquev.model.metric.util.ClassUtils;
import org.jacoquev.model.visitor.type.CouplingBetweenObjectsVisitor;
import org.jacoquev.model.visitor.type.JavaClassVisitor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProjectModelBuilder extends ModelBuilder {

    private final JavaProject javaProject;

    public ProjectModelBuilder(JavaProject javaProject) {
        super();
        this.javaProject = javaProject;
    }

    private Set<JavaClassVisitor> deferredClassVisitors = Set.of(
            new CouplingBetweenObjectsVisitor()
    );

    public void addJavaFileToJavaProject(JavaProject javaProject, PsiJavaFile psiJavaFile) {
        JavaPackage javaPackage = findOrCreateJavaPackage(javaProject, psiJavaFile);
        createJavaClass(javaPackage, psiJavaFile);
    }

    private JavaPackage findOrCreateJavaPackage(JavaProject javaProject, PsiJavaFile psiJavaFile) {
        List<PsiPackage> packageList = ClassUtils.getPackagesRecursive(psiJavaFile);
        assert packageList != null;
        if (packageList.isEmpty()) {
            return null;
        }
        if (javaProject.getPackagesMap().isEmpty()) {
            Iterator<PsiPackage> psiPackageIterator = packageList.iterator();
            PsiPackage firstPsiPackage = psiPackageIterator.next();
            JavaPackage firstJavaPackage = new JavaPackage(firstPsiPackage.getName(), firstPsiPackage);
            javaProject.getPackagesMap().put(firstJavaPackage.getPsiPackage().getQualifiedName(), firstJavaPackage);
            javaProject.addPackage(firstJavaPackage);
            JavaPackage currentJavaPackage = firstJavaPackage;
            while (psiPackageIterator.hasNext()) {
                PsiPackage aPsiPackage = psiPackageIterator.next();
                JavaPackage aJavaPackage = new JavaPackage(aPsiPackage.getName(), aPsiPackage);
                javaProject.getPackagesMap().put(aJavaPackage.getPsiPackage().getQualifiedName(), aJavaPackage);
                currentJavaPackage.addPackage(aJavaPackage);
                currentJavaPackage = aJavaPackage;
            }
            return currentJavaPackage;
        } else {
            Collections.reverse(packageList);
            PsiPackage[] psiPackages = packageList.toArray(new PsiPackage[0]);
            int j = 0;
            JavaPackage aPackage = null;
            for (int i = 0; i < psiPackages.length; i++) {
                JavaPackage javaPackage = javaProject.getPackagesMap().get(psiPackages[i].getQualifiedName());
                if (javaPackage != null) {
                    aPackage = javaProject.getPackagesMap().get(psiPackages[i].getQualifiedName());
                    j = i;
                    break;
                }
            }
            for (int i = j - 1; i >= 0; i--) {
                JavaPackage newPackage = new JavaPackage(psiPackages[i].getName(), psiPackages[i]);
                javaProject.getPackagesMap().put(newPackage.getPsiPackage().getQualifiedName(), newPackage);
                aPackage.addPackage(newPackage);
                aPackage = newPackage;
            }
            return aPackage;
        }
    }

    @Override
    protected void addClassToClassesSet(JavaClass javaClass) {
        javaProject.addClassToClassesSet(javaClass);
    }

    @Override
    protected void addMethodToMethodsSet(JavaMethod javaMethod) {
        javaProject.addMethodToMethodsSet(javaMethod);
    }

    public void calculateMetrics() {
        javaProject.getAllClasses().forEach(c -> {
            deferredClassVisitors.stream().forEach(v -> c.accept(v));
        });
//        javaProject.getAllMethods().forEach(m -> {
//            methodVisitors.stream().forEach(v -> m.accept(v));
//        });
    }
}