package com.jpacourse.persistance.service;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class VisitServiceTest {
    @Autowired
    private VisitService visitService;

    @Test
    void shouldReturnVisitsForGivenPatientId() {
        // given
        int patientId = 1;

        // when
        List<VisitTO> visits = visitService.findByPatientId(patientId);

        // then
        assertNotNull(visits);
        assertEquals(3, visits.size());

        VisitTO checkup = visits.stream()
                .filter(v -> LocalDateTime.of(2025,3,26,10,0,0).equals(v.getTime()))
                .findFirst()
                .orElse(null);

        assertNotNull(checkup);
        assertEquals("John", checkup.getDoctor().getFirstName());
        assertEquals("Doe", checkup.getDoctor().getLastName());

    }
}
