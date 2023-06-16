package com.hse.units.repos;

import com.hse.units.domain.Form;
import com.hse.units.domain.PercentageOfProgress;
import com.hse.units.domain.TaskTag;
import com.hse.units.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProgressRepository extends CrudRepository<PercentageOfProgress, Long>,
        PagingAndSortingRepository<PercentageOfProgress, Long> {
    List<PercentageOfProgress> findByUser(User user);

    Optional<PercentageOfProgress> findByUserAndTag(User user, TaskTag tag);

    default Map<TaskTag, PercentageOfProgress> userProgress(User user) {
        List<PercentageOfProgress> percentageOfProgresses = findByUser(user);
        Map<TaskTag, PercentageOfProgress> result = new HashMap<>();

        for (var p : percentageOfProgresses) {
            result.put(p.getTag(), p);
        }

        return result;
    }

}
