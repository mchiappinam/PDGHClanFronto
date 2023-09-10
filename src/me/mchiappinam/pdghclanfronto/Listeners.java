/**
 * Copyright PDGH Minecraft Servers & HostLoad © 2013-XXXX
 * Todos os direitos reservados
 * Uso apenas para a PDGH.com.br e https://HostLoad.com.br
 * Caso você tenha acesso a esse sistema, você é privilegiado!
*/

package me.mchiappinam.pdghclanfronto;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Listeners implements Listener {
	
	private Main plugin;
	public Listeners(Main main) {
		plugin=main;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void onDeath(PlayerDeathEvent e) {
		if((plugin.status==3)||(plugin.status==4)) {
			if(plugin.participantesDesafiador.contains((Player)e.getEntity())) {
				plugin.participantesDesafiador.remove((Player)e.getEntity());
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getEntity())+"§f] §c"+e.getEntity().getName()+" §fmorreu!");
			}else if(plugin.participantesDesafiado.contains((Player)e.getEntity())) {
				plugin.participantesDesafiado.remove((Player)e.getEntity());
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getEntity())+"§f] §c"+e.getEntity().getName()+" §fmorreu!");
			}
			plugin.checkFim();
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void onQuit(PlayerQuitEvent e) {
		if(plugin.participantesDesafiador.contains(e.getPlayer())) {
			plugin.participantesDesafiador.remove(e.getPlayer());
			if((plugin.status==3)||(plugin.status==4)) {
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
	            plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getPlayer())+"§f] §c"+e.getPlayer().getName()+" §fdesconectou e morreu!");
	            e.getPlayer().setHealth(0);
				plugin.checkFim();
			}else{
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiado), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
	                
			}
		}else if(plugin.participantesDesafiado.contains(e.getPlayer())) {
			plugin.participantesDesafiado.remove(e.getPlayer());
			if((plugin.status==3)||(plugin.status==4)) {
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
	            plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getPlayer())+"§f] §c"+e.getPlayer().getName()+" §fdesconectou e morreu!");
	            e.getPlayer().setHealth(0);
				plugin.checkFim();
			}else{
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiado), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
	                
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void onKick(PlayerKickEvent e) {
		if(plugin.participantesDesafiador.contains(e.getPlayer())) {
			plugin.participantesDesafiador.remove(e.getPlayer());
			if((plugin.status==3)||(plugin.status==4)) {
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
	            plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getPlayer())+"§f] §c"+e.getPlayer().getName()+" §fdesconectou e morreu!");
	            e.getPlayer().setHealth(0);
				plugin.checkFim();
			}else{
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiado), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
	                
			}
		}else if(plugin.participantesDesafiado.contains(e.getPlayer())) {
			plugin.participantesDesafiado.remove(e.getPlayer());
			if((plugin.status==3)||(plugin.status==4)) {
				plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
	            plugin.getServer().broadcastMessage("§9[ⒸⒻ] §f["+plugin.getClanTAGColorida(e.getPlayer())+"§f] §c"+e.getPlayer().getName()+" §fdesconectou e morreu!");
	            e.getPlayer().setHealth(0);
				plugin.checkFim();
			}else{
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
				plugin.notifyClan(plugin.getClanTAG(plugin.desafiado), "§9["+plugin.getClanTAGColorida(e.getPlayer())+"§9] §5"+ChatColor.BOLD+"✸ §c"+e.getPlayer().getName()+" desconectou-se.");
	                
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player)
			if(e.getDamager() instanceof Player||e.getDamager() instanceof Projectile) {
				Player ent = (Player)e.getEntity();
				Player dam = null;
				if(e.getDamager() instanceof Player)
					dam=(Player)e.getDamager();
				else {
					Projectile a = (Projectile) e.getDamager();
					if(a.getShooter() instanceof Player)
						dam=(Player)a.getShooter();
				}
				if(((plugin.participantesDesafiador.contains(ent))&&(plugin.status==3))||((plugin.participantesDesafiado.contains(ent))&&(plugin.status==3))) {
					e.setCancelled(true);
					if(dam!=null)
						dam.sendMessage("§c§lPvP desativado!");
					return;
				}
				if(dam!=null&&(plugin.status==3||plugin.status==4||plugin.status==5))
					if((plugin.participantesDesafiador.contains(ent))||(plugin.participantesDesafiado.contains(ent)))
						if(plugin.getClanTAG(ent).contains(plugin.getClanTAG(dam)))
							e.setCancelled(true);
			}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void onDamageP(PotionSplashEvent e) {
		for(Entity ent2 : e.getAffectedEntities())
			if(ent2 instanceof Player) {
				Player dam = null;
				Player ent = (Player)ent2;
				if(e.getPotion().getShooter() instanceof Player)
					dam=(Player)e.getEntity().getShooter();
				if(((plugin.participantesDesafiador.contains(ent))&&(plugin.status==3))||((plugin.participantesDesafiado.contains(ent))&&(plugin.status==3))) {
					e.setCancelled(true);
					dam.sendMessage("§c§lPvP desativado!");
				}
				if(dam!=null&&(plugin.status==3||plugin.status==4||plugin.status==5))
					if((plugin.participantesDesafiador.contains(ent))||(plugin.participantesDesafiado.contains(ent)))
						if(plugin.getClanTAG(ent).contains(plugin.getClanTAG(dam)))
							e.getAffectedEntities().remove(ent2);
			}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
	    if(e.getPlayer().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF"))) {
        	
	        World w = plugin.getServer().getWorld(plugin.getConfig().getString("mundoPrincipal"));
	        if (w != null) {
	        	e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê morreu :(");
	        	e.setRespawnLocation(w.getSpawnLocation());
	        }else{
	        	e.getPlayer().sendMessage("§cOcorreu um erro. Notifique alguém da STAFF.");
	        }
	        
	    }
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
	    if(e.getPlayer().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF"))) {
        	
	        World w = plugin.getServer().getWorld(plugin.getConfig().getString("mundoPrincipal"));
	        if (w != null) {
	        	e.getPlayer().teleport(w.getSpawnLocation());
	        	e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê desconectou no cf e foi teleportado para o spawn.");
	        }else{
	        	e.getPlayer().sendMessage("§cOcorreu um erro. Notifique alguém da STAFF.");
	        }
	        
	    }
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e) {
	    if((e.getBlock().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))&&(!e.getPlayer().hasPermission("pdgh.op"))) {
        	e.setCancelled(true);
        	e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê não pode quebrar blocos do cf.");
	    }
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent e) {
	    if((e.getBlock().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))&&(!e.getPlayer().hasPermission("pdgh.op"))) {
        	e.setCancelled(true);
        	e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê não pode colocar blocos no cf.");
	    }
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPCmd(PlayerCommandPreprocessEvent e) {
	    if((e.getPlayer().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))&&(!e.getPlayer().hasPermission("pdgh.moderador"))) {
	    	if((e.getMessage().toLowerCase().startsWith("/g"))||(e.getMessage().toLowerCase().startsWith("/."))) {
	    		return;
	    	}else{
	    		e.setCancelled(true);
	    		e.getPlayer().sendMessage("§9[ⒸⒻ] §cApenas os comandos do chat global (/g) e do chat do clan (/.) são liberados.");
	    	}
    	}
	}
	
	@EventHandler
    private void onTeleport(PlayerTeleportEvent e) {
		if((e.getFrom().getWorld()!=plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))&&(e.getTo().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))) {
			if((plugin.participantesDesafiador.contains(e.getPlayer()))||(plugin.participantesDesafiado.contains(e.getPlayer()))) {
			}else{
				if(e.getPlayer().hasPermission("pdgh.moderador"))
					return;
				e.setCancelled(true);
    			e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê não pode entrar no cf!");
    		}
		}
		if((e.getFrom().getWorld()==plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))&&(e.getTo().getWorld()!=plugin.getServer().getWorld(plugin.getConfig().getString("mundoDoCF")))) {
			if(((plugin.participantesDesafiador.contains(e.getPlayer()))&&(plugin.status==4))||((plugin.participantesDesafiado.contains(e.getPlayer()))&&(plugin.status==4))) {
				if(e.getPlayer().hasPermission("pdgh.moderador"))
					return;
    			e.setCancelled(true);
    			e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê não pode sair do cf!");
    		}
		}
	}
}