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
        Long patientId = 1L;

        // when
        List<VisitTO> visits = visitService.findByPatientId(patientId);

        // then
        assertNotNull(visits);
        assertEquals(2, visits.size());  // Pacjent o ID=1 ma 2 wizyty w data.sql

        VisitTO checkup = visits.stream()
                .filter(v -> v.getTime().getYear() == 2023)  // Wizyty są z roku 2023
                .findFirst()
                .orElse(null);

        assertNotNull(checkup);

        // Sprawdź dane lekarza z ID=1 w data.sql
        assertEquals("Jan", checkup.getDoctor().getFirstName());
        assertEquals("Kowalski", checkup.getDoctor().getLastName());

    }
}
