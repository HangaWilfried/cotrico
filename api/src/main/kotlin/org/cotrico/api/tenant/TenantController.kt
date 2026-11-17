package org.cotrico.api.tenant

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tenant")
class TenantController {
    @GetMapping fun fetchAllTenants(): List<String> = listOf("alice", "ashley")
}