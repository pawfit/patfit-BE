import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import com.peauty.persistence.config.JpaConfig;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.QCustomerEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = JpaConfig.class)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class QuerydslSettingTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void contextLoads() {
        CustomerEntity customer = CustomerEntity.builder()
                .socialId("1111")
                .socialPlatform(SocialPlatform.KAKAO)
                .status(Status.ACTIVE)
                .build();
        em.persist(customer);
        JPAQueryFactory query = new JPAQueryFactory(em);
        QCustomerEntity qCustomer = QCustomerEntity.customerEntity;
        CustomerEntity result = query
                .selectFrom(qCustomer)
                .fetchOne();
        assertThat(result).isEqualTo(customer);
    }
}
