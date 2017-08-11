package io.tchepannou.k.travel.client.response;

import java.util.List;

public class GetSchedulesReponse {
    private List<ScheduleDto> schedules;

    public List<ScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(final List<ScheduleDto> schedules) {
        this.schedules = schedules;
    }
}
