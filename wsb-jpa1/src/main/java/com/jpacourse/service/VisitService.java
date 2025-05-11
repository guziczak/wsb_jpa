package com.jpacourse.service;

import com.jpacourse.dto.VisitTO;

import java.util.List;

public interface VisitService {
    List<VisitTO> findByPatientId(final Long patientId);
}
