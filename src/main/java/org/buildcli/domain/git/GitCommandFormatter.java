package org.buildcli.domain.git;

public class GitCommandFormatter {

    protected static String getGitDir(String localRepository){
        return "--git-dir=" + localRepository +"/.git";
    }

    protected static String getWorkTree(String localRepository){
        return  "--work-tree=" +localRepository;
    }

    protected static String releaseVersion(String version){
        return "release/v" + version;
    }
}
