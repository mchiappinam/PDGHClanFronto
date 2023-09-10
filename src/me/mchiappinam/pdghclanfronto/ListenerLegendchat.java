package me.mchiappinam.pdghclanfronto;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class ListenerLegendchat implements Listener {
	private Main plugin;
	public ListenerLegendchat(Main main) {
		plugin=main;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	private void onChat(ChatMessageEvent e) {
		if(plugin.hasClan(e.getSender()))
			if(plugin.getClanTAG(e.getSender()).contains(plugin.getConfig().getString("vencedor")))
				e.setTagValue("cf", plugin.getConfig().getString("tag").replaceAll("&", "§"));
	}
}
