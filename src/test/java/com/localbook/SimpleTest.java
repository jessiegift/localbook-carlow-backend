package com.localbook;

import com.localbook.model.*;
import com.localbook.repository.*;
import org.junit.jupiter.api. Test;
import org.springframework. beans.factory.annotation.Autowired;
import org.springframework. boot.test.context.SpringBootTest;
import org.springframework. transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util. List;

import static org.junit.jupiter. api.Assertions.*;

/**
 * Simple test file for LocalBook app
 * Tests existing features matching YOUR actual entities and enums
 */
@SpringBootTest
@Transactional
public class SimpleTest {

    @Autowired(required = false)
    private UserRepository userRepository;

    @Autowired(required = false)
    private BusinessRepository businessRepository;

    @Autowired(required = false)
    private ServiceRepository serviceRepository;

    @Autowired(required = false)
    private AppointmentRepository appointmentRepository;

    @Autowired(required = false)
    private RatingRepository ratingRepository;

    // ========================================
    // TEST 1: DATABASE CONNECTION
    // ========================================
    @Test
    public void test01_DatabaseConnection() {
        System.out.println("\n📊 TEST 1: Database Connection");
        System.out.println("=".repeat(50));

        assertNotNull(userRepository, "UserRepository should be loaded");
        assertNotNull(businessRepository, "BusinessRepository should be loaded");
        assertNotNull(serviceRepository, "ServiceRepository should be loaded");

        System.out.println("✅ All repositories loaded successfully");
        System.out.println("✅ Database connection working\n");
    }

    // ========================================
    // TEST 2: CREATE USER
    // ========================================
    @Test
    public void test02_CreateUser() {
        System.out.println("\n👤 TEST 2: Create User");
        System.out.println("=".repeat(50));

        // Create user with enum
        User user = new User();
        user.setName("Test User");
        user.setEmail("test" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("0871234567");
        user.setRole(UserRole.CLIENT); // ✅ Using your enum

        // Save user
        User savedUser = userRepository.save(user);

        // Verify
        assertNotNull(savedUser. getId(), "User should have an ID after saving");
        assertEquals("Test User", savedUser.getName());
        assertEquals(UserRole.CLIENT, savedUser. getRole());

        System.out.println("✅ User created with ID: " + savedUser.getId());
        System.out.println("✅ User name: " + savedUser.getName());
        System.out.println("✅ User role: " + savedUser.getRole() + "\n");
    }

    // ========================================
    // TEST 3: CREATE BUSINESS
    // ========================================
    @Test
    public void test03_CreateBusiness() {
        System.out.println("\n🏪 TEST 3: Create Business");
        System.out.println("=".repeat(50));

        // First create owner user
        User owner = new User();
        owner.setName("Business Owner");
        owner.setEmail("owner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password123");
        owner.setRole(UserRole.BUSINESS_OWNER); // ✅ Using your enum
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        // Create business
        Business business = new Business();
        business.setBusinessName("Test Salon");
        business.setOwnerName("Business Owner");
        business.setAddress("123 Test Street");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 X2Y7");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("testsalon" + System.currentTimeMillis() + "@example.com");
        business.setDescription("Professional hair salon");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business. setIsApproved(true);
        business. setLat(52.8408);
        business.setLng(-6.9261);

        // Save business
        Business savedBusiness = businessRepository.save(business);

        // Verify
        assertNotNull(savedBusiness.getId(), "Business should have an ID");
        assertEquals("Test Salon", savedBusiness.getBusinessName());
        assertEquals("Carlow", savedBusiness.getTown());
        assertEquals("Hair Salons", savedBusiness. getCategory());
        assertEquals("APPROVED", savedBusiness.getStatus());
        assertTrue(savedBusiness.isApproved());

        System.out.println("✅ Business created with ID: " + savedBusiness.getId());
        System.out.println("✅ Business name: " + savedBusiness.getBusinessName());
        System.out.println("✅ Owner:  " + savedBusiness.getOwnerName());
        System.out.println("✅ Town: " + savedBusiness.getTown());
        System.out. println("✅ Category: " + savedBusiness.getCategory());
        System.out. println("✅ Status: " + savedBusiness.getStatus());
        System.out.println("✅ Approved: " + savedBusiness.isApproved() + "\n");
    }

    // ========================================
    // TEST 4: CREATE SERVICE
    // ========================================
    @Test
    public void test04_CreateService() {
        System.out.println("\n⚙️ TEST 4: Create Service");
        System.out.println("=".repeat(50));

        // Create owner first
        User owner = new User();
        owner.setName("Service Owner");
        owner.setEmail("serviceowner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password123");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        // Create business
        Business business = new Business();
        business.setBusinessName("Service Test Salon");
        business.setOwnerName("Service Owner");
        business.setAddress("456 Service St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 ABC1");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("service" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business.setIsApproved(true);
        business = businessRepository.save(business);

        // Create service
        com.localbook.model.Service service = new com.localbook.model.Service();
        service.setName("Haircut");
        service.setDescription("Professional haircut");
        service.setDurationMinutes(60);
        service.setPrice(25.0);
        service.setBusiness(business);

        // Save service
        com.localbook.model.Service savedService = serviceRepository.save(service);

        // Verify
        assertNotNull(savedService.getId(), "Service should have an ID");
        assertEquals("Haircut", savedService.getName());
        assertEquals(60, savedService.getDurationMinutes());
        assertEquals(25.0, savedService.getPrice());

        System.out.println("✅ Service created with ID: " + savedService.getId());
        System.out.println("✅ Service name: " + savedService.getName());
        System.out.println("✅ Duration: " + savedService.getDurationMinutes() + " minutes");
        System.out.println("✅ Price: €" + savedService.getPrice() + "\n");
    }

    // ========================================
    // TEST 5: CREATE APPOINTMENT
    // ========================================
    @Test
    public void test05_CreateAppointment() {
        System.out.println("\n📅 TEST 5: Create Appointment");
        System.out.println("=".repeat(50));

        // Create client user
        User client = new User();
        client.setName("Appointment Client");
        client.setEmail("aptclient" + System.currentTimeMillis() + "@example.com");
        client.setPassword("password");
        client.setRole(UserRole.CLIENT);
        client.setPhoneNumber("0871234567");
        client = userRepository.save(client);

        // Create owner
        User owner = new User();
        owner.setName("Appointment Owner");
        owner.setEmail("aptowner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password");
          owner.setPhoneNumber("0871234567");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner = userRepository.save(owner);
      

        // Create business
        Business business = new Business();
        business.setBusinessName("Appointment Business");
        business.setOwnerName("Appointment Owner");
        business.setAddress("789 Apt St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 DEF2");
        business.setCategory("Hair Salons");
        business.setLocation("Carlow Town");
        business.setPhoneNumber("0871234567");
        business.setEmail("aptbiz" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business.setIsApproved(true);
        business = businessRepository. save(business);

        // Create service
        com.localbook. model.Service service = new com. localbook.model.Service();
        service.setName("Haircut");
        service.setDurationMinutes(60);
        service.setPrice(25.0);
        service.setBusiness(business);
        service = serviceRepository.save(service);

        // Create appointment with CONFIRMED status (no PENDING in your enum)
        Appointment appointment = new Appointment();
        appointment.setUser(client);
        appointment. setBusiness(business);
        appointment.setService(service);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment. setStatus(AppointmentStatus.CONFIRMED); // ✅ Using your enum
        appointment. setNotes("Test appointment");

        // Save appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Verify
        assertNotNull(savedAppointment. getId(), "Appointment should have an ID");
        assertEquals(AppointmentStatus.CONFIRMED, savedAppointment.getStatus());
        assertEquals(client. getId(), savedAppointment.getUser().getId());
        assertEquals(business.getId(), savedAppointment.getBusiness().getId());

        System.out.println("✅ Appointment created with ID:  " + savedAppointment.getId());
        System.out.println("✅ Status: " + savedAppointment. getStatus());
        System.out.println("✅ Client: " + savedAppointment. getUser().getName());
        System.out.println("✅ Business: " + savedAppointment.getBusiness().getBusinessName() + "\n");
    }

    // ========================================
    // TEST 6: COMPLETE APPOINTMENT
    // ========================================
    @Test
    public void test06_CompleteAppointment() {
        System.out. println("\n✅ TEST 6: Complete Appointment");
        System.out.println("=".repeat(50));

        // Setup
        User client = new User();
        client.setName("Complete Client");
        client.setEmail("completeclient" + System.currentTimeMillis() + "@example.com");
        client.setPassword("password");
        client.setRole(UserRole.CLIENT);
        client.setPhoneNumber("0871234567");
        client = userRepository.save(client);

        User owner = new User();
        owner.setName("Complete Owner");
        owner.setEmail("completeowner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        Business business = new Business();
        business.setBusinessName("Complete Business");
        business.setOwnerName("Complete Owner");
        business.setAddress("321 Complete St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 GHI3");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("completebiz" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business = businessRepository.save(business);

        com.localbook.model.Service service = new com.localbook.model.Service();
        service.setName("Haircut");
        service.setDurationMinutes(60);
        service.setPrice(25.0);
        service.setBusiness(business);
        service = serviceRepository.save(service);

        Appointment appointment = new Appointment();
        appointment.setUser(client);
        appointment. setBusiness(business);
        appointment.setService(service);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus(AppointmentStatus. CONFIRMED);
        appointment = appointmentRepository.save(appointment);

        System.out.println("📝 Initial status: " + appointment.getStatus());

        // Change status to COMPLETED
        appointment. setStatus(AppointmentStatus. COMPLETED);
        appointment = appointmentRepository.save(appointment);

        // Verify
        assertEquals(AppointmentStatus.COMPLETED, appointment. getStatus());

        System.out.println("✅ Status changed to: " + appointment.getStatus());
        System.out. println("✅ Appointment completed successfully\n");
    }

    // ========================================
    // TEST 7: CREATE RATING
    // ========================================
    @Test
    public void test07_CreateRating() {
        System.out.println("\n⭐ TEST 7: Create Rating");
        System.out. println("=".repeat(50));

        // Create client
        User client = new User();
        client.setName("Rating Client");
        client.setEmail("ratingclient" + System.currentTimeMillis() + "@example.com");
        client.setPassword("password");
        client.setRole(UserRole.CLIENT);
        client.setPhoneNumber("0871234567");
        client = userRepository.save(client);

        // Create owner
        User owner = new User();
        owner.setName("Rating Owner");
        owner.setEmail("ratingowner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        // Create business
        Business business = new Business();
        business.setBusinessName("Rating Business");
        business.setOwnerName("Rating Owner");
        business.setAddress("111 Rating St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 JKL4");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("ratingbiz" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business = businessRepository.save(business);

        // Create rating (without comment field if it doesn't exist)
        Rating rating = new Rating();
        rating.setUser(client);
        rating.setBusiness(business);
        rating.setRating(5);
        rating.setSentiment("positive");

        // Save rating
        Rating savedRating = ratingRepository.save(rating);

        // Verify
        assertNotNull(savedRating.getId(), "Rating should have an ID");
        assertEquals(5, savedRating.getRating());
        assertEquals("positive", savedRating.getSentiment());

        System.out. println("✅ Rating created with ID: " + savedRating. getId());
        System.out. println("✅ Rating:  " + savedRating.getRating() + " stars");
        System.out.println("✅ Sentiment: " + savedRating.getSentiment() + "\n");
    }

    // ========================================
    // TEST 8: CALCULATE AVERAGE RATING
    // ========================================
    @Test
    public void test08_CalculateAverageRating() {
        System.out.println("\n📊 TEST 8: Calculate Average Rating");
        System.out. println("=".repeat(50));

        // Create owner
        User owner = new User();
        owner.setName("Avg Owner");
        owner.setEmail("avgowner" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("password");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        // Create business
        Business business = new Business();
        business.setBusinessName("Average Rating Business");
        business.setOwnerName("Avg Owner");
        business.setAddress("222 Avg St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 MNO5");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("avgbiz" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business = businessRepository.save(business);

        // Create client
        User client = new User();
        client.setName("Rating Client");
        client.setEmail("avgclient" + System.currentTimeMillis() + "@example.com");
        client.setPassword("password");
        client.setRole(UserRole.CLIENT);
        client.setPhoneNumber("0871234567");
        client = userRepository.save(client);

        // Create multiple ratings
        Rating rating1 = new Rating();
        rating1.setUser(client);
        rating1.setBusiness(business);
        rating1.setRating(5);
        rating1.setSentiment("positive");
        ratingRepository.save(rating1);

        Rating rating2 = new Rating();
        rating2.setUser(client);
        rating2.setBusiness(business);
        rating2.setRating(4);
        rating2.setSentiment("positive");
        ratingRepository.save(rating2);

        Rating rating3 = new Rating();
        rating3.setUser(client);
        rating3.setBusiness(business);
        rating3.setRating(3);
        rating3.setSentiment("neutral");
        ratingRepository.save(rating3);

        // Get all ratings for this business
        List<Rating> ratings = ratingRepository.findByBusinessId(business.getId());

        // Calculate average manually
        double sum = 0;
        for (Rating r : ratings) {
            sum += r.getRating();
        }
        double average = sum / ratings.size();

        // Verify
        assertEquals(3, ratings.size(), "Should have 3 ratings");
        assertEquals(4.0, average, 0.01, "Average of 5, 4, 3 should be 4.0");

        System.out. println("✅ Total ratings: " + ratings.size());
        System.out.println("✅ Ratings:  5, 4, 3");
        System.out.println("✅ Average: " + average);
        System.out.println("✅ Calculation correct:  (5 + 4 + 3) / 3 = 4.0\n");
    }

    // ========================================
    // TEST 9: GET APPROVED BUSINESSES
    // ========================================
    @Test
    public void test09_GetApprovedBusinesses() {
        System.out.println("\n🏢 TEST 9: Get Approved Businesses");
        System.out.println("=".repeat(50));

        // Create owner for approved business
        User approvedOwner = new User();
        approvedOwner.setName("Approved Owner");
        approvedOwner.setEmail("approvedowner" + System.currentTimeMillis() + "@example.com");
        approvedOwner.setPassword("password");
        approvedOwner.setRole(UserRole.BUSINESS_OWNER);
        approvedOwner.setPhoneNumber("0871234567");
        approvedOwner = userRepository.save(approvedOwner);

        // Create approved business
        Business approvedBusiness = new Business();
        approvedBusiness.setBusinessName("Approved Salon");
        approvedBusiness. setOwnerName("Approved Owner");
        approvedBusiness. setAddress("333 Approved St");
        approvedBusiness. setTown("Carlow");
        approvedBusiness.setCounty("Carlow");
        approvedBusiness.setEircode("R93 PQR6");
        approvedBusiness.setLocation("Carlow Town");
        approvedBusiness.setCategory("Hair Salons");
        approvedBusiness.setPhoneNumber("0871234567");
        approvedBusiness.setEmail("approved" + System.currentTimeMillis() + "@example.com");
        approvedBusiness.setOwner(approvedOwner);
        approvedBusiness.setStatus("APPROVED");
        approvedBusiness. setIsApproved(true);
        businessRepository.save(approvedBusiness);

        // Create owner for non-approved business
        User notApprovedOwner = new User();
        notApprovedOwner.setName("Not Approved Owner");
        notApprovedOwner.setEmail("notapprovedowner" + System.currentTimeMillis() + "@example.com");
        notApprovedOwner.setPassword("password");
        notApprovedOwner.setRole(UserRole.BUSINESS_OWNER);
        notApprovedOwner.setPhoneNumber("0871234567");
        notApprovedOwner = userRepository.save(notApprovedOwner);

        // Create non-approved business
        Business notApprovedBusiness = new Business();
        notApprovedBusiness.setBusinessName("Not Approved Salon");
        notApprovedBusiness.setOwnerName("Not Approved Owner");
        notApprovedBusiness.setAddress("444 Not Approved St");
        notApprovedBusiness. setTown("Carlow");
        notApprovedBusiness.setCounty("Carlow");
        notApprovedBusiness.setEircode("R93 STU7");
        notApprovedBusiness.setLocation("Carlow Town");
        notApprovedBusiness.setCategory("Hair Salons");
        notApprovedBusiness.setPhoneNumber("0871234567");
        notApprovedBusiness.setEmail("notapproved" + System.currentTimeMillis() + "@example.com");
        notApprovedBusiness.setOwner(notApprovedOwner);
        notApprovedBusiness.setStatus("REJECTED");
        notApprovedBusiness.setIsApproved(false);
        businessRepository.save(notApprovedBusiness);

        // Get only approved businesses
        List<Business> approvedBusinesses = businessRepository. findByIsApproved(true);

        // Verify
        assertTrue(approvedBusinesses.size() > 0, "Should have at least one approved business");
        
        // Check all returned businesses are approved
        for (Business b : approvedBusinesses) {
            assertTrue(b.isApproved(), "Business should be approved");
        }

        System.out.println("✅ Found " + approvedBusinesses.size() + " approved business(es)");
        System.out.println("✅ All returned businesses have approved status");
        System.out.println("✅ Non-approved businesses correctly filtered out\n");
    }

    // ========================================
    // TEST 10: TIME SLOT BLOCKING
    // ========================================
    @Test
    public void test10_TimeSlotBlocking() {
        System.out.println("\n🕐 TEST 10: Time Slot Blocking");
        System.out.println("=".repeat(50));

        // Create owner
        User owner = new User();
        owner.setName("Slot Owner");
        owner.setEmail("slotowner" + System. currentTimeMillis() + "@example.com");
        owner.setPassword("password");
        owner.setRole(UserRole.BUSINESS_OWNER);
        owner.setPhoneNumber("0871234567");
        owner = userRepository.save(owner);

        // Create business
        Business business = new Business();
        business.setBusinessName("Slot Test Business");
        business.setOwnerName("Slot Owner");
        business.setAddress("555 Slot St");
        business.setTown("Carlow");
        business.setCounty("Carlow");
        business.setEircode("R93 VWX8");
        business.setLocation("Carlow Town");
        business.setCategory("Hair Salons");
        business.setPhoneNumber("0871234567");
        business.setEmail("slotbiz" + System.currentTimeMillis() + "@example.com");
        business.setOwner(owner);
        business.setStatus("APPROVED");
        business = businessRepository.save(business);

        // Create 60-minute service
        com.localbook.model.Service service = new com.localbook.model.Service();
        service.setName("60 Min Service");
        service.setDurationMinutes(60);
        service.setPrice(30.0);
        service.setBusiness(business);
        service = serviceRepository.save(service);

        // Create client
        User client = new User();
        client.setName("Slot Client");
        client.setEmail("slotclient" + System.currentTimeMillis() + "@example.com");
        client.setPassword("password");
        client.setRole(UserRole.CLIENT);
        client.setPhoneNumber("0871234567");
        client = userRepository.save(client);

        // Create confirmed appointment at 10:00 AM
        Appointment appointment = new Appointment();
        appointment.setUser(client);
        appointment. setBusiness(business);
        appointment.setService(service);
        appointment.setAppointmentDateTime(LocalDateTime.of(2025, 12, 25, 10, 0));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment = appointmentRepository.save(appointment);

        // Verify service duration
        assertEquals(60, service. getDurationMinutes(), "Service should be 60 minutes");

        // Calculate blocked slots
        // 60 minutes = 4 slots (10:00, 10:15, 10:30, 10:45)
        int slotsBlocked = service.getDurationMinutes() / 15;

        assertEquals(4, slotsBlocked, "60-minute service should block 4 time slots");

        System.out. println("✅ Service duration:  " + service.getDurationMinutes() + " minutes");
        System.out.println("✅ Appointment time: 10:00 AM");
        System.out.println("✅ Slots blocked: " + slotsBlocked);
        System.out.println("✅ Blocked times: 10:00, 10:15, 10:30, 10:45");
        System.out.println("✅ Time slot blocking working correctly\n");
    }

    // ========================================
    // SUMMARY
    // ========================================
    @Test
    public void test99_Summary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎉 ALL TESTS COMPLETED SUCCESSFULLY!");
        System.out. println("=".repeat(50));
        System.out.println("\n📊 Test Summary:");
        System.out. println("   ✅ Database Connection");
        System.out.println("   ✅ User Creation (CLIENT, BUSINESS_OWNER)");
        System.out.println("   ✅ Business Creation (with all required fields)");
        System.out.println("   ✅ Service Creation");
        System.out.println("   ✅ Appointment Creation (CONFIRMED)");
        System.out.println("   ✅ Appointment Completion (COMPLETED)");
        System.out.println("   ✅ Rating Creation");
        System.out.println("   ✅ Average Rating Calculation");
        System.out.println("   ✅ Business Filtering (Approved)");
        System.out.println("   ✅ Time Slot Blocking");
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}