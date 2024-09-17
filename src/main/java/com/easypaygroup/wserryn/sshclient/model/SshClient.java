package com.easypaygroup.wserryn.sshclient.model;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshClient {
    private SshCredential sshCredential;
    private Session session = null;
    private Deque<String> commands = null;

    public SshClient() {

    }

    // must not be new openssh.
    // solve issue with ssh-keygen -p -f id_rsa -m pem -P "" -N ""
    public void createSession(SshCredential sshCredential) throws JSchException {
        this.sshCredential = sshCredential;
        JSch jsch = new JSch();
        jsch.setKnownHosts(sshCredential.getKnownHostsFile());
        jsch.addIdentity(sshCredential.getKeyFile());
        this.session = jsch.getSession(sshCredential.getUser(), sshCredential.getHost(), sshCredential.getPort());
        session.setConfig("StrictHostKeyChecking", "yes");
        session.setConfig("HashKnownHosts", "yes");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.connect();
        createChannel();
    }

    public void addCommand(String command) {
        getCommands().offer(command);
    }

    private Deque<String> getCommands() {
        if (this.commands == null)
            this.commands = new ArrayDeque<>();
        return this.commands;
    }

    public String executeNext() throws Exception {
        return executeCommand(createChannel());
    }

    public Deque<String> executeAll() throws Exception {
        Channel channel = createChannel();
        Deque<String> results = new ArrayDeque<>();
        while (!commands.isEmpty())
            results.offer(executeCommand(channel));
        return results;
    }

    private Channel createChannel() throws JSchException {
        return this.session.openChannel("shell");
    }

    private String executeCommand(Channel channel) throws Exception {
        // Channel channel = this.createChannel();
        String command = commands.poll();
        ((ChannelShell) channel).sendSignal(command);
        ;
        channel.setInputStream(null);
        InputStream in = null;
        in = channel.getInputStream();

        // TODO Auto-generated catch block

        StringBuilder out = new StringBuilder();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                // responseStream.write(tmp);

                out.append(new String(tmp, 0, i));
                // System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                System.out.println(command + " exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        if (channel != null)
            channel.disconnect();
        return out.toString();
    }

    public void close() {

        if (session != null)
            session.disconnect();
    }
}
