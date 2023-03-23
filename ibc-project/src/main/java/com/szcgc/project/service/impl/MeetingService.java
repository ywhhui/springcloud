package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.project.model.MeetingInfo;
import com.szcgc.project.repository.MeetingRepository;
import com.szcgc.project.service.IMeetingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @Author: chenxinli
 * @Date: 2020/8/7 17:17
 * @Description:
 */
@Service
public class MeetingService extends BaseService<MeetingRepository, MeetingInfo, Integer> implements IMeetingService {

    @Override
    public List<MeetingInfo> findByDate(LocalDate startDate, LocalDate endDate) {
        return repository.findByMeetDateBetweenOrderByMeetDate(Objects.requireNonNull(startDate), Objects.requireNonNull(endDate));
    }
}
