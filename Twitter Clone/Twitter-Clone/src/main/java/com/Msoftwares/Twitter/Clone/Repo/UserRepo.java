package com.Msoftwares.Twitter.Clone.Repo;

import com.Msoftwares.Twitter.Clone.Model.Post;
import com.Msoftwares.Twitter.Clone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository <User, String>{
    User findByScreenName(String screenName);
}
