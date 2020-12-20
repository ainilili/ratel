package org.nico.ratel.landlords.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.handler.DefaultChannelInitializer;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.StreamUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {

	public static int id = -1;

	public static String serverAddress;

	public static int port = 1024;

	private static String[] serverAddressSource = new String[] {
			"https://raw.githubusercontent.com/ainilili/ratel/master/serverlist.json",
			"https://gitee.com/ainilili/ratel/raw/master/serverlist.json"
	};

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
		
		if(serverAddress == null){
			List<String> serverAddressList = getServerAddressList();
			if(serverAddressList == null || serverAddressList.size() == 0) {
				throw new RuntimeException("Please use '-host' to setting server address.");
			}
			
			SimplePrinter.printNotice("Please select a server:");
			for(int i = 0; i < serverAddressList.size(); i++) {
				SimplePrinter.printNotice((i+1) + ". " + serverAddressList.get(i));
			}
			int serverPick = Integer.parseInt(SimpleWriter.write("option"));
			while(serverPick < 1 || serverPick > serverAddressList.size()){
				try {
					SimplePrinter.printNotice("The server address does not exist!");
					serverPick = Integer.parseInt(SimpleWriter.write("option"));
				}catch(NumberFormatException e){}
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

	private static List<String> getServerAddressList(){
		for(String serverAddressSource: serverAddressSource) {
			try {
				String serverInfo = StreamUtils.convertToString(new URL(serverAddressSource));
				return  Noson.convert(serverInfo, new NoType<List<String>>() {});
			} catch (IOException e) {
				SimplePrinter.printNotice("Try connected " + serverAddressSource + " failed: " + e.getMessage());
			}
		}
		return null;
	}

}
