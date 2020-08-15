package com.example.jpa.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserAuditor implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        // TODO: 2020/8/15 返回当前用户的id
        return Optional.empty();
    }
}
