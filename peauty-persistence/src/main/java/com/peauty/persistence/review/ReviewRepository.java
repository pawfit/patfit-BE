package com.peauty.persistence.review;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaAttributeConverter<ReviewEntity, Long> {
}
