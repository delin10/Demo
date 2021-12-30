package nil.ed.test.web.controller;

import nil.ed.test.springboot.aop.AopConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TransactionalService {

    @Transactional
    @RequestMapping
    @AopConfig.Anno
    public void insert(@RequestParam int...test) {

    }

}
