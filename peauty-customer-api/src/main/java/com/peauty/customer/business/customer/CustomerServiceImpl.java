package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.UploadProfileImageResult;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerPort customerPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file) {
        User user = customerPort.getByUserId(userId);
        String newProfileImageUrl = internalPort.uploadImage(file);
        user.uploadProfileImageUrl(newProfileImageUrl);
        return UploadProfileImageResult.from(customerPort.save(user));
    }
}
