package com.renderjunkies.torchtimer;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TorchTimer extends JavaPlugin
{
	private int torchDuration;
	private TorchList toggledOff;
	@Override
	public void onEnable()
	{
		ConfigurationSerialization.registerClass(TorchList.class, "ToggleList");
		getServer().getPluginManager().registerEvents(new TorchListener(this),  this);
		loadConfig();
		getLogger().info("TorchTimer has been enabled.");
	}
	
	public void onDisable()
	{
		getConfig().set("ToggleList", toggledOff);
		saveConfig();
		getLogger().info("TorchTimer has been disabled.");
	}
	
	public void toggleTimer(Player player)
	{
		toggledOff.toggleTimer(player.getName());
		getConfig().set("ToggleList", toggledOff);
	}
	
	public boolean isTimed(Player player)
	{
		return toggledOff.isTimed(player.getName());
	}
	
	public void destroyTorch(Block block)
	{
		block.setTypeId(0);
	}
	
	public void loadConfig()
	{
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.reloadConfig();
		torchDuration = getConfig().getInt("duration");
		toggledOff = (TorchList)getConfig().get("ToggleList");
		if(toggledOff == null)
			toggledOff = new TorchList();
	}
	
	public int getDuration()
	{
		return torchDuration;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("torchToggle"))
		{
			if(sender instanceof Player)
			{
				toggleTimer((Player)sender);
				if(isTimed((Player)sender))
					sender.sendMessage("Your torches will now be timed.");
				else
					sender.sendMessage("Your torches will now be permanent.");
				return true;
			}
			else
			{
				sender.sendMessage("Only players may execute this command.");
				return false;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("torchSet"))
		{
			if(args.length != 1)
			{
				return false;
			}
			else
			{
				try
				{
					this.torchDuration = Integer.parseInt(args[0]);
					getConfig().set("duration", this.torchDuration);
					saveConfig();
					sender.sendMessage(ChatColor.YELLOW + "TorchTimer duration set to "+this.torchDuration+" seconds.");
					return true;
				}
				catch(NumberFormatException e)
				{
					return false;
				}
			}
		}
		return false;
	}
}