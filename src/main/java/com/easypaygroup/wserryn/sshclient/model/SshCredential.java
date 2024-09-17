package com.easypaygroup.wserryn.sshclient.model;

public class SshCredential {
    private String host;
    private int port;
    private String user;
    private String keyFile;
    private String rootFolder;
    private String knownHostsFile;

    public SshCredential(String host, String user) {
        this.host = host;
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getKnownHostsFile() {
        return this.knownHostsFile;
    }

    public void setKnownHostsFile(String knownHostsfile) {
        this.knownHostsFile = knownHostsfile;
    }

}
