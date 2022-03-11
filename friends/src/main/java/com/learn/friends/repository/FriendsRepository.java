package com.learn.friends.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.learn.friends.entity.Friends;

public interface FriendsRepository extends CrudRepository<Friends, Long>{
	@Query(value="Select * from friends_list f where f.friends_from=:id", nativeQuery=true)
	public List<Friends> findFriendsById(@Param("id") Long id);
}
