package me.cher1shrxd.rebook.domain.review.repository;

import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

}
