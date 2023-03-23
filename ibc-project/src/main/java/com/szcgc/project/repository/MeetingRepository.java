package com.szcgc.project.repository;

import com.szcgc.project.model.MeetingInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/8/7 17:18
 * @Description:
 */
public interface MeetingRepository extends PagingAndSortingRepository<MeetingInfo, Integer> {

    List<MeetingInfo> findByMeetDateBetweenOrderByMeetDate(LocalDate startDate, LocalDate endDate);

}
