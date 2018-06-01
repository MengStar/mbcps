package meng.xing.user.service;

import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    boolean initAccount(String account, String password, long account_id);
}
