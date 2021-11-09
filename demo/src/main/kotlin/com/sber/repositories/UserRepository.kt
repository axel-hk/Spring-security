package com.sber.repositories

import com.sber.domain.Address
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<Address, Long>