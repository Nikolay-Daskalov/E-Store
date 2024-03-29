package com.project.EStore.init;

import com.project.EStore.service.product.ProductSizeService;
import com.project.EStore.service.product.ProductSupplyService;
import com.project.EStore.service.user.RoleService;
import com.project.EStore.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLRImpl implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLRImpl.class);//TODO: Refactor with AOP

    private final UserService userService;
    private final ProductSupplyService productSupplyService;
    private final ProductSizeService productSizeService;
    private final RoleService roleService;

    public CLRImpl(UserService userService, ProductSupplyService productSupplyService, ProductSizeService productSizeService, RoleService roleService) {
        this.userService = userService;
        this.productSupplyService = productSupplyService;
        this.productSizeService = productSizeService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
        LOGGER.info("Application data initialized and running!");
    }

    private void init(){
        this.roleService.init();
        this.userService.init();
        this.productSizeService.init();
        this.productSupplyService.init();
    }
}
