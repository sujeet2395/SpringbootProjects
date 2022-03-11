package com.learn.friends.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.friends.entity.Friends;
import com.learn.friends.service.FriendsService;

@RestController
public class FriendsController {
	@Autowired
	private FriendsService friendsService;
	
	@PostMapping("/friends")
	public Friends createFriends(@RequestBody Friends frnd)
	{
		return friendsService.createFriends(frnd);
	}
	
	@GetMapping("/friends/{id}/distance/{k}")
	public List<Friends> getFriendsAtDistanceK(@PathVariable("id") Long id,@PathVariable("k") int k)
	{
		return friendsService.getFriendsAtDistanceK(id, k);
	}
}
