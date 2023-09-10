/**
 * Copyright PDGH Minecraft Servers & HostLoad © 2013-XXXX
 * Todos os direitos reservados
 * Uso apenas para a PDGH.com.br e https://HostLoad.com.br
 * Caso você tenha acesso a esse sistema, você é privilegiado!
*/

package me.mchiappinam.pdghclanfronto;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comando implements CommandExecutor {
	private Main plugin;

	public Comando(Main main) {
		plugin = main;
	}
	
  	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cf")) {
			if (args.length==0) {
				plugin.help((Player)sender);
				return true;
			}
			if(args[0].equalsIgnoreCase("admin")) {
				if (args.length==1) {
					plugin.help((Player)sender);
					return true;
				}
				if(!sender.hasPermission("pdgh.admin")) {
					sender.sendMessage("§cSem permissões.");
					return true;
				}
				if(args[1].equalsIgnoreCase("setpos")) {
					if((args.length<4) || (args.length>4)) {
						sender.sendMessage("§eUse /cf admin setpos <1-2> <nome-da-arena>");
						return true;
					}
	                int valor=Integer.parseInt(args[2]);
					if(!(valor>=1)&&(valor<=2)) {
						sender.sendMessage("§eUse /cf admin setpos <1-2> <nome-da-arena>");
						return true;
					}
					Player p = (Player)sender;
					plugin.getConfig().set("arenas."+args[3]+"."+valor, p.getLocation().getWorld().getName()+";"+p.getLocation().getX()+";"+p.getLocation().getY()+";"+p.getLocation().getZ()+";"+p.getLocation().getYaw()+";"+p.getLocation().getPitch());
					plugin.saveConfig();
					sender.sendMessage("§aPosição "+valor+" marcada na arena "+args[3]);
					return true;
				}else if(args[1].equalsIgnoreCase("cancelar")) {
					if(args.length>2) {
						sender.sendMessage("§eUse /cf admin cancelar");
						return true;
					}
					if(!((plugin.status>=2)&&(plugin.status<=4))) {
						sender.sendMessage("§9[ⒸⒻ] §cNenhum CF em execução.");
						return true;
					}
					plugin.getServer().broadcastMessage("§9[ⒸⒻ] §2Cancelando...");
					double taxa = plugin.getConfig().getDouble("taxa");
					Main.econ.depositPlayer(plugin.desafiador.getName(), taxa);
					Main.econ.depositPlayer(plugin.desafiado.getName(), taxa);
					plugin.getServer().broadcastMessage("§9[ⒸⒻ] §2Taxa devolvida!");
					plugin.cAllTasks();
					plugin.status=5;
					plugin.teleportarForaArena();
					/**for(Player p : plugin.participantesDesafiador) {
						World w = plugin.getServer().getWorld(plugin.getConfig().getString("mundoPrincipal"));
				        if (w != null)
				        	p.teleport(w.getSpawnLocation());
				        else
				        	p.sendMessage("§cOcorreu um erro. Notifique alguém da STAFF.");
					}
					for(Player p : plugin.participantesDesafiado) {
						World w = plugin.getServer().getWorld(plugin.getConfig().getString("mundoPrincipal"));
				        if (w != null)
				        	p.teleport(w.getSpawnLocation());
				        else
				        	p.sendMessage("§cOcorreu um erro. Notifique alguém da STAFF.");
					}*/
					plugin.resetarEvento();
			        return true;
				}
				plugin.help((Player)sender);
				return true;
			}
			if(args[0].equalsIgnoreCase("desafiar")) {
				if((args.length<3) || (args.length>3)) {
					plugin.help((Player)sender);
					return true;
				}
				if(plugin.status==1) {
					sender.sendMessage("um CF já está pendente para aceitar ou negar");
					return true;
				}
				if(plugin.status==2||plugin.status==3||plugin.status==4) {
					sender.sendMessage("§9[ⒸⒻ] §cEstá acontecendo um CF no momento. Tente novamente mais tarde.");
					return true;
				}
			    if(plugin.status==5) {
					sender.sendMessage("§9[ⒸⒻ] §cAguarde o término da contagem para os vencedores do CF anterior sairem da arena.");
					return true;
			    }
			    Player playerargs1 = plugin.getServer().getPlayer(args[1]);
				if(playerargs1 == null) {
					sender.sendMessage("§9[ⒸⒻ] §cO jogador §a"+args[1]+" §cnão está online.");
					return true;
				}
				if(sender.getName().contains(playerargs1.getName())) {
					sender.sendMessage("§9[ⒸⒻ] §cVocê não pode se desafiar.");
					return true;
				}
                if(((Player)sender).isInsideVehicle()) {
				     sender.sendMessage("§9[ⒸⒻ] §cVocê está dentro de um veículo!");
				     return true;
				}else if(playerargs1.isInsideVehicle()) {
					sender.sendMessage("§9[ⒸⒻ] §a"+playerargs1.getName()+" §cestá dentro de um veículo!");
					plugin.getServer().getPlayer(args[1]).sendMessage("§9[ⒸⒻ] §a"+sender.getName()+" §ctentou te desafiar para CF com você dentro de um veículo!");
					return true;
				}
                if(((Player)sender).isDead()) {
				     sender.sendMessage("§9[ⒸⒻ] §cVocê está morto!");
				     return true;
				}else if(playerargs1.isDead()) {
					sender.sendMessage("§9[ⒸⒻ] §a"+playerargs1.getName()+" §cestá morto!");
					playerargs1.sendMessage("§9[ⒸⒻ] §a"+sender.getName()+" §ctentou te desafiar para CF com você morto!");
					return true;
				}
			    if(!plugin.hasClan((Player)sender)) {
					sender.sendMessage("§9[ⒸⒻ] §cVocê não tem um clan.");
					return true;
			    }else if(!plugin.hasClan(playerargs1)) {
						sender.sendMessage("§9[ⒸⒻ] §c"+playerargs1.getName()+" não tem um clan.");
						return true;
				    }
				if(plugin.isMesmoClan((Player)sender, playerargs1)) {
					sender.sendMessage("§9[ⒸⒻ] §c"+playerargs1.getName()+" é do mesmo clan que você ("+plugin.getClanTAG((Player)sender)+")");
					return true;
				}
				if(!plugin.isLeadder((Player)sender)) {
					sender.sendMessage("§9[ⒸⒻ] §cVocê não é líder do seu clan atual.");
					return true;
				}else if(!plugin.isLeadder(playerargs1)) {
					sender.sendMessage("§9[ⒸⒻ] §c"+playerargs1.getName()+" não é líder do clan.");
					return true;
				}
				double taxa = plugin.getConfig().getDouble("taxa");
		        if (!(Main.econ.getBalance(sender.getName()) >= taxa)) {
				    sender.sendMessage("§9[ⒸⒻ] §cVocê não tem money suficiente.");
				    sender.sendMessage("§9[ⒸⒻ] §cMoney necessário: §a$"+taxa+"§c.");
				    return true;
		        }else if (!(Main.econ.getBalance(playerargs1.getName()) >= taxa)) {
				    sender.sendMessage("§9[ⒸⒻ] §cO jogador "+playerargs1.getName()+" não tem money suficiente.");
				    sender.sendMessage("§9[ⒸⒻ] §cMoney necessário: §a$"+taxa+"§c.");
				    return true;
		        }
                plugin.jogadores=Integer.parseInt(args[2]);
				if(!(plugin.jogadores>=2)&&(plugin.jogadores<=5)) {
					plugin.help((Player)sender);
					return true;
				}
				if(plugin.clanOnlinePlayers(plugin.getClanTAG((Player)sender))<plugin.jogadores) {
					sender.sendMessage("§9[ⒸⒻ] §cSeu clan não tem "+plugin.jogadores+" ou mais jogadores online.");
					return true;
				}else if(plugin.clanOnlinePlayers(plugin.getClanTAG(playerargs1))<plugin.jogadores) {
					sender.sendMessage("§9[ⒸⒻ] §cO clan de "+playerargs1.getName()+" não tem "+plugin.jogadores+" ou mais jogadores online.");
					return true;
				}
				plugin.participantesDesafiador.add((Player)sender);
                plugin.desafiador=((Player)sender);
                plugin.desafiado=playerargs1;
                plugin.status=1;
                plugin.tempoResposta();
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §fSerá que o clan §a"+plugin.getClanTAGColorida(plugin.desafiado)+" §faceita ou arrega?");
                plugin.desafiador.sendMessage(" ");
                plugin.desafiador.sendMessage("§9[ⒸⒻ] §5"+ChatColor.BOLD+"✸ §a"+plugin.getClanTAGColorida(plugin.desafiado)+" §ftem 1 minuto para aceitar ou negar o "+plugin.jogadores+"x"+plugin.jogadores+".");
                plugin.desafiado.sendMessage(" ");
                plugin.desafiado.sendMessage("§9[ⒸⒻ] §5"+ChatColor.BOLD+"✸ §f1 minuto para aceitar ou negar.");
                plugin.desafiado.sendMessage("§9[ⒸⒻ] §5"+ChatColor.BOLD+"✸ §fAceite com o comando §a/cf aceitar");
                plugin.desafiado.sendMessage("§9[ⒸⒻ] §5"+ChatColor.BOLD+"✸ §fNegue com o comando §a/cf negar");
                plugin.desafiado.sendMessage("§cTaxa de §a§l$"+taxa+"§c.");
				return true;
			}else if(args[0].equalsIgnoreCase("aceitar")) {
				if(args.length>1) {
					plugin.help((Player)sender);
					return true;
				}
				if(plugin.status==0) {
					sender.sendMessage("§9[ⒸⒻ] §cNenhum CF ocorrendo.");
					return true;
				}
				if(plugin.status==2||plugin.status==3||plugin.status==4) {
					sender.sendMessage("§9[ⒸⒻ] §cEstá acontecendo um CF no momento.");
					return true;
				}
			    if(plugin.status==5) {
					sender.sendMessage("§9[ⒸⒻ] §cAguarde o término da contagem para os vencedores do antigo CF sairem da arena.");
					return true;
			    }
		        if(sender.getName().contains(plugin.desafiador.getName())) {
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê não pode aceitar um desafio seu!");
		        	return true;
				}
		        if(!sender.getName().contains(plugin.desafiado.getName())) {
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê não foi o desafiado!");
		        	return true;
		        }
				if(((Player)sender).isInsideVehicle()) {
			     	sender.sendMessage("§9[ⒸⒻ] §cVocê está dentro de um veículo!");
			     	return true;
				}else if(plugin.desafiador.isInsideVehicle()) {
					sender.sendMessage("§9[ⒸⒻ] §a"+plugin.desafiador.getName()+" §cestá dentro de um veículo!");
					plugin.desafiador.sendMessage("§9[ⒸⒻ] §a"+sender.getName()+" §ctentou aceitar seu CF com você dentro de um veículo!");
					return true;
				}
            	if(((Player)sender).isDead()) {
			     	sender.sendMessage("§9[ⒸⒻ] §cVocê está morto!");
			     	return true;
				}else if(plugin.desafiador.isDead()) {
					sender.sendMessage("§9[ⒸⒻ] §a"+plugin.desafiador.getName()+" §cestá morto!");
					plugin.desafiador.sendMessage("§9[ⒸⒻ] §a"+sender.getName()+" §ctentou aceitar seu CF com você morto!");
					return true;
				}
			    if(!plugin.hasClan((Player)sender)) {
					sender.sendMessage("§9[ⒸⒻ] §cVocê não tem um clan.");
					return true;
			    }else if(!plugin.hasClan(plugin.desafiador)) {
						sender.sendMessage("§9[ⒸⒻ] §c"+plugin.desafiador.getName()+" não tem um clan.");
						return true;
				    }
				if(plugin.isMesmoClan((Player)sender, plugin.desafiador)) {
					sender.sendMessage("§9[ⒸⒻ] §c"+plugin.desafiador.getName()+" é do mesmo clan que você ("+plugin.getClanTAG((Player)sender)+")");
					return true;
				}
				if(!plugin.isLeadder((Player)sender)) {
					sender.sendMessage("§9[ⒸⒻ] §cVocê não é líder do seu clan atual.");
					return true;
				}else if(!plugin.isLeadder(plugin.desafiador)) {
					sender.sendMessage("§9[ⒸⒻ] §c"+plugin.desafiador.getName()+" não é líder do clan.");
					return true;
				}
				double taxa = plugin.getConfig().getDouble("taxa");
		        if (!(Main.econ.getBalance(sender.getName()) >= taxa)) {
				    sender.sendMessage("§9[ⒸⒻ] §cVocê não tem money suficiente.");
				    sender.sendMessage("§9[ⒸⒻ] §cMoney necessário: §a$"+taxa+"§c.");
				    return true;
		        }else if (!(Main.econ.getBalance(plugin.desafiador.getName()) >= taxa)) {
				    sender.sendMessage("§9[ⒸⒻ] §cO jogador "+plugin.desafiador.getName()+" não tem money suficiente.");
				    sender.sendMessage("§9[ⒸⒻ] §cMoney necessário: §a$"+taxa+"§c.");
				    return true;
		        }
				if(plugin.clanOnlinePlayers(plugin.getClanTAG((Player)sender))<plugin.jogadores) {
					sender.sendMessage("§9[ⒸⒻ] §cSeu clan não tem "+plugin.jogadores+" ou mais jogadores online.");
					return true;
				}else if(plugin.clanOnlinePlayers(plugin.getClanTAG(plugin.desafiador))<plugin.jogadores) {
					sender.sendMessage("§9[ⒸⒻ] §cO clan de "+plugin.desafiador.getName()+" não tem "+plugin.jogadores+" ou mais jogadores online.");
					return true;
				}
                plugin.ctempoResposta();
                plugin.status=2;
                Main.econ.withdrawPlayer(sender.getName(), taxa);
                sender.sendMessage("§9[ⒸⒻ] §a$"+taxa+" debitados de sua conta!");
                Main.econ.withdrawPlayer(plugin.desafiador.getName(), taxa);
                plugin.desafiador.sendMessage("§9[ⒸⒻ] §a$"+taxa+" debitados de sua conta!");
                plugin.participantesDesafiado.add((Player)sender);
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.jogadores+"x"+plugin.jogadores+"§f - "+plugin.getClanTAGColorida(plugin.desafiador)+" §fvs §a"+plugin.getClanTAGColorida(plugin.desafiado));
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.getClanTAGColorida(plugin.desafiado)+" §faceitou! §c>:)");
                plugin.notifyClan(plugin.getClanTAG((Player)sender), "§9["+plugin.getClanTAGColorida((Player)sender)+"§9] §5"+ChatColor.BOLD+"✸ §fVocês estão escalados para um ClanFronto!");
                plugin.notifyClan(plugin.getClanTAG((Player)sender), "§9["+plugin.getClanTAGColorida((Player)sender)+"§9] §5"+ChatColor.BOLD+"✸ §fDigite §e/cf participar§f para participar.");
                plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(plugin.desafiador)+"§9] §5"+ChatColor.BOLD+"✸ §fVocês estão escalados para um ClanFronto!");
                plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida(plugin.desafiador)+"§9] §5"+ChatColor.BOLD+"✸ §fDigite §e/cf participar§f para participar.");
                plugin.tempoResposta();
				return true;
			}else if(args[0].equalsIgnoreCase("negar")) {
				if(args.length>1) {
					plugin.help((Player)sender);
					return true;
				}
				if(plugin.status==0) {
					sender.sendMessage("§9[ⒸⒻ] §cNenhum CF ocorrendo.");
					return true;
				}
				if(plugin.status==2||plugin.status==3||plugin.status==4) {
					sender.sendMessage("§9[ⒸⒻ] §cEstá acontecendo um CF no momento.");
					return true;
				}
			    if(plugin.status==5) {
					sender.sendMessage("§9[ⒸⒻ] §cAguarde o término da contagem para os vencedores do antigo CF sairem da arena.");
					return true;
			    }
		        if(sender.getName().contains(plugin.desafiador.getName())) {
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê não pode negar um desafio seu!");
		        	return true;
				}
		        if(!sender.getName().contains(plugin.desafiado.getName())) {
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê não foi o desafiado!");
		        	return true;
		        }
                plugin.ctempoResposta();
                plugin.getServer().broadcastMessage("§9[ⒸⒻ] §a"+plugin.desafiado.getName()+" §fnegou o CF de §a"+plugin.desafiador.getName());
                plugin.resetarEvento();
				return true;
			}else if(args[0].equalsIgnoreCase("participar")) {
				if(args.length>1) {
					plugin.help((Player)sender);
					return true;
				}
				if(plugin.status==0) {
					sender.sendMessage("§9[ⒸⒻ] §cNenhum CF ocorrendo.");
					return true;
				}
				if(plugin.status==1||plugin.status==3||plugin.status==4||plugin.status==5) {
					sender.sendMessage("§9[ⒸⒻ] §cCF não disponível para participar.");
					return true;
				}
		        if((plugin.participantesDesafiado.contains((Player)sender))||(plugin.participantesDesafiador.contains((Player)sender))) {
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê já está participando, aguarde o início.");
		        	return true;
				}
		        int clan=0; //1=desafiador, 2=desafiado
		        if(plugin.getClanTAG((Player)sender).contains(plugin.getClanTAG(plugin.desafiador))) {
		        	clan=1;
		        }else if(plugin.getClanTAG((Player)sender).contains(plugin.getClanTAG(plugin.desafiado))) {
		        	clan=2;
		        }else{
		        	sender.sendMessage("§9[ⒸⒻ] §cVocê não faz parte dos clans que vão confrontar.");
		        	return true;
		        }
                if(clan==1) {
                	if(plugin.participantesDesafiador.size()==plugin.jogadores) {
                		sender.sendMessage("§9[ⒸⒻ] §cLimite de "+plugin.jogadores+" jogadores nesse ClanFronto!");
                		return true;
                	}
                	plugin.participantesDesafiador.add((Player)sender);
                }else if(clan==2) {
                	if(plugin.participantesDesafiado.size()==plugin.jogadores) {
                		sender.sendMessage("§9[ⒸⒻ] §cLimite de "+plugin.jogadores+" jogadores nesse ClanFronto!");
                		return true;
                	}
                	plugin.participantesDesafiado.add((Player)sender);
                }
                plugin.notifyClan(plugin.getClanTAG(plugin.desafiador), "§9["+plugin.getClanTAGColorida((Player)sender)+"§9] §5"+ChatColor.BOLD+"✸ §f"+sender.getName()+" está participando.");
                plugin.notifyClan(plugin.getClanTAG(plugin.desafiado), "§9["+plugin.getClanTAGColorida((Player)sender)+"§9] §5"+ChatColor.BOLD+"✸ §f"+sender.getName()+" está participando.");
                return true;
			}
			if (args.length<2) {
				plugin.help((Player)sender);
				return true;
			}
		}
		return false;
    }
  	
}