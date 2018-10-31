package emonitor.app.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import emonitor.app.domain.Report;
import emonitor.app.serialization.ReportSerializer;
import lombok.Getter;

@JsonSerialize(using = ReportSerializer.class)
public class ReportWrapper {

    @Getter private final Report report;

    public ReportWrapper(Report report) {
        this.report = report;
    }
}
