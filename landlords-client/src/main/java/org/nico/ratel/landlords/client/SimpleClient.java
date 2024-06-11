package org.nico.ratel.landlords.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.noson.util.string.StringUtils;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.client.proxy.ProtobufProxy;
import org.nico.ratel.landlords.client.proxy.WebsocketProxy;
import org.nico.ratel.landlords.features.Features;
import org.nico.ratel.landlords.helper.I18nHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.StreamUtils;

public class SimpleClient {

	public static int id = -1;

	public final static String VERSION = Features.VERSION_1_3_0;

	public static String serverAddress;

	public static int port = 1024;

	public static String protocol = "pb";

	public static String language;

	private final static String[] serverAddressSource = new String[]{
			"https://raw.githubusercontent.com/ainilili/ratel/master/serverlist.json",			//Source
			"https://cdn.jsdelivr.net/gh/ainilili/ratel@master/serverlist.json",				//CN CDN
			"https://raw.fastgit.org/ainilili/ratel/master/serverlist.json",				//HongKong CDN
			"https://cdn.staticaly.com/gh/ainilili/ratel/master/serverlist.json",				//Japanese CDN
			"https://ghproxy.com/https://raw.githubusercontent.com/ainilili/ratel/master/serverlist.json",	//Korea CDN
			"https://gitee.com/ainilili/ratel/raw/master/serverlist.json"					//CN Gitee
	};

	public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
		if (args != null && args.length > 0) {
			for (int index = 0; index < args.length; index = index + 2) {
				if (index + 1 < args.length) {
					if (args[index].equalsIgnoreCase("-p") || args[index].equalsIgnoreCase("-port")) {
						port = Integer.parseInt(args[index + 1]);
					}
					if (args[index].equalsIgnoreCase("-h") || args[index].equalsIgnoreCase("-host")) {
						serverAddress = args[index + 1];
					}
					if (args[index].equalsIgnoreCase("-ptl") || args[index].equalsIgnoreCase("-protocol")) {
						protocol = args[index + 1];
					}
					if (args[index].equalsIgnoreCase("-lang") || args[index].equalsIgnoreCase("-language")) {
						language = args[index + 1];
					}
				}
			}
		}

		if (StringUtils.isBlank(language)) {
			I18nHelper.enable();
		} else {
			Locale locale = getLocale(language);
			I18nHelper.enable(locale);
		}

		if (serverAddress == null) {
			List<String> serverAddressList = getServerAddressList();
			if (serverAddressList == null || serverAddressList.size() == 0) {
				throw new RuntimeException("Please use '-host' to setting server address.");
			}

			SimplePrinter.printTranslate("pls_select_srv");
			for (int i = 0; i < serverAddressList.size(); i++) {
				SimplePrinter.printNotice((i + 1) + ". " + serverAddressList.get(i));
			}
			int serverPick = Integer.parseInt(SimpleWriter.write(User.INSTANCE.getNickname(), "option"));
			while (serverPick < 1 || serverPick > serverAddressList.size()) {
				try {
					SimplePrinter.printTranslate("srv_addr_not_exist");
					serverPick = Integer.parseInt(SimpleWriter.write(User.INSTANCE.getNickname(), "option"));
				} catch (NumberFormatException ignore) {}
			}
			serverAddress = serverAddressList.get(serverPick - 1);
			String[] elements = serverAddress.split(":");
			serverAddress = elements[0];
			port = Integer.parseInt(elements[1]);
		}

		if (Objects.equals(protocol, "pb")) {
			new ProtobufProxy().connect(serverAddress, port);
		} else if (Objects.equals(protocol, "ws")) {
			new WebsocketProxy().connect(serverAddress, port + 1);
		} else {
			throw new UnsupportedOperationException("Unsupported protocol " + protocol);
		}
	}

	private static List<String> getServerAddressList() {
		for (String serverAddressSource : serverAddressSource) {
			try {
				String serverInfo = StreamUtils.convertToString(new URL(serverAddressSource));
				return Noson.convert(serverInfo, new NoType<List<String>>() {});
			} catch (IOException e) {
				SimplePrinter.printTranslate("try_connect_%s_failed_%s", serverAddressSource, e.getMessage());
			}
		}
		return null;
	}

	private static Locale getLocale(String langCode) {
		switch (langCode) {
			case "zh":
			case "zh_CN":
				return Locale.SIMPLIFIED_CHINESE;
			case "en":
			case "en_US":
					return Locale.US;
			default:
				System.out.println("[warning] not supported language code: " + langCode + ", set to en_US");
				return Locale.US;
		}
	}
}
