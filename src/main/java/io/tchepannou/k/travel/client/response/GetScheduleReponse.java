package io.tchepannou.k.travel.client.response;

public class GetScheduleReponse {
    private ScheduleDto schedule;

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(final ScheduleDto schedule) {
        this.schedule = schedule;
    }
}
