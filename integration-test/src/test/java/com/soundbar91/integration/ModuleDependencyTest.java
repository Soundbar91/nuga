package com.soundbar91.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Module Dependency Integration Test
 * Verifies that all modules are properly connected and dependencies are resolved.
 */
class ModuleDependencyTest {

    @Test
    @DisplayName("Phase 2 common module classes should be accessible")
    void commonModuleAccessible() {
        // Verify common module is accessible
        assertThat(com.soundbar91.common.exception.BusinessException.class).isNotNull();
    }

    @Test
    @DisplayName("Phase 2 domain module classes should be accessible")
    void domainModuleAccessible() {
        // Verify domain module is accessible
        assertThat(com.soundbar91.domain.user.entity.User.class).isNotNull();
    }

    @Test
    @DisplayName("Phase 3 user-domain module classes should be accessible")
    void userDomainModuleAccessible() {
        // Verify user-domain module is accessible
        assertThat(com.soundbar91.user.domain.entity.User.class).isNotNull();
    }

    @Test
    @DisplayName("Phase 3 shop-domain module classes should be accessible")
    void shopDomainModuleAccessible() {
        // Verify shop-domain module is accessible
        assertThat(com.soundbar91.shop.domain.entity.Shop.class).isNotNull();
    }

    @Test
    @DisplayName("Phase 3 order-domain module classes should be accessible")
    void orderDomainModuleAccessible() {
        // Verify order-domain module is accessible
        assertThat(com.soundbar91.order.domain.entity.Order.class).isNotNull();
    }
}
