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

/**
 * Tests the difference between FetchMode.SELECT and FetchMode.JOIN when fetching a Patient with visits.
 * 
 * This test is part of Lab 3 exercise to observe SQL query differences when using different fetch modes.
 * See the comments below for observations about each fetch mode.
 * 
 * To use this test properly:
 * 1. Configure PatientEntity with @Fetch(FetchMode.SELECT) and run the test
 * 2. Observe the log output and count the queries
 * 3. Change to @Fetch(FetchMode.JOIN) and run the test again
 * 4. Compare the output and observe the differences
 */
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

    /**
     * Test fetching a Patient with many visits using the configured fetch mode.
     * 
     * Observations:
     * 
     * When using FetchMode.SELECT with EAGER fetching:
     * - Executes 1 query to load the Patient
     * - Executes 1 additional query to load all visits for the patient
     * - Total of 2 SQL queries
     * - Advantage: Can be more efficient when dealing with many associations
     * 
     * When using FetchMode.JOIN with EAGER fetching:
     * - Executes 1 query with a JOIN to load the Patient and all visits in one go
     * - Total of 1 SQL query
     * - Advantage: Fewer round trips to the database
     * - Disadvantage: Can result in cartesian product with large result sets
     */
    @Test
    @Transactional
    public void testPatientWithVisitsFetchMode() {
        // Get patient with ID 3 which has been set up with multiple visits in data.sql
        PatientEntity patient = patientDao.findOne(3L);
        
        // Force loading of lazy associations if any
        assertNotNull(patient, "Patient should not be null");
        assertEquals("Michał", patient.getFirstName(), "First name should match");
        assertEquals("Lewandowski", patient.getLastName(), "Last name should match");
        
        // Access the visits collection to force loading if not already loaded
        assertNotNull(patient.getVisitEntityList(), "Visit list should not be null");
        System.out.println("Patient has " + patient.getVisitEntityList().size() + " visits");
        
        // Print details of each visit to ensure they're fully loaded
        for (VisitEntity visit : patient.getVisitEntityList()) {
            System.out.println("Visit ID: " + visit.getId() + ", Time: " + visit.getTime());
        }
        
        // Print statistics about executed queries
        System.out.println("\nSQL Query Execution Statistics:");
        System.out.println("Total queries executed: " + statistics.getQueryExecutionCount());
        System.out.println("Entity load count: " + statistics.getEntityLoadCount());
        System.out.println("Collection load count: " + statistics.getCollectionLoadCount());
        
        /*
         * OBSERVATIONS (add your findings here after running the tests with both fetch modes):
         * 
         * FetchMode.SELECT:
         * -----------------------------
         * This mode causes Hibernate to use separate SELECT statements for the main entity
         * and each collection being fetched. With EAGER fetching, this results in:
         * - One SQL query to fetch the patient
         * - One separate SQL query to fetch all visits for the patient
         * 
         * Advantages:
         * - Avoids large result sets caused by cartesian products
         * - More efficient for large collections
         * - Better caching potential
         * 
         * FetchMode.JOIN:
         * -----------------------------
         * This mode causes Hibernate to use SQL JOIN statements to fetch the main entity
         * and its collections in a single query. With EAGER fetching, this results in:
         * - One SQL query with JOINs to fetch the patient and all visits
         * 
         * Advantages:
         * - Fewer database round trips
         * - Potentially faster for small to medium collections
         * 
         * Disadvantages:
         * - Can cause N+1 problem if not used carefully
         * - Large result sets due to cartesian products
         * - Less efficient memory usage with duplicate data
         */
    }
}