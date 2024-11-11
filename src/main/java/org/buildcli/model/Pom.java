package org.buildcli.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pom {
	
	private static final Logger logger = Logger.getLogger(Pom.class.getName());

	@XmlElementWrapper
	@XmlElement(name = "dependency")
    private final List<Dependency> dependencies;
    
    public Pom() {
    	this.dependencies = new ArrayList<>();
    }

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

    public void addDependency(String groupId, String artifactId, String version) {
        dependencies.stream()
                .filter(d -> d.getArtifactId()
                        .equals(artifactId) && d.getGroupId().equals(groupId))
                .findFirst()
                .ifPresentOrElse(f -> f.setVersion(version),
                        () -> dependencies.add(new Dependency(groupId, artifactId, version)));
    }

    public void addDependency(String groupId, String artifactId) {
        addDependency(groupId, artifactId, "LATEST");
    }

    public void addDependencyFile(String groupId, String artifactId, String version, String type, String scope, String optional) {
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
        	
        	if (Objects.isNull(dependency.getVersion()) || dependency.getVersion().isBlank()) {
        		result.append( """
                        <!-- Added by BuildCLI-->
                        <dependency>
                            <groupId>%s</groupId>
                            <artifactId>%s</artifactId>
                """.formatted(dependency.getGroupId(), dependency.getArtifactId()));
        	} else {
        		result.append( """
                            <!-- Added by BuildCLI-->
                            <dependency>
                                <groupId>%s</groupId>
                                <artifactId>%s</artifactId>
                                <version>%s</version>
                    """.formatted(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
        	}
            
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

    public boolean hasDependency(String groupId, String artifactId) {
    	return this.dependencies.stream()
    			.anyMatch(d -> d.getGroupId().equals(groupId) && d.getArtifactId().equals(artifactId));
    }
    
    public int countDependencies() {
    	return this.dependencies.size();
    }
    
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        dependencies.forEach(dependency -> sb.append(dependency.getGroupId())
                .append(":")
                .append(dependency.getArtifactId())
                .append(":")
                .append(dependency.getVersion())
                .append(","));
        sb.append("]");
        return sb.toString();
    }
}
