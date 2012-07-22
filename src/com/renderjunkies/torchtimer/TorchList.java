package com.renderjunkies.torchtimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ToggleList")
public class TorchList implements ConfigurationSerializable
{
	private List<String> toggledOff;
	
	@Override
	public Map<String, Object> serialize() 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		for(String pName : toggledOff)
		{
			map.put(pName, "ToggleList");
		}
		return map;
	}
	
	public TorchList()
	{
		toggledOff = new ArrayList<String>();
	}
	
	public TorchList(Map<String, Object> map)
	{
		toggledOff = new ArrayList<String>();
		for(Map.Entry<String, Object> entry : map.entrySet())
		{
			toggledOff.add(entry.getKey());
		}
	}
	
	public TorchList deserialize(Map<String, Object> map)
	{
		toggledOff.clear();
		for(Map.Entry<String, Object> entry : map.entrySet())
		{
			toggledOff.add(entry.getKey());
		}
		return this;
	}

	public void toggleTimer(String player)
	{
		if(toggledOff.contains(player))
			toggledOff.remove(player);
		else
			toggledOff.add(player);
	}
	
	public boolean isTimed(String player)
	{
		if(toggledOff.contains(player))
			return false;
		return true;
	}
	
}