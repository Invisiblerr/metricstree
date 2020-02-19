package org.jacoquev.model.metric;

import java.util.Set;

public class Sets {
    private Sets(){}
    //    Chidamber-Kemerer metrics set includes:
    //    WMC: Weighted methods per class
    //    DIT: Depth of Inheritance Tree
    //    NOC: Number of Children
    //    CBO: Coupling between object classes
    //    RFC: Response for a Class
    //    LCOM: Lack of cohesion in methods
    private static final Set<String> chidamberKemererMetricsSet = Set.of("WMC", "DIT", "NOC", "CBO", "RFC", "LCOM");
    public static final boolean inChidamberKemererMetricsSet(String metricName) {
        return chidamberKemererMetricsSet.contains(metricName);
    }

    //    Lorenz-Kidd metrics set includes:
    //    NOA: Number of Attributes
    //    NOO: Number of Operations
    //    NOAM: Number of Added Methods
    //    NOOM: Number of Overridden Methods
    private static final Set<String> lorenzKiddMetricsSet = Set.of("NOA", "NOO", "NOAM", "NOOM");
    public static final boolean inLorenzKiddMetricsSet(String metricName) {
        return lorenzKiddMetricsSet.contains(metricName);
    }
    //    Robert C. Martin metrics set includes:
    //    Ce: Efferent Coupling
    //    Ca: Afferent Coupling
    //    I: Instability
    //    A: Abstractness
    //    D: Normalized Distance from Main Sequence
    private static final Set<String> robertMartinMetricsSet = Set.of("Ce", "Ca", "I", "A", "D");
    public static final boolean inRobertMartinMetricsSet(String metricName) {
        return robertMartinMetricsSet.contains(metricName);
    }

    //    MOOD metrics set includes:
    //    MHF: Method Hiding Factor
    //    AHF: Attribute Hiding Factor
    //    MIF: Method Inheritance Factor
    //    AIF: Attribute Inheritance Factor
    //    PF: Polymorphism Factor
    //    CF: Coupling Factor
    private static final Set<String> moodMetricsSet = Set.of("MHF", "AHF", "MIF", "AIF", "PF", "CF");
    public static final boolean inMoodMetricsSet(String metricName) {
        return moodMetricsSet.contains(metricName);
    }
}
