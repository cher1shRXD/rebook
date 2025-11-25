package me.cher1shrxd.rebook.domain.review.repository;

import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByUserEmail(String email);
}
