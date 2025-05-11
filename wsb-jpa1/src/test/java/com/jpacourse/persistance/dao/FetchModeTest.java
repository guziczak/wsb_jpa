package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FetchModeTest {

    @Autowired
    private PatientDao patientDao;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    public void setup() {
        Session session = entityManager.unwrap(Session.class);
        statistics = session.getSessionFactory().getStatistics();
        statistics.setStatisticsEnabled(true);
        statistics.clear();
    }

    @Test
    @Transactional
    public void testPatientWithVisitsFetchMode() {
        PatientEntity patient = patientDao.findOne(3L);
        
        assertNotNull(patient, "Patient should not be null");
        assertEquals("Michał", patient.getFirstName(), "First name should match");
        assertEquals("Lewandowski", patient.getLastName(), "Last name should match");
        
        assertNotNull(patient.getVisitEntityList(), "Visit list should not be null");
        System.out.println("Patient has " + patient.getVisitEntityList().size() + " visits");
        
        for (VisitEntity visit : patient.getVisitEntityList()) {
            System.out.println("Visit ID: " + visit.getId() + ", Time: " + visit.getTime());
        }
        
        System.out.println("\nSQL Query Execution Statistics:");
        System.out.println("Total queries executed: " + statistics.getQueryExecutionCount());
        System.out.println("Entity load count: " + statistics.getEntityLoadCount());
        System.out.println("Collection load count: " + statistics.getCollectionLoadCount());

    }
}