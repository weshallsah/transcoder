package com.weshallsah.transcoder.Repo;

import com.weshallsah.transcoder.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<Users,Integer> {

    public Users findByUsername(String username);

}
