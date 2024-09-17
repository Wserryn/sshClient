package com.easypaygroup.wserryn.sshclient.model;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PropertyParser {
    public static SshCredential getCredential(String propertyKey) {
        JSONObject credentialObj = getCredentialJSONObject(propertyKey);
        String host = (String) credentialObj.get("host");
        int port = (int) (long) credentialObj.get("port");
        String user = (String) credentialObj.get("user");
        String keyFile = (String) credentialObj.get("keyFile");
        String rootFolder = (String) credentialObj.get("rootFolder");
        String knownHostsFile = (String) credentialObj.get("knownHostsFile");

        System.out.println("host: " + host);
        System.out.println("port: " + port);
        System.out.println("user: " + user);
        System.out.println("keyFile: " + keyFile);
        System.out.println("rootFolder: " + rootFolder);
        System.out.println("knownHostsFile: " + knownHostsFile);

        SshCredential sshCredential = new SshCredential(host, user);
        sshCredential.setPort(port);
        sshCredential.setKeyFile(keyFile);
        sshCredential.setRootFolder(rootFolder);
        sshCredential.setKnownHostsFile(knownHostsFile);
        return sshCredential;
    }

    private static JSONObject getCredentialJSONObject(String propertyKey) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("c:/dev/vscode_workspaces/properties/env.json"));
            JSONObject jsonObject = (JSONObject) obj;
            return (JSONObject) jsonObject.get(propertyKey);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
