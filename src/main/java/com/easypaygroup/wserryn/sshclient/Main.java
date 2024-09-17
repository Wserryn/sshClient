package com.easypaygroup.wserryn.sshclient;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.easypaygroup.wserryn.sshclient.model.PropertyParser;
import com.easypaygroup.wserryn.sshclient.model.SshClient;
import com.easypaygroup.wserryn.sshclient.model.SshCredential;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        SshCredential cred = new SshCredential("epgdev", "Wserryn");
        cred.setKeyFile("C:\\Users\\Wserryn\\.ssh\\id_rsa");
        cred.setKnownHostsFile("C:\\Users\\Wserryn\\.ssh\\known_hosts");

        SshClient client = new SshClient();
        client.createSession(cred);
        client.addCommand("ls -l");
        client.executeAll();
    }

}
