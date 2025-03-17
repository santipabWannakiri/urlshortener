package com.shortened.repository;

import com.shortened.model.ShortenerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ShortenerRepository")
public interface ShortenerRepository extends JpaRepository<ShortenerModel,Long> {

}
