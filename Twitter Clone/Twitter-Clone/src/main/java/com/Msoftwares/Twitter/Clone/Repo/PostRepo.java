package com.Msoftwares.Twitter.Clone.Repo;

import com.Msoftwares.Twitter.Clone.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByPostUser_ScreenNameOrContentContains(String screenName, String mention);

}
