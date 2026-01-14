package com.soundbar91.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration Test Base Class
 * All integration tests should extend this class for common configuration.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestBase {
    // Common test utilities and configurations can be added here
}
