package com.jagex.game.runetek5;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.sign.SignLink;

import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class99_Sub1;
public class  ClientLoader extends Applet {

    public static String world;
    public static String serverIp = "127.0.0.1";
    public JFrame frame;
    public static final String version = "0.1_a1";
    private final JPanel jp = new JPanel();

    public static Properties aProperties1 = new Properties();
    public static void main(final String[] strings) {
        new ClientLoader("1");
    }

    public ClientLoader(final String string) {
        try {
            world = string;
            frame = new JFrame("open592 client - version: " + version);
            frame.setLayout(new BorderLayout());
            frame.setResizable(true);
            jp.setLayout(new BorderLayout());
            jp.add(this);
            jp.setPreferredSize(new Dimension(765, 503));
            frame.getContentPane().add(jp, "Center");
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            setParams();
            final SignLink signlink = new SignLink(this, 32, "runescape", 31);
            Class99_Sub1.aClass66_5 = Class126.aClass66_4 = signlink;
            final client var_client = new client();
            var_client.init();
            //Class246_Sub34.aBool5145 = true;// fps debug
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setParams() {
        aProperties1.put("java_arguments", "-Xmx512m -Dsun.java2d.noddraw=true");
        aProperties1.put("colourid", "0");
        aProperties1.put("worldid", world);
        aProperties1.put("modewhere", "0");
        aProperties1.put("modewhat", "0");
        aProperties1.put("lang", "0");
        aProperties1.put("objecttag", "0");
        aProperties1.put("js", "1");
        aProperties1.put("game", "0");
        aProperties1.put("affid", "0");
        aProperties1.put("advert", "1");
        aProperties1.put("settings", "wwGlrZHF5gJcZl7tf7KSRh0MZLhiU0gI0xDX6DwZ-Qk");
        aProperties1.put("country", "0");
        aProperties1.put("haveie6", "0");
        aProperties1.put("havefirefox", "1");
        aProperties1.put("cookieprefix", "");
        aProperties1.put("cookiehost", ".runescape.com");
        aProperties1.put("cachesubdirid", "0");
        aProperties1.put("crashurl", "");
        aProperties1.put("unsignedurl", "");
        aProperties1.put("sitesettings_member", "1");
        aProperties1.put("frombilling", "false");
        aProperties1.put("sskey", "");
        aProperties1.put("force64mb", "false");
        aProperties1.put("worldflags", "8");
    }

    @Override
    public String getParameter(final String string) {
        return (String) aProperties1.get(string);
    }

    @Override
    public URL getCodeBase() {
        URL url;
        try {
            url = new URL("http://" + serverIp);
        } catch (final MalformedURLException malformedurlexception) {
            malformedurlexception.printStackTrace();
            return null;
        }
        return url;
    }

    @Override
    public URL getDocumentBase() {
        return getCodeBase();
    }
}
