package com.project.EStore.init;

import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSizeService;
import com.project.EStore.service.domain.product.ProductSupplyService;
import com.project.EStore.service.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLRImpl implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLRImpl.class);

    private final UserService userService;
    private final ProductSupplyService productSupplyService;
    private final ProductSizeService productSizeService;
    private final ProductService productService;

    public CLRImpl(UserService userService, ProductSupplyService productSupplyService, ProductSizeService productSizeService, ProductService productService) {
        this.userService = userService;
        this.productSupplyService = productSupplyService;
        this.productSizeService = productSizeService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
        LOGGER.info("Application data initialized and running!");
    }

    private void init(){
        this.userService.init();
        this.productSizeService.init();
        this.productSupplyService.init();
    }
}
