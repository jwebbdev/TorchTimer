package com.renderjunkies.torchtimer;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TorchListener implements Listener
{
	private TorchTimer tt;
	
	public TorchListener(TorchTimer tt)
	{
		this.tt = tt;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		// If the item isn't a torch
		if(event.getBlock().getTypeId() != 50)
			return;

		Player player = event.getPlayer();
		
		// If the player doesn't have the timed torch permission (on for everyone by default)
		if(!player.hasPermission("torchtimer.place.timed"))
			return;
		
		// If the player has the timed torches toggled off
		if(!tt.isTimed(player))
			return;
		
		final Block torch = event.getBlock();
		
		tt.getServer().getScheduler().scheduleSyncDelayedTask(tt,  new Runnable()
		{
			public void run()
			{
				tt.destroyTorch(torch);
			}
		}, (long)(tt.getDuration() * 20));
	}
}