package org.nico.ratel.landlords.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.handler.DefaultChannelInitializer;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {

	public static int id = -1;
	
	public static String serverAddress = "";
	
	public static int port = 0;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		if(args != null && args.length > 0) {
			for(int index = 0; index < args.length; index = index + 2) {
				if(index + 1 < args.length) {
					if(args[index].equalsIgnoreCase("-p") || args[index].equalsIgnoreCase("-port")) {
						port = Integer.valueOf(args[index + 1]);
					}
					if(args[index].equalsIgnoreCase("-h") || args[index].equalsIgnoreCase("-host")) {
						serverAddress = args[index + 1];
					}
				}
			}
		}
		
		if(serverAddress.equals("") || port == 0){
			StringBuffer buffer = new StringBuffer();
			try {
				URL url = new URL("https://raw.githubusercontent.com/abbychau/ratel/master/serverlist.json");
				URLConnection con = url.openConnection();
				con.setUseCaches(false);
				InputStreamReader isr = new InputStreamReader(con.getInputStream());
				BufferedReader in = new BufferedReader(isr);
				String s = null;
				while ( (s = in.readLine()) != null) {
					buffer.append(s).append("\n");
				}
			} catch ( Exception e ) {
				System.out.println(e.toString());
				buffer = null;
			} 
			List<String> serverAddressList = Noson.convert(buffer.toString(), new NoType<List<String>>() {});
			SimplePrinter.printNotice("Please select a server:");
			for(int i = 0; i < serverAddressList.size(); i++) {
				SimplePrinter.printNotice((i+1) + ". " + serverAddressList.get(i));
			}
			int serverPick = Integer.parseInt(SimpleWriter.write("option"));
			while(serverPick<1 || serverPick>serverAddressList.size()){
				SimplePrinter.printNotice("Invalid Option");
			}
			serverAddress = serverAddressList.get(serverPick-1);
			String[] elements = serverAddress.split(":");
			serverAddress = elements[0];
			port = Integer.parseInt(elements[1]);
		}
		
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new DefaultChannelInitializer());
			SimplePrinter.printNotice("Connecting to " + serverAddress + ":" + port);
			Channel channel = bootstrap.connect(serverAddress, port).sync().channel();
			channel.closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}

	}
	
}
