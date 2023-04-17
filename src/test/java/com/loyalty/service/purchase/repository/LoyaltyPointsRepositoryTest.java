package com.loyalty.service.purchase.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LoyaltyPointsRepositoryTest {
    @Autowired private LoyaltyPointsRepository loyaltyPointsRepository;


    @Test
    void findByCustomerIdAndGroupByLoyaltyPoints() {

        final var result = loyaltyPointsRepository.findByCustomerIdAndGroupByLoyaltyPoints(1L,04,2023);
        assertEquals(13,result);
    }
}