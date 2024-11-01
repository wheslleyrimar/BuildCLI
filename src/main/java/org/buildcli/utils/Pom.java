package org.buildcli.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Pom {

    private final List<Dependency> dependencies = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Pom.class.getName());

    public void addDependency(String dependency) {
        String[] parts = dependency.split(":");

        switch (parts.length) {
            case 2:
                addDependency(parts[0], parts[1]);
                break;
            case 3:
                addDependency(parts[0], parts[1], parts[2]);
                break;
            default:
                logger.warning("Invalid dependency format. Use 'groupId:artifactId'" +
                        "or 'groupId:artifactId:version'.");
        }
    }

    void addDependency(String groupId, String artifactId, String version) {
        dependencies.stream()
                .filter(d -> d.getArtifactId()
                        .equals(artifactId) && d.getGroupId().equals(groupId))
                .findFirst()
                .ifPresentOrElse(f -> f.setVersion(version),
                        () -> dependencies.add(new Dependency(groupId, artifactId, version)));
    }

    void addDependency(String groupId, String artifactId) {
        addDependency(groupId, artifactId, "LATEST");
    }

    void addDependencyFile(String groupId, String artifactId, String version, String type, String scope, String optional) {
        dependencies.add(new Dependency(groupId, artifactId, version, type, scope, optional));
    }

    public void rmDependency(String dependency) {
        String[] parts = dependency.split(":");
        switch (parts.length) {
            case 2,3:
                rmDependency(parts[0], parts[1]);
                break;
            default:
                logger.warning("Invalid dependency format. Use 'groupId:artifactId'" +
                        "or 'groupId:artifactId:version'.");
        }
    }

    public void rmDependency(String groupId, String artifactId) {
        dependencies.remove(dependencies.stream()
                .filter(d -> d.getArtifactId().equals(artifactId) && d.getGroupId().equals(groupId))
                .findFirst().orElse(null));
    }

    public String getDependencyFormatted(){
        StringBuilder result = new StringBuilder();
        result.append("""
                                <dependencies>
                            """);
        for (Dependency dependency : dependencies) {
            result.append( """
                            <!-- Added by BuildCLI-->
                            <dependency>
                                <groupId>%s</groupId>
                                <artifactId>%s</artifactId>
                                <version>%s</version>
                    """.formatted(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
            if(dependency.getType() != null)
                result.append("""
                                    <type>%s</type>
                        """.formatted(dependency.getType()));
            if (dependency.getScope() != null)
                result.append("""
                                    <scope>%s</scope>
                        """.formatted(dependency.getScope()));
            if (dependency.getOptional() != null)
                result.append("""
                                    <optional>%s</optional>
                        """.formatted(dependency.getOptional()));
            result.append( """
                                    </dependency>
                            """);
        }
        result.append("""
                                </dependencies>\
                            """);
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        dependencies.forEach(dependency -> {sb.append(dependency.getGroupId())
                .append(":")
                .append(dependency.getArtifactId())
                .append(":")
                .append(dependency.getVersion())
                .append(",");});
        sb.append("]");
        return sb.toString();
    }
}

class Dependency{
    private String groupId;
    private String artifactId;
    private String version;
    private String type;
    private String scope;
    private String optional;

    public Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Dependency(String groupId, String artifactId, String version, String type, String scope, String optional) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.type = type;
        this.scope = scope;
        this.optional = optional;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }
}
