package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.impl.CreditService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK lateinit var creditRepository: CreditRepository
    @InjectMockKs lateinit var creditService: CreditService

    @Test
    fun `should create credit`() {
        //given
        val fakeCredit: Credit = buildCredit()
        every { creditRepository.save(any()) } returns fakeCredit
        //when
        val actual: Credit = creditService.save(fakeCredit)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(2000.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023,5, 26),
        numberOfInstallments: Int = 5,
        customerId: Long = 1L
    ) = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = Customer(
            id = customerId
        )
    )
}