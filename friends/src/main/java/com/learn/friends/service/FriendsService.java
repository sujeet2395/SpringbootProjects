package com.learn.friends.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.friends.entity.Friends;
import com.learn.friends.repository.FriendsRepository;

@Service
public class FriendsService {
	@Autowired
	private FriendsRepository friendsRepository;
	
	public List<Friends> getFriendsAtDistanceK(Long id, int k)
	{
		Set<Long> set=new HashSet<>();
		int count=0;
		List<Friends> f_list=friendsRepository.findFriendsById(id);
		List<Friends> f_list_r=null;
		for(Friends f: f_list)
		{
			if(!set.contains(f.getId()))
			{
				set.add(id);
				count++;
				f_list_r=getFriendsAtDistanceK(f.getId(), k, count, set, f, new ArrayList<>());
				count--;
			}
		}
		return f_list_r;
	}
	public List<Friends> getFriendsAtDistanceK(Long id, int k, int count, Set<Long> set, Friends frnd, List<Friends> res_list)
	{
		if(count == k)
		{
			res_list.add(frnd);
			return res_list;
		}
		List<Friends> f_list=friendsRepository.findFriendsById(id);
		List<Friends> f_list_r=null;
		for(Friends f: f_list)
		{
			if(!set.contains(f.getId()))
			{
				set.add(id);
				count++;
				f_list_r= getFriendsAtDistanceK(f.getId(), k, count, set, f, res_list);
				count--;
			}
		}
		return f_list_r;
	}
	
	public Friends createFriends(Friends frnd)
	{
		return friendsRepository.save(frnd);
	}
}
